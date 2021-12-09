package chat;
import com.kuang.dao.UserDao;
import com.kuang.dao.UserDaoImpl;
import com.kuang.pojo.Message;
import com.kuang.pojo.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.net.*;
import java.util.*;

// 线程运行InetAddress 保存ip地址和对应的端口号
public class servergui implements Runnable {
    private static HashMap<String,HashMap<Integer,InetAddress>> iplist=new HashMap<String,HashMap<Integer,InetAddress>>();
    private static List<String> namelist = new ArrayList<>();// 包
    private static List<String> loginuser = new ArrayList<String>(); //已登录的用户
    private String name;
    private static int sum;
    private static UserDaoImpl mapper;
    private static DatagramSocket server;
    private static DatagramSocket fileport;
    private static  Thread t;
    public servergui(UserDaoImpl mapper,DatagramSocket server,DatagramSocket fileport) {
        servergui.mapper = mapper;
        servergui.server =server;
        servergui.fileport=fileport;
    }

    public static void main(String[] args) throws Exception {
        try {
            server = new DatagramSocket(13000);//服务器端口
            fileport=new DatagramSocket(13001);//专门接受文件的端口
            System.out.println("服务器启动，正在等待连接...");
            //构建用户名列表以及总数生成id，避免用户名相同
            ApplicationContext context = new ClassPathXmlApplicationContext("mybatis-config.xml");
            UserDaoImpl mapper = (UserDaoImpl) context.getBean("UserDao", UserDao.class);
            namelist = mapper.selectname();
            sum = mapper.selectcount();
            t = new Thread(() -> {
                while (true) {
                    servergui user = new servergui(mapper,server,fileport);
                    Thread u = new Thread(user);
                    u.start();
                    try {
                        Thread.sleep(60*60*24*30);
                    } catch (InterruptedException e) {
                        System.out.print("");
                    }
                }
            });
            t.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        DatagramPacket dp;//接收包裹
        ByteArrayOutputStream bout;
        ObjectOutputStream oout=null;
        try {
            while (true) {
                byte[] buf = new byte[1024 * 8];
                dp = new DatagramPacket(buf, buf.length);
                byte[] sendBuff;
                server.receive(dp);//接受信息，若无则堵塞在这
                ByteArrayInputStream bint = new ByteArrayInputStream(buf);

                ObjectInputStream oint = new ObjectInputStream(bint);
                Message receive = (Message)oint.readObject();       //反序列化，恢复对象
                //解析发送方ip
                InetAddress ip = dp.getAddress();
                //解析发送方端口号
                int port = dp.getPort();
                if (receive.getFlag() == 1) {

                    User user1 = null;
                    user1 = receive.getUser();
                    int flag = user1.getFlag();
                    if (flag == 1) {
                        //登陆
                        if (namelist.contains(user1.getName())) {
                            int exist = mapper.getbynamepwd(user1);
                            if (exist == 1 && loginuser.contains(user1.getName())) {
                                String str = "登陆失败，用户已登录";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                            } else if (exist == 1) {
                                //发送登陆信息
                                String str = "登陆成功";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                                //发送列表
                                name = user1.getName();
                                loginuser.add(name);
                                //可能要线程沉睡
                                HashMap<Integer,InetAddress> a=new HashMap<Integer,InetAddress>();
                                a.put(port,ip);
                                iplist.put(name,a);
                                //把在线用户写过去
                                Message message = new Message(1, loginuser);

                                bout = new ByteArrayOutputStream();
                                oout = new ObjectOutputStream(bout);

                                //遍历每个ip地址以及端口号发送好友更新信息
                                for (HashMap<Integer,InetAddress> entry:iplist.values()) {
                                    for(int port2:entry.keySet()){
                                        InetAddress ipadress=entry.get(port2);
                                        message.setUseport(port2);
                                        oout.writeObject(message);
                                        oout.flush();
                                        byte[] Buff = bout.toByteArray();       //转化为字节数组
                                        DatagramPacket dPacket = new DatagramPacket(Buff, Buff.length, ipadress,port2);
                                        server.send(dPacket);
                                    }
                                }
                                System.out.println(name+"已登录");
                                //当一个登陆成功的时候，唤醒主线程新建一个子线程去接收信息，尽量做到服务端线程数比客户端线程数多1从而不会丢失信息
                                t.interrupt();
                            } else {
                                String str = "登陆失败，用户名或密码错误";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                            }
                        } else {
                            String str = "该用户不存在请注册";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                        }

                    }
                    else if (flag == 0) {//注册
                        if (namelist.contains(user1.getName())) {
                            String str = "该用户名存在，请更改";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                        } else {
                            //mybatis框架新增用户
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("username", user1.getName());
                            map.put("pwd", user1.getPwd());
                            mapper.addUser(map);

                            String str = "注册成功";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                            namelist.add(user1.getName());
                        }
                    }

                }
                else if (receive.getFlag() == 2) {
                    String receuser=receive.getReceiveuser();//接收的用户
                    HashMap<Integer,InetAddress> entry=iplist.get(receuser);
                    for(int port2:entry.keySet()){
                        InetAddress ipadress=entry.get(port2);
                        //现在目的ip地址和对应端口号都获得了
                        receive.setCharmessage(receive.getSenduser()+"："+receive.getCharmessage()+"\r\n");
                        bout=new ByteArrayOutputStream();
                        oout = null;
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //转化为字节数组
                        dp=new DatagramPacket(sendBuff,sendBuff.length,ipadress,port2);
                        server.send(dp);
                    }
                }
                else if (receive.getFlag() == 3) {
                    //从客户端列表和已登录用户移除
                    iplist.remove(receive.getSenduser());
                    for (int i = 0; i < loginuser.size(); i++)
                        if (loginuser.get(i).equals(name)) {
                            loginuser.remove(i);
                            break;
                        }
                    name = receive.getSenduser();
                    bout = new ByteArrayOutputStream();
                    try {
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    sendBuff = bout.toByteArray();       //转化为字节数组
                    dp = new DatagramPacket(sendBuff, sendBuff.length, ip, port);
                    server.send(dp);
                    System.out.println(name + "已下线");
                    break;
                }
                else if(receive.getFlag()==4){
                            //接受文件到服务端指定路径
                    bout=new ByteArrayOutputStream();
                    try {
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //转化为字节数组
                        dp=new DatagramPacket(sendBuff,sendBuff.length,ip,port);
                        server.send(dp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    //先发送信息过去让客户端把文件发送过来
                    //存储路径
                    String filepath="D://savingdocument";
                    fliedeverty use=new fliedeverty();
                    File file=use.receivefile(filepath,fileport,receive,receive.getFileleng());
                    fileport=new DatagramSocket(12999);
                    //接受完成，发送信息给接收端看接收端是否愿意接受文件
                    receive.setFlag(5);
                    receive.setFile(file);
                    //发送信息给接收端，让接收端准备接受文件
                    String receuser=receive.getReceiveuser();//接收的用户
                    HashMap<Integer,InetAddress> entry=iplist.get(receuser);
                    for(int port2:entry.keySet()){
                        InetAddress ipadress=entry.get(port2);
                        bout=new ByteArrayOutputStream();
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //转化为字节数组
                        dp=new DatagramPacket(sendBuff,sendBuff.length,ipadress,port2);
                        server.send(dp);
                    }
                }
                else if(receive.getFlag()==5){
                            HashMap<Integer,InetAddress> entry=iplist.get(receive.getReceiveuser());
                            for(int port2:entry.keySet()){
                                InetAddress ipad=entry.get(port2);
                                String  result = receive.getResult();
                                File file= receive.getFile();
                                if(result.equals("拒绝接受"))
                                    file.delete();
                                else{
                                    //发送文件过去
                                    fliedeverty use=new fliedeverty();
                                    use.sentfile(file,fileport,port2,ipad);
                                    fileport=new DatagramSocket(12999);
                                }
                            }
                        }

                    }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

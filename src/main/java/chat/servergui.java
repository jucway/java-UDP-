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

// �߳�����InetAddress ����ip��ַ�Ͷ�Ӧ�Ķ˿ں�
public class servergui implements Runnable {
    private static HashMap<String,HashMap<Integer,InetAddress>> iplist=new HashMap<String,HashMap<Integer,InetAddress>>();
    private static List<String> namelist = new ArrayList<>();// ��
    private static List<String> loginuser = new ArrayList<String>(); //�ѵ�¼���û�
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
            server = new DatagramSocket(13000);//�������˿�
            fileport=new DatagramSocket(13001);//ר�Ž����ļ��Ķ˿�
            System.out.println("���������������ڵȴ�����...");
            //�����û����б��Լ���������id�������û�����ͬ
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
        DatagramPacket dp;//���հ���
        ByteArrayOutputStream bout;
        ObjectOutputStream oout=null;
        try {
            while (true) {
                byte[] buf = new byte[1024 * 8];
                dp = new DatagramPacket(buf, buf.length);
                byte[] sendBuff;
                server.receive(dp);//������Ϣ���������������
                ByteArrayInputStream bint = new ByteArrayInputStream(buf);

                ObjectInputStream oint = new ObjectInputStream(bint);
                Message receive = (Message)oint.readObject();       //�����л����ָ�����
                //�������ͷ�ip
                InetAddress ip = dp.getAddress();
                //�������ͷ��˿ں�
                int port = dp.getPort();
                if (receive.getFlag() == 1) {

                    User user1 = null;
                    user1 = receive.getUser();
                    int flag = user1.getFlag();
                    if (flag == 1) {
                        //��½
                        if (namelist.contains(user1.getName())) {
                            int exist = mapper.getbynamepwd(user1);
                            if (exist == 1 && loginuser.contains(user1.getName())) {
                                String str = "��½ʧ�ܣ��û��ѵ�¼";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                            } else if (exist == 1) {
                                //���͵�½��Ϣ
                                String str = "��½�ɹ�";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                                //�����б�
                                name = user1.getName();
                                loginuser.add(name);
                                //����Ҫ�̳߳�˯
                                HashMap<Integer,InetAddress> a=new HashMap<Integer,InetAddress>();
                                a.put(port,ip);
                                iplist.put(name,a);
                                //�������û�д��ȥ
                                Message message = new Message(1, loginuser);

                                bout = new ByteArrayOutputStream();
                                oout = new ObjectOutputStream(bout);

                                //����ÿ��ip��ַ�Լ��˿ںŷ��ͺ��Ѹ�����Ϣ
                                for (HashMap<Integer,InetAddress> entry:iplist.values()) {
                                    for(int port2:entry.keySet()){
                                        InetAddress ipadress=entry.get(port2);
                                        message.setUseport(port2);
                                        oout.writeObject(message);
                                        oout.flush();
                                        byte[] Buff = bout.toByteArray();       //ת��Ϊ�ֽ�����
                                        DatagramPacket dPacket = new DatagramPacket(Buff, Buff.length, ipadress,port2);
                                        server.send(dPacket);
                                    }
                                }
                                System.out.println(name+"�ѵ�¼");
                                //��һ����½�ɹ���ʱ�򣬻������߳��½�һ�����߳�ȥ������Ϣ����������������߳����ȿͻ����߳�����1�Ӷ����ᶪʧ��Ϣ
                                t.interrupt();
                            } else {
                                String str = "��½ʧ�ܣ��û������������";
                                dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                                server.send(dp);
                            }
                        } else {
                            String str = "���û���������ע��";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                        }

                    }
                    else if (flag == 0) {//ע��
                        if (namelist.contains(user1.getName())) {
                            String str = "���û������ڣ������";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                        } else {
                            //mybatis��������û�
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("username", user1.getName());
                            map.put("pwd", user1.getPwd());
                            mapper.addUser(map);

                            String str = "ע��ɹ�";
                            dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ip, port);
                            server.send(dp);
                            namelist.add(user1.getName());
                        }
                    }

                }
                else if (receive.getFlag() == 2) {
                    String receuser=receive.getReceiveuser();//���յ��û�
                    HashMap<Integer,InetAddress> entry=iplist.get(receuser);
                    for(int port2:entry.keySet()){
                        InetAddress ipadress=entry.get(port2);
                        //����Ŀ��ip��ַ�Ͷ�Ӧ�˿ںŶ������
                        receive.setCharmessage(receive.getSenduser()+"��"+receive.getCharmessage()+"\r\n");
                        bout=new ByteArrayOutputStream();
                        oout = null;
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
                        dp=new DatagramPacket(sendBuff,sendBuff.length,ipadress,port2);
                        server.send(dp);
                    }
                }
                else if (receive.getFlag() == 3) {
                    //�ӿͻ����б���ѵ�¼�û��Ƴ�
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

                    sendBuff = bout.toByteArray();       //ת��Ϊ�ֽ�����
                    dp = new DatagramPacket(sendBuff, sendBuff.length, ip, port);
                    server.send(dp);
                    System.out.println(name + "������");
                    break;
                }
                else if(receive.getFlag()==4){
                            //�����ļ��������ָ��·��
                    bout=new ByteArrayOutputStream();
                    try {
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
                        dp=new DatagramPacket(sendBuff,sendBuff.length,ip,port);
                        server.send(dp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    //�ȷ�����Ϣ��ȥ�ÿͻ��˰��ļ����͹���
                    //�洢·��
                    String filepath="D://savingdocument";
                    fliedeverty use=new fliedeverty();
                    File file=use.receivefile(filepath,fileport,receive,receive.getFileleng());
                    fileport=new DatagramSocket(12999);
                    //������ɣ�������Ϣ�����ն˿����ն��Ƿ�Ը������ļ�
                    receive.setFlag(5);
                    receive.setFile(file);
                    //������Ϣ�����նˣ��ý��ն�׼�������ļ�
                    String receuser=receive.getReceiveuser();//���յ��û�
                    HashMap<Integer,InetAddress> entry=iplist.get(receuser);
                    for(int port2:entry.keySet()){
                        InetAddress ipadress=entry.get(port2);
                        bout=new ByteArrayOutputStream();
                        oout = new ObjectOutputStream(bout);
                        oout.writeObject(receive);
                        oout.flush();
                        sendBuff=bout.toByteArray();       //ת��Ϊ�ֽ�����
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
                                if(result.equals("�ܾ�����"))
                                    file.delete();
                                else{
                                    //�����ļ���ȥ
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

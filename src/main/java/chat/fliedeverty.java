package chat;

import com.kuang.pojo.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class fliedeverty {
    public File receivefile(String filepath, DatagramSocket server, Message mess,long leng) throws IOException {
        File file=null;
        FileOutputStream fos = null;
        byte []buff=new byte[1024*8];
        DatagramPacket packet=new DatagramPacket(buff,buff.length);
        try {
            File directory = new File(filepath);
            if(!directory.exists()) {
                directory.mkdir();
            }
            file = new File(directory.getAbsolutePath() + File.separatorChar + mess.getFilenmane());
            fos = new FileOutputStream(file);
            System.out.println("======== 开始接收文件 ========");
            int len = 0;   //数据长度
            while (len < leng) {  //无数据则开始循环接收数据
                //接收数据包
                server.receive(packet);
                len = packet.getLength()+len;
                buff=packet.getData();
                //指定接收到数据的长度，可使程序正常接收数据
                fos.write(buff,0,packet.getLength());
                fos.flush();
            }
            System.out.println("======== 文件接收成功 ========");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null) {
                fos.close();
            }
        }
        return file;
    }
    public void sentfile(File file, DatagramSocket s, int port, InetAddress serverip) throws Exception {
        InputStream inputstream=null;
        try {
            inputstream = new FileInputStream(file);
            byte[] data = new byte[1024*8];
            //创建UDP数据报
            while ((inputstream.read(data)) != -1) {
                DatagramPacket pack = new DatagramPacket(data, data.length,serverip,port);
                s.send(pack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputstream != null) {
                inputstream.close();
            }
            s.close();
        }
    }
}

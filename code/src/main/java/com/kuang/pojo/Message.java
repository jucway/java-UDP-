package com.kuang.pojo;

import java.io.File;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//set get方法
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Message implements java.io.Serializable {
    private String charmessage;//聊天内容
    private List<String> list;//好友列表
    private String senduser;//发送方
    private String receiveuser;//接收方
    private int flag;//1为更新好友列表，2为私发，3为关闭信息,4为发送文件，5为接受文件
    private String filenmane;
    private File file;
    private String result;
    private User user;
    private int useport;
    private long fileleng;//文件长度
    private String sendTime;

    public Message(User user){
        this.user=user;
    }
    public Message(int flag,String senduser,String receiveuser,String filenmane,File file,long fileleng){
        this.flag=flag;
        this.receiveuser=receiveuser;
        this.filenmane=filenmane;
        this.senduser=senduser;
        this.file=file;
        this.fileleng=fileleng;
    }
    public Message(int flag){
        this.flag=flag;
    }
    public Message(int flag,List<String> list){
        this.list=list;
        this.flag=flag;
    }
    public String getSendTime(String format) {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

}
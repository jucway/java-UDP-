package com.kuang.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//set get方法
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class User implements Serializable {
    private int id;  //id
    private String name;   //姓名
    private String pwd;   //密码
    public int flag;//0为注册，1为登陆
}

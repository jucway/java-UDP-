package com.kuang.dao;

import com.kuang.pojo.User;
import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> selectUser();
    int  addUser(Map<String,Object> map);
    List<String> selectname();
    int selectcount();
    int getbynamepwd(User user);
}
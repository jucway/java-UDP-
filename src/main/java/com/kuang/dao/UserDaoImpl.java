package com.kuang.dao;

import com.kuang.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    @Override
    public List<User> selectUser() {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        return mapper.selectUser();
    }

    @Override
    public int addUser(Map<String, Object> map) {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        return mapper.addUser(map);
    }

    @Override
    public List<String> selectname() {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        return mapper.selectname();
    }

    @Override
    public int selectcount() {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        return mapper.selectcount();
    }

    @Override
    public int getbynamepwd(User user) {
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        return mapper.getbynamepwd(user);
    }
}

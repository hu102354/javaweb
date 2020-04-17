package com.hsz.dao;

import com.hsz.domain.Admin;
import com.hsz.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的dao
 */
public interface UserDao{

    //判断账号密码方法
    public Admin findUserByUsernameAndPassword(String username, String password);

    //添加用户信息
    public void addUser(User user);

    //根据id删除user
    void delete(int id);

    //根据id查询
    User findById(int id);

    //修改用户信息
    void update(User user);

    //查询总记录数
    int findTotalCount(Map<String, String[]> condition);

    //分页查询每页记录
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}

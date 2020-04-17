package com.hsz.service;

import com.hsz.domain.Admin;
import com.hsz.domain.PageBean;
import com.hsz.domain.User;

import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     *登录方法
     * @param admin
     * @return
     */
    public Admin login(Admin admin);

    /**
     *保存user
     * @param user
     */
    public void addUser(User user);

    /**
     * 根据id删除user
     * @param id
     */
    void deleteUser(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除用户
     * @param ids
     */
    void delSelectedUser(String[] ids);

    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}

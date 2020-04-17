package com.hsz.service.impl;

import com.hsz.dao.UserDao;
import com.hsz.dao.impl.UserDaoImpl;
import com.hsz.domain.Admin;
import com.hsz.domain.PageBean;
import com.hsz.domain.User;
import com.hsz.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    public Admin login(Admin admin){

        return dao.findUserByUsernameAndPassword(admin.getUsername(),admin.getPassword());

    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] ids) {
        //1.遍历数组
        for (String id : ids) {
            //2.调用dao删除
            dao.delete(Integer.parseInt(id));
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {

        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);

        //1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<User>();
        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //3.调用dao查询总记录
        int totalCound = dao.findTotalCount(condition);
        pb.setTotalCount(totalCound);
        //4.调用dao查询List集合
        //计算开始的记录索引
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findByPage(start,rows,condition);
        pb.setList(list);
        //5.计算总页码
        int totalPage = totalCound % rows == 0 ? totalCound/rows : totalCound/rows + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}

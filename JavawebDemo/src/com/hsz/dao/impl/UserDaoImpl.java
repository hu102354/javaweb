package com.hsz.dao.impl;

import com.hsz.dao.UserDao;
import com.hsz.domain.Admin;
import com.hsz.domain.User;
import com.hsz.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Admin findUserByUsernameAndPassword(String username, String password) {

        try {
            //1.编写sql
            String sql = "select * from admin where username = ? and password = ?";
            //2.调用query方法
            Admin admin = template.queryForObject(sql,
                    new BeanPropertyRowMapper<Admin>(Admin.class),
                    username, password);
            return admin;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        //定义sql
        String sql = "insert into user values(null,?,?,?,?,?,?)";
        //执行sql
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void delete(int id) {
        //定义sql
        String sql = "delete from user where id = ?";
        //执行sql
        template.update(sql,id);
    }

    @Override
    public User findById(int id) {
        //定义sql
        String sql = "select * from user where id = ?";
        //执行sql
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);

        return user;
    }

    @Override
    public void update(User user) {
        //定义sql
        String sql = "update user set name = ? , gender = ? , age = ? , address = ? , qq = ? , email = ? where id = ?";
        //执行sql
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义模板初始化sql
        String sql = "select count(*) from user where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件值
            }
        }

        sql = sb.toString();

        //执行sql
        Integer integer = template.queryForObject(sql, Integer.class,params.toArray());
        return integer;
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        //定义sql
        String sql = "select * from user where  1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if (value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件值
            }
        }
        //添加分页查询
        sb.append(" limit ?,?");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);

        sql = sb.toString();

        //执行sql
        List<User> query = template.query(sql, new BeanPropertyRowMapper<User>(User.class),params.toArray());
        return query;
    }
}

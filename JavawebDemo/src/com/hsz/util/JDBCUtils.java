package com.hsz.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC工具类 使用Druid链接池
 */
public class JDBCUtils {

    private static DataSource ds;

    static {
        try {
            //1.加载配置文件
            Properties pro = new Properties();
            //使用ClassLoader加载配置文件，获取字节输入流
            pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.初始化链接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接池对象
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 获取链接Connection对象
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

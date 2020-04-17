package com.hsz.web.servlet;

import com.hsz.domain.Admin;
import com.hsz.service.UserService;
import com.hsz.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据
        //2.1获取用户填写的验证码
        String verifycode = request.getParameter("verifycode");
        //2.2.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //删除session中存入的验证码确保验证码一次性
        session.removeAttribute("CHECKCODE_SERVER");
        if (!checkcode_server.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误!");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();
        //3.封装Admin对象
        Admin admin = new Admin();
        try {
            BeanUtils.populate(admin,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //4.调用service查询
        UserService service = new UserServiceImpl();
        Admin loginuser = service.login(admin);
        //5.判断是否登录成功
        if (loginuser != null){
            //跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

package com.longqishi.jdbc.servlet;

import com.longqishi.jdbc.bean.User;
import com.longqishi.jdbc.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 注册Servlet
 *
 * @version 1.0
 */
public class RegistServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String real_name = request.getParameter("real_name");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        User user = userService.registCheck(username, password,real_name,birthday,phone,address);
        Boolean addUser=userService.addUser(user);

        if (user!=null&&addUser) {
            request.getSession().setAttribute("username", username);
            request.getRequestDispatcher("/login.do").forward(request, response);
        } else {
            System.out.println("用户名重复");
            request.getRequestDispatcher("/regPrompt.do").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}

package com.longqishi.jdbc.servlet;

import com.longqishi.jdbc.bean.User;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录跳转到留言板页面的Servlet
 * @version 1.0
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user=(User)request.getSession().getAttribute("user");
        String username=user.getName();
        request.getSession().invalidate();
        request.getRequestDispatcher("/message/list.do").forward(request, response);
    }

}

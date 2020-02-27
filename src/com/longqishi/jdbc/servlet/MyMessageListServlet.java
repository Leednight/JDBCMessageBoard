package com.longqishi.jdbc.servlet;

import com.longqishi.jdbc.bean.Message;
import com.longqishi.jdbc.bean.User;
import com.longqishi.jdbc.service.MessageService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 我的消息列表Servlet
 * @version 1.0
 */
public class MyMessageListServlet extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得当前登录的user
        User user = (User) request.getSession().getAttribute("user");
        String pageStr = request.getParameter("page");//当前页码
        int page = 1;//页码默认值为1
        if (null != pageStr && (!"".equals(pageStr))) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<Message> messages = messageService.getMyMessages(page, 5, user.getName());//分页查询全部留言
        int count = messageService.countMyMessages(user.getName());
        int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);
        request.setAttribute("last", last);
        request.setAttribute("messages", messages);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/WEB-INF/views/biz/my_message_list.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        messageService = null;
    }

}

package com.longqishi.jdbc.servlet;

import com.longqishi.jdbc.bean.Message;
import com.longqishi.jdbc.bean.User;
import com.longqishi.jdbc.service.MessageService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 留言处理Servlet
 *
 * @version 1.0
 */
public class MessageServlet extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService();
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathName = request.getServletPath();
        if (Objects.equals("/my/addMessagePrompt.do", pathName)) {
            request.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(request, response);
        } else if (Objects.equals("/my/addMessage.do", pathName)) {
            User user = (User)request.getSession().getAttribute("user");
            if (null == user) {
                request.getRequestDispatcher("/message/list.do").forward(request, response);
            } else {
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                boolean result = messageService.addMessage(new Message(user.getId(), user.getName(), title, content));
                if (result) {
                    request.getRequestDispatcher("/message/list.do").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(request, response);
                }
            }
        }else if (Objects.equals("/my/editMessagePrompt.do", pathName)) {
            Long id = Long.valueOf(request.getParameter("id"));
            request.getSession().setAttribute("id",id);
            Message message = messageService.getMessagesById(id);
            if(message!=null){
                request.getSession().setAttribute("message", message);
                request.getRequestDispatcher("/WEB-INF/views/biz/edit_message.jsp").forward(request, response);
            }else{
                request.getRequestDispatcher("/WEB-INF/views/biz/my/message_list.jsp").forward(request, response);
            }
            } else if (Objects.equals("/my/editMessage.do", pathName)) {
            Message message = (Message) request.getSession().getAttribute("message");
            String inputTitle = request.getParameter("title");
            String inputContent = request.getParameter("content");
            message.setTitle(inputTitle);
            message.setContent(inputContent);
            boolean result = messageService.editMessage(message);
            if(result) {
                //获得当前登录的user
                User user = (User) request.getSession().getAttribute("user");
                System.out.println(user.getName());
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
            }else {
                request.getRequestDispatcher("/WEB-INF/views/biz/edit_message.jsp").forward(request, response);
            }
        }else if (Objects.equals("/my/deleteMessage.do", pathName)) {
            System.out.println("我是servlet");
            Long id = Long.valueOf(request.getParameter("id"));
            request.getSession().setAttribute("id",id);
            Message message = (Message) request.getSession().getAttribute("message");
            boolean result = messageService.deleteMessage(id);
            if(result) {
                //获得当前登录的user
                User user = (User) request.getSession().getAttribute("user");
                System.out.println(user.getName());
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
            }else {
                request.getRequestDispatcher("/WEB-INF/views/biz/edit_message.jsp").forward(request, response);
            }
        }  else {
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        messageService = null;
    }
}

package com.longqishi.jdbc.service;

import com.longqishi.jdbc.bean.Message;
import com.longqishi.jdbc.bean.User;
import com.longqishi.jdbc.dao.MessageDAO;
import java.util.Date;
import java.util.List;

/**
 * 消息Service
 *
 * @version 1.0
 */
public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public boolean addMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDAO.save(message);
    }

    public boolean editMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDAO.edit(message);
    }

    public Message getMessagesById(Long id){
        return messageDAO.getMessageById(id);
    }

    public boolean deleteMessage(Long id) {
        return messageDAO.delete(id);
    }

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessages(int page, int pageSize) {
        return messageDAO.getMessages(page, pageSize);
    }

    /**
     * 分页查询我的全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @param username 登录用户
     * @return
     */
    public List<Message> getMyMessages(int page, int pageSize, String username) {
        return messageDAO.getMyMessages(page, pageSize, username);
    }

    /**
     * 计算我的留言数量
     * @return
     */
    public int countMyMessages(String username) {
        return messageDAO.countMyMessages(username);
    }

    /**
     * 计算所有留言数量
     * @return
     */
    public int countMessages() {
        return messageDAO.countMessages();
    }

}

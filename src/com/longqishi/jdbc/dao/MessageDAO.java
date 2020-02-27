package com.longqishi.jdbc.dao;

import com.longqishi.jdbc.bean.Message;
import com.longqishi.jdbc.common.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息DAO
 *
 * @version 1.0
 */
public class MessageDAO {

    /**
     * 保存留言信息
     * @param message
     * @return
     */
    public boolean save(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "insert into message(user_id, username, title, content, create_time) values (?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, message.getUserId());
            stmt.setString(2, message.getUsername());
            stmt.setString(3, message.getTitle());
            stmt.setString(4, message.getContent());
            stmt.setTimestamp(5, new Timestamp(message.getCreateTime().getTime()));
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("保存留言信息失败。");
            e.printStackTrace();
            return false;
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
        return true;
    }

    /**
     * 修改留言信息
     * @param message
     * @return
     */
    public boolean edit(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET title = ?, content = ?, create_time = ? where id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, message.getTitle());
            stmt.setString(2, message.getContent());
            stmt.setTimestamp(3, new Timestamp(message.getCreateTime().getTime()));
            stmt.setLong(4, message.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("修改留言信息失败。");
            e.printStackTrace();
            return false;
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
        return true;
    }

    /**
     * 删除留言信息
     * @param id
     * @return
     */
    public boolean delete(Long id) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "delete from message where id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("删除留言信息失败。");
            e.printStackTrace();
            return false;
        } finally {
            ConnectionUtil.release(null, stmt, conn);
        }
        return true;
    }

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessages(int page, int pageSize) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select * from message order by create_time desc limit ?, ?";//limit m, n：从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }

    /**
     * 分页查询我的全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @param username 登录用户
     * @return
     */
    public List<Message> getMyMessages(int page, int pageSize, String username) {
        System.out.println(username);
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select * from message where username = ? order by create_time desc limit ?, ?";//limit m, n：从第m条开始，取出总共n条记录
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Message> messages = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return messages;
    }

    /**
     * 计算我的留言数量
     * @return
     */
    public int countMyMessages(String username) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select count(username) AS mytotal from message where username = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("mytotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return 0;
    }

    /**
     * 计算所有留言数量
     * @return
     */
   public int countMessages() {
       Connection conn = ConnectionUtil.getConnection();
       String sql = "select count(*) AS total from message";
       PreparedStatement stmt = null;
       ResultSet rs = null;
       try {
           stmt = conn.prepareStatement(sql);
           rs = stmt.executeQuery();
           while (rs.next()) {
               return rs.getInt("total");
           }
       } catch (SQLException e) {
           e.printStackTrace();
       } finally {
           ConnectionUtil.release(rs, stmt, conn);
       }
       return 0;
   }

    /**
     * 查询留言为ID
     * @param id 查询id
     * @return
     */
    public Message getMessageById(Long id) {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "select * from message where id = ?";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Message message=null;
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if(rs.next()){
                message=new Message(rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("create_time"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.release(rs, stmt, conn);
        }
        return message;
    }

}

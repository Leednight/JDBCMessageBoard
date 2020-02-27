package com.longqishi.jdbc.service;

import com.longqishi.jdbc.bean.User;
import com.longqishi.jdbc.dao.UserDAO;
import java.sql.Date;

/**
 * UserService
 *
 * @version 1.0
 */
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功返回用户user，失败返回null
     */
    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    /**
     * 用户注册校验
     * @param username 用户名
     * @return 成功返回用户user，失败返回null
     */
    public User registCheck(String username, String password, String real_name, String birthday, String phone, String address) {
        return userDAO.registCheck(username, password,real_name,birthday,phone,address);
    }

    /**
     * 添加注册用户
     * @param user 用户
     * @return 成功返回用户user，失败返回null
     */
    public Boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

}

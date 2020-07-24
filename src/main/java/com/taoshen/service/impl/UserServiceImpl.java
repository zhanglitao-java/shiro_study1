package com.taoshen.service.impl;

import com.taoshen.dao.UserDao;
import com.taoshen.entity.Permission;
import com.taoshen.entity.Role;
import com.taoshen.entity.User;
import com.taoshen.service.UserService;
import com.taoshen.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户注册service
     * @param user
     */
    @Override
    public void register(User user) {
        //明文密码进行md5+salt+hash散列
        //1.生成随机salt 8位随机盐
        String salt = SaltUtils.getSalt(8);
        //2.将随机盐保存到数据库 User中已经存储了用户名和明文密码 再把salt也搞进去
        user.setSalt(salt);
        //3.明文密码进行md5+salt+hash散列 调用shiro提供的对象对明文密码进行加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        //加密好之后转换成十六进制 然后覆盖User之前的明文password
        user.setPassword(md5Hash.toHex());
        //保存dao数据库去
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findRolesByUsername(String username) {

        return userDao.findRolesByUsername(username);
    }

    @Override
    public List<Permission> findPermissionsByRoleId(String roleId) {
        return userDao.findPermissionsByRoleId(roleId);
    }
}

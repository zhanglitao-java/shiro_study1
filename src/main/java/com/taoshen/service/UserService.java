package com.taoshen.service;

import com.taoshen.entity.Permission;
import com.taoshen.entity.Role;
import com.taoshen.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    //注册用户
    public void register(User user);

    //根据用户名查询用户
    public User findByUsername(String username);

    //根据用户名查询当前用户的所有角色
    User findRolesByUsername(String username);

    //根据角色id查询 对应的所有权限
    List<Permission> findPermissionsByRoleId(String roleId);
}

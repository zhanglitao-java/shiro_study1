package com.taoshen.dao;

import com.taoshen.entity.Permission;
import com.taoshen.entity.Role;
import com.taoshen.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    void save(User user);

    User findByUsername(String username);

    //根据用户名查询所有角色
    User findRolesByUsername(String username);

    //根据角色id查询权限集合
    List<Permission> findPermissionsByRoleId(String id);
}

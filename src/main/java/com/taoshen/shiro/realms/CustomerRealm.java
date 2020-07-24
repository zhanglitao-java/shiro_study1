package com.taoshen.shiro.realms;

import com.taoshen.entity.Permission;
import com.taoshen.entity.Role;
import com.taoshen.entity.User;
import com.taoshen.service.UserService;
import com.taoshen.utils.ApplicationContextUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 自定义realm
 */
public class CustomerRealm extends AuthorizingRealm {
    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前用户主身份信息 也就是用户名
        String primaryPrincipal = (String) principals.getPrimaryPrincipal();
        System.out.println("调用授权验证："+primaryPrincipal);

        //根据主身份信息获取角色 和 权限信息
        UserService userService = (UserService)ApplicationContextUtils.getBean("userService");
        //根据用户名查询出当前用户
        User user = userService.findRolesByUsername(primaryPrincipal);

        //如果当前用户实体类中的List<Roles>不为null 说明该用户有角色权限赋予
        if(!CollectionUtils.isEmpty(user.getRoles())){
            //Simple的授权info对象
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //获取到List<Role> 然后遍历
            user.getRoles().forEach(role -> {
                //遍历的角色都添加进去
                simpleAuthorizationInfo.addRole(role.getName());

                //再对每一个Role 根据RoleId都查一下对应的Permission
                List<Permission> permissions = userService.findPermissionsByRoleId(role.getId());

                //如果查询出权限了 进行遍历
                if(!CollectionUtils.isEmpty(permissions)){
                    permissions.forEach(permission -> {
                        //将所有的权限字符串添加进去
                        simpleAuthorizationInfo.addStringPermission(permission.getName());
                    });
                }
            });
            //最终返回
            return simpleAuthorizationInfo;
        }

        //如果走到这 就说明鉴权失败 没有对应权限
        return null;
    }

    @Override
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从controller层中的token中调用函数获取到当前用户名
        String principal = (String)token.getPrincipal();

        //使用工具类获取Spring工厂容器 获取userService的bean
        UserService userService = (UserService) ApplicationContextUtils.getBean("userService");

        //从数据库中去按照用户名去查询User
        User user = userService.findByUsername(principal);

        //如果查询出的User对象不为null 就说明认证成功 返回一个SimpleAuthenticationInfo给controller中的subject.login(token)
        if(!ObjectUtils.isEmpty(user)){
            //参数分别是 用户名 加密后的密码  数据库中的盐特殊写法 realm数据源的名字
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        }

        //如果上面return没走 就说明认证失败 所以返回一个null给subject.login(token) 也就是认证失败
        return null;
    }
}

package org.example.login_app.service.servicelmpl;

import jakarta.annotation.Resource;
import org.example.login_app.domain.User;
import org.example.login_app.repository.UserDao;
import org.example.login_app.service.UserService;
import org.example.login_app.utils.MD5Hash;
import org.springframework.stereotype.Service;

@Service//用于标识一个类是一个服务类
public class UserServicelmpl implements UserService {
  @Resource
  private UserDao userDao;

    @Override//用于标识方法是重写父类或接口中的方法
    public User loginService(String uname, String password) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        User user = userDao.findByUnameAndPassword(uname, MD5Hash.md5Hash(password));
        // 重要信息置空
        if(user != null){
            user.setPassword("");
        }
         return user;
    }

    @Override
    public User registService(User user) {
        //当新用户的用户名已存在时
        if(userDao.findByUname(user.getUname())!=null){
            // 无法注册
            return null;
        }else{
            //返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
            newUser.setPassword(MD5Hash.md5Hash(user.getPassword()));
            if(newUser != null){
                newUser.setPassword("");
            }
            return newUser;
        }
    }
    @Override//用于标识方法是重写父类或接口中的方法
    public User emailLoginService(String email) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        User user = userDao.findByEmail(email);
        // 重要信息置空
        if(user != null){
            user.setPassword("");
        }
        return user;
    }
}

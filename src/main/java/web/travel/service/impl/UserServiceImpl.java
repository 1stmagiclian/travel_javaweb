package web.travel.service.impl;

import web.travel.dao.UserDao;
import web.travel.domain.User;
import web.travel.service.UserService;
import web.travel.util.MailUtils;
import web.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

//    private UserDao userDao = new UserDaoImpl();

    @Autowired
    private UserDao userDao;//通过注入的形式引入

    //我们根据UserServiceImpl的两个需求分别写两个Dao类的方法
    @Override
    public boolean regist(User user) {//注册用户
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null
        if(u != null){
            //用户名存在，注册失败
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送，邮件正文？

        String content="<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活【旅游网】</a>";

        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    @Override
    public boolean active(String code) {    //激活用户
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){
            //2.调用dao的修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User login(User user) {    //定义登录方法

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

}

package web.travel.web.servlet;

import web.travel.domain.Result;
import web.travel.domain.User;
import web.travel.service.UserService;
import web.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3.调用Service查询
        UserService service = new UserServiceImpl();
        User u  = service.login(user);

        Result result = new Result();

        //4.判断用户对象是否为null
        if(u == null){
            //用户名密码或错误
            result.setFlag(false);
            result.setErrorMsg("用户名密码或错误");
        }
        //5.判断用户是否激活
        if(u != null && !"Y".equals(u.getStatus())){
            //用户尚未激活
            result.setFlag(false);
            result.setErrorMsg("您尚未激活，请激活");
        }
        //6.判断登录成功
        if(u != null && "Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);//登录成功标记
            //登录成功
            result.setFlag(true);
        }
        //响应数据
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


//把验证码校验的代码写在最前面，如果验证码错误，就不必进行其他的校验
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();

        String checkCode_server = (String) session.getAttribute("checkCode_server");
        //为了保证验证码只能使用一次
        session.removeAttribute("checkCode_server");
        //比较
        if(checkCode_server == null || !checkCode_server.equalsIgnoreCase(check)){
            //验证码错误
            Result result = new Result();
            //注册失败
            result.setFlag(false);
            result.setErrorMsg("验证码错误");
            //将result对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        Result result = new Result();
        //4.响应结果
        if(flag){
            //注册成功
            result.setFlag(true);
        }else{
            //注册失败
            result.setFlag(false);
            result.setErrorMsg("注册失败!");
        }

        //将result对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(result);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}

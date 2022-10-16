package web.travel.dao.impl;

import web.travel.dao.UserDao;
import web.travel.domain.User;
import web.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        User user = null;

        //使用try-catch方法来捕捉异常，可以不用log.error打印异常日志
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }

        return user;
    }

    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into " +
                        "tab_user(" +
                            "username," +
                            "password," +
                            "name," +
                            "birthday," +
                            "sex," +
                            "telephone," +
                            "email," +
                            "status," +
                            "code) " +
                            "values(?,?,?,?,?,?,?,?,?)";

        //2.执行sql
        template.update(sql,user.getUsername(),
                    user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
                );
    }

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            //执行sql语句
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改指定用户激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = " update tab_user set status = 'Y' where uid=?";
        template.update(sql,user.getUid());
    }

    /**
     * 根据用户名和密码查询的方法
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
        } catch (Exception e) {

        }

        return user;
    }
}

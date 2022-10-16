package web.travel.service;

import web.travel.domain.User;

public interface UserService {
    /**
     * 注册用户
     * @athor zhanglian
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活用户
     * @athor zhanglian
     * @param code
     * @return
     */
    boolean active(String code);

    User login(User user);
}

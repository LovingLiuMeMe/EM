package cn.lovingliu.service;

import cn.lovingliu.pojo.User;
import cn.lovingliu.response.ServerResponse;

import java.util.List;

public interface UserService {
    User getUserByTelePhone(String phone);
    Integer createUser(User user);
    User getUserById(Integer id);
    Integer updateByUser(User user);
    ServerResponse blacklist(Integer userId);
    List<User> getUserByType(Integer type);
}

package cn.lovingliu.service.impl;

import cn.lovingliu.constant.UserType;
import cn.lovingliu.mapper.UserMapper;
import cn.lovingliu.pojo.User;
import cn.lovingliu.response.ServerResponse;
import cn.lovingliu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByTelePhone(String telePhone){
        User user = userMapper.selectByTelephone(telePhone);
        return user;
    }

    @Override
    public Integer createUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 加入/移除黑名单
     * @param userId
     * @return
     */
    @Override
    public ServerResponse blacklist(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        Integer type = user.getType();
        if(type != UserType.STUDENT || type != UserType.TEACHER){
            return ServerResponse.createByErrorMessage("权限不足,不能加入/移除黑名单");
        }
        Integer inBlacklist = user.getInBlacklist();
        user.setInBlacklist(1 - inBlacklist);
        String msg = "移除黑名单成功,该用户将可以租借任何器材！";
        if(inBlacklist == 0){
            msg = "加入黑名单成功,该用户将不能再租借任何器材！";
        }
        return ServerResponse.createBySuccessMessage(msg);
    }
}

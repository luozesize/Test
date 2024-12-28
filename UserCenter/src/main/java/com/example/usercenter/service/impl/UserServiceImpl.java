package com.example.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.domain.User;
import com.example.usercenter.service.UserService;
import com.example.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* &#064;description  针对表【user】的数据库操作Service实现
* &#064;createDate  2024-12-28 08:46:47
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userName, String password , String checkPassword) {

        //密码和校验码相同
        if (!StringUtils.equals(password,checkPassword)){
            return -1;
        }
        User user = new User();
        user.setUserName(userName);
        user.setUserAccount("");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassWord(password);
        user.setPhone("");
        user.setEmail("");
        user.setUserstatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        userMapper.insert(user);

        return 0;
    }
}





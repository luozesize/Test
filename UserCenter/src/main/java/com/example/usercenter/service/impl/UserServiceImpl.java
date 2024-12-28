package com.example.usercenter.service.impl;





import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.mapper.UserMapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

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
    public Long userRegister(String userAccount, String password, String checkPassword) throws Exception{

        //密码和校验码相同
        if (!StringUtils.equals(password,checkPassword)){
            System.out.println("密码和校验码不同");
            return -1L;
        }

        if (!checkMassage(userAccount,password)){
            System.out.println("用户名和密码校验不通过");

            return -1L;
        }

       if (getUser(userAccount) != null){
           System.out.println("用户已存在");

           return -1L;
       }



        password = getEncryptMassage(password);
        User user = new User();

        user.setUserName("");
        user.setUserAccount(userAccount);
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

        return null;
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest httpServletRequest) throws Exception {
        if (!checkMassage(userAccount,password)){
            System.out.println("用户名和密码校验不通过");

            return null;
        }




        User user = getUser(userAccount);
        if (user == null){
            System.out.println("用户不存在");

            return null;

        }


        password = getEncryptMassage(password);
        if (!StringUtils.equals(password,user.getUserPassWord())){
            return null;
        }



        user.setId(user.getId());
        user.setUserName(user.getUserName());
        user.setUserAccount(user.getUserAccount());
        user.setAvatarUrl(user.getAvatarUrl());
        user.setPhone("");
        user.setEmail("");
        user.setUpdateTime(new Date());
        httpServletRequest.getSession().setAttribute(USER_LOGIN_STATE,user);
        return user;
    }

    private  User getUser(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userName",userName);
        return userMapper.selectOne(queryWrapper);
    }

    private String getEncryptMassage(String password) throws Exception{
        String key = "1234567890123456"; // 16字符，对应128位
        String iv = "1234567890123456"; // 固定的IV

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(password.getBytes());

        // 输出加密结果
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private boolean checkMassage(String userName, String password) {

        //为空校验
        if (StringUtils.isAnyBlank(userName,password)){
            System.out.println("信息为空");

            return false;
        }

        //从长度校验
        if (StringUtils.length(userName)<4 || StringUtils.length(password)<8){
            System.out.println("长度不符合");

            return false;
        }

        //特殊字符校验
        ///校验是否包含特殊字符
        //正则表达式，匹配除了字母、数字和下划线以外的任何字符
        String specialCharRegex = "[^a-zA-Z0-9_]";
        // 使用Pattern和Matcher类来检查字符串
        Pattern pattern = Pattern.compile(specialCharRegex);
        // 如果找到匹配项，则表示字符串包含特殊字符

        if (pattern.matcher(userName).find() && pattern.matcher(password).find()){
            System.out.println("包含特殊字符");

            return false;
        }
        return true;
    }
}





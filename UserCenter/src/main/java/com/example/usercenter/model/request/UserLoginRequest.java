package com.example.usercenter.model.request;


import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 */

@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 123811L;

    private String userAccount, password;


}

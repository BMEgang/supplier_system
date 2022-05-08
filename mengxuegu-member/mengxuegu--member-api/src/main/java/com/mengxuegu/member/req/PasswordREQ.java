package com.mengxuegu.member.req;

import lombok.Data;

@Data
public class PasswordREQ {
    private Integer userId;
    private String password;
}

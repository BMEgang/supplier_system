package com.mengxuegu.member.req;

import lombok.Data;

import java.util.Date;

@Data
public class MemberREQ {
    private String name;
    private String cardNum;
    private String payType;
    private Date birthday;
}

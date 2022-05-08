package com.mengxuegu.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Accessors(chain = true)
@Data
@TableName("tb_member")
public class Member implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String cardNum;
    private String name;
    private Date birthday;
    private String phone;
    private Integer integral;
    private double money;
    private String payType;
    private String address;
}

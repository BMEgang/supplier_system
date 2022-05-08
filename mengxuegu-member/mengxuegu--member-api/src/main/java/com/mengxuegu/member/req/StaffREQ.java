package com.mengxuegu.member.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaffREQ implements Serializable {
    private String name;
    private String username;

}

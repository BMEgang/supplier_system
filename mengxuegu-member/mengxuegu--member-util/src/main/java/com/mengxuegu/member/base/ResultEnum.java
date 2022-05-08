package com.mengxuegu.member.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(2000, "success"),
    ERROR(999, "fail");

    private Integer code;
    private String desc;

}

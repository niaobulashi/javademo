package com.niaobulashi.microservices.sentinel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description: UserEntity
 * @date: 2023/11/15 20:52
 * @author: HuLang
 * @version: V1.0
 **/
@Data
@TableName("t_user")
public class UserEntity implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Integer age;
    private String email;
}

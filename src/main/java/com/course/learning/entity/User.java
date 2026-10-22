package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体
 * 表名 user 为 MySQL 保留字，映射为 t_user
 */
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户名（账号密码登录） */
    private String username;

    /** 密码（BCrypt 加密，微信用户可为空） */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 微信小程序 openid（唯一，支持微信端登录） */
    @Column(unique = true)
    private String wxOpenid;

    /** 头像 */
    private String avatar;

    /** 角色：student / teacher / admin */
    private String role = "student";

    /** 绑定班级 id */
    private Long classId;

    /** 创建时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}

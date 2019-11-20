package com.phpsong.user.api.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    /**
     * 用户名
     */
    @Length(max = 30, min = 4, message = "用户名长度只能在4-30之间")
    private String username;

    /**
     * 密码，加密存储
     */
    @JsonIgnore
    @Length(max = 30, min = 4, message = "密码长度只能在4-30之间")
    private String password;

    /**
     * 注册手机号
     */
    @Pattern(regexp = "^1[356789]\\d{9}$",message = "手机号格式不正确")
    private String phone;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 密码加密的salt值
     */
    @JsonIgnore
    private String salt;


}
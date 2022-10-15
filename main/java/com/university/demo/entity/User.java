package com.university.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sargeraswang.util.ExcelUtil.ExcelCell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author redcomet
 * @since 2021-04-01
 */

@TableName("tb_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(index = 0)
    private Integer id;


    private Integer groupId;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    @ExcelProperty(index = 1)
    private String realname;

//    /**
//     * 用户类型1
//     */
//    private Integer userType;

    /**
     * 身份证
     */
    @ExcelProperty(index = 3)
    private String idno;


    /**
     * 联系电话
     */
    @ExcelProperty(index = 6)
    private String phone;

    /**
     * 电子邮件
     */
    private String email;


    /**
     * 年龄
     */
    @ExcelProperty(index = 5)
    private Integer age;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String addr;

    /**
     * 签名
     */
    private String intro;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账号状态
     */
    private Integer status;

    @ExcelProperty(index = 7)
    private String roles;

    private String gender;  //0-男 1-女

    private Double bal; //钱包

    private String vip;

//    @TableField(value = "vip_date")
    private String vipDate;

//    private String provinceName;
//    private String provinceId;
//
//    private Double score;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

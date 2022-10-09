package com.university.demo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserXls extends BaseRowModel {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(index = 0)
    private Integer id;

    /**
     * 用户编号
     */
    private String userId;

    private Integer groupId;

    @ExcelProperty(index = 3)
    private String groupId2;  /*用来获取excel中的部门中文名*/

    @Override
    public String toString() {
        return "UserXls{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", groupId=" + groupId +
                ", groupId2='" + groupId2 + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", userType=" + userType +
                ", idno='" + idno + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", roles='" + roles + '\'' +
                ", gender=" + gender +
                ", gender2='" + gender2 + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    /**
     * 用户名
     */
    @ExcelProperty(index = 1)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    @ExcelProperty(index = 2)
    private String realname;

    /**
     * 用户类型1
     */
    private Integer userType;

    /**
     * 身份证
     */
    @ExcelProperty(index = 4)
    private String idno;


    /**
     * 联系电话
     */
    @ExcelProperty(index = 7)
    private String telephone;

    /**
     * 电子邮件
     */
    private String email;


    /**
     * 年龄
     */
    @ExcelProperty(index = 6)
    private Integer age;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 地址
     */
    private String address;

    /**
     * 签名
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账号状态
     */
    private Integer status;

    @ExcelProperty(index = 8)
    private String roles;

    private Integer gender;  //0-男 1-女

    @ExcelProperty(index = 5)
    private String gender2;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

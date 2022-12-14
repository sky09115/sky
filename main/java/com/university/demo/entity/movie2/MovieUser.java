package com.university.demo.entity.movie2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("movie_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieUser extends Model<MovieUser> {

    private static final long serialVersionUID=1L;

    @TableId()
    private Integer user_id;

    private String user_name;

    private String user_unique_name;

    private String account;

    private String password;

    private String email;

    private String phone;

    private String sex;

    private String birth;

    private Integer age;

    private String profession;

    private String user_head_portrait_url;

    private String user_url;
}

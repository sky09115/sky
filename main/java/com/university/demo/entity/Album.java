package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Album
 * @author
 * @since 2022年10月09日
 */

@TableName("tb_album")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album extends Model<Album> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "albumId")
    private String albumId;
    private String name;
    private String alia;
    private String pic;
    @TableField(value = "artistId")
    private String artistId;
    @TableField(value = "artistName")
    private String artistName;
    private String company;
    private String description;
    private String type;
    private String size;
    @TableField(value = "likedCount")
    private String likedCount;
    @TableField(value = "shareCount")
    private String shareCount;
    @TableField(value = "commentCount")
    private String commentCount;
    @TableField(value = "publishTime")
    private LocalDateTime publishTime;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

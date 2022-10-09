package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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

    private String albumId;
    private String name;
    private String alia;
    private String pic;
    private String artistId;
    private String artistName;
    private String company;
    private String description;
    private String type;
    private String size;
    private String likedCount;
    private String shareCount;
    private String commentCount;
    private LocalDateTime publishTime;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

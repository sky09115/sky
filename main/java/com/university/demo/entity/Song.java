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
 * Song2
 * @author
 * @since 2022年10月09日08:22:45
 */

@TableName("tb_song2")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song extends Model<Song> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "songId")
    private String songId;
    @TableField(value = "songName")
    private String songName;
    private String alia;
    private String pic;
    @TableField(value = "singerId")
    private String singerId;
    @TableField(value = "singerName")
    private String singerName;
    @TableField(value = "albumId")
    private String albumId;
    @TableField(value = "albumName")
    private String albumName;
    private String dt;
    private String pop;
    private String fee;
    private String mv;
    private String cd;
    private String no;
    @TableField(value = "originCoverType")
    private String originCoverType;
    @TableField(value = "publishTime")
    private LocalDateTime publishTime;
    private String mvUrl;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

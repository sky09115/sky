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

    private String songId;
    private String songName;
    private String alia;
    private String pic;
    private String singerId;
    private String singerName;
    private String albumId;
    private String albumName;
    private String dt;
    private String pop;
    private String fee;
    private String mv;
    private String cd;
    private String no;
    private String originCoverType;
    private LocalDateTime publishTime;
    private String mvUrl;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

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
 * Artist
 * @author
 * @since 2022年10月09日
 */

@TableName("tb_artist2")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Artist extends Model<Artist> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "artistId")
    private String artistId;
    @TableField(value = "artistName")
    private String artistName;
    private String alias;
    private String pic;
    @TableField(value = "briefDesc")
    private String briefDesc;
    @TableField(value = "musicSize")
    private Integer musicSize;
    @TableField(value = "albumSize")
    private Integer albumSize;
    @TableField(value = "mvSize")
    private Integer mvSize;
    private String status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

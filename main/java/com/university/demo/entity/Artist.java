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

    private String artistId;
    private String artistName;
    private String alias;
    private String pic;
    private String briefDesc;
    private Integer musicSize;
    private Integer albumSize;
    private Integer mvSize;
    private String status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

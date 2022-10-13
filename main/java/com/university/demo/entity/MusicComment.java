package com.university.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论 [音乐大数据专用]
 * @author tesla
 * @since 2022年10月13日
 */

@TableName("tb_comment2")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicComment extends Model<MusicComment> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "songId")
    private String songId;
    @TableField(value = "userId")
    private String userId;
    private String content;
    private String nickname;
    private String avatar;
    @TableField(value = "commentId")
    private String commentId;
    @TableField(value = "likedCount")
    private Integer likedCount;
    @TableField(value = "isHot")
    private String isHot;
    @TableField(value = "pubTime")
    private String pubTime;
    private String label;
    private Double score;

    @TableLogic
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

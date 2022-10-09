package com.university.demo.entity.response;

import com.university.demo.entity.Comment;
import com.university.demo.entity.News;

import java.util.List;

public class NewsVo extends News {
    private List<Comment> comments;

    private boolean ithumb;

    private boolean istar;

    private String tend;

    public String getTend() {
        return tend;
    }

    public void setTend(String tend) {
        this.tend = tend;
    }

    public boolean isIthumb() {
        return ithumb;
    }

    public void setIthumb(boolean ithumb) {
        this.ithumb = ithumb;
    }

    public boolean isIstar() {
        return istar;
    }

    public void setIstar(boolean istar) {
        this.istar = istar;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

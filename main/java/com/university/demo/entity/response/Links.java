package com.university.demo.entity.response;

public class Links {

    private String source;
    private String target;

    @Override
    public String toString() {
        return "Links{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}

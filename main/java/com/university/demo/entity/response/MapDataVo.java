package com.university.demo.entity.response;

public class MapDataVo {
    String name;
    Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MapDataVo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

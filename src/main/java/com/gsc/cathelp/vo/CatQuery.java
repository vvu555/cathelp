package com.gsc.cathelp.vo;

public class CatQuery {
    private String name;
    private Long typeId;

    public CatQuery() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "CatQuery{" +
                "name='" + name + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}

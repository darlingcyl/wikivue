package com.frank.wiki.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 假设用户可能会把id 和名字作为查询条件
 * 当然真实情况下可能远远不止两个
 */
//@Setter
//@Getter
public class EbookQueryReq extends PageReq{
    private Long id;

    private String name;

    private Long categoryId2;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(Long categoryId2) {
        this.categoryId2 = categoryId2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}
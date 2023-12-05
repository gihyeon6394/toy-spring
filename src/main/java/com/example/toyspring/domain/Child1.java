package com.example.toyspring.domain;

import com.example.toyspring.domain.id.Child1Id;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "CHILD_1")
@IdClass(Child1Id.class)
public class Child1 {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @Id
    private int childOrder;

    private String name;

    public Child1() {
    }

    public Child1(Parent parent, int childOrder, String name) {
        this.parent = parent;
        this.childOrder = childOrder;
        this.name = name;
    }



    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public int getChildOrder() {
        return childOrder;
    }

    public void setChildOrder(int childOrder) {
        this.childOrder = childOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}

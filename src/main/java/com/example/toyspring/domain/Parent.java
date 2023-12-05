package com.example.toyspring.domain;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PARENT")
public class Parent {

    @Id
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child1> child1s = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child2> child2s = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "GRAND_PARENT_ID")
    private GrandParent grandParent;

    public Parent() {
    }

    public Parent(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public List<Child1> getChildren() {
        return child1s;
    }

    public void setChildren(List<Child1> child1s) {
        this.child1s = child1s;
    }

    public List<Child1> getChild1s() {
        return child1s;
    }

    public void setChild1s(List<Child1> child1s) {
        this.child1s = child1s;
    }

    public List<Child2> getChild2s() {
        return child2s;
    }

    public void setChild2s(List<Child2> child2s) {
        this.child2s = child2s;
    }

    public GrandParent getGrandParent() {
        return grandParent;
    }

    public void setGrandParent(GrandParent grandParent) {
        this.grandParent = grandParent;
    }

    @Override
    public String toString() {
        return ToStringBuilder
                .reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}

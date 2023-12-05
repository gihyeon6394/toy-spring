package com.example.toyspring.domain.id;

import com.example.toyspring.domain.Parent;

import java.io.Serializable;

public class Child1Id implements Serializable {

    private Parent parent;

    private int childOrder;

    public Child1Id() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Child1Id child1Id = (Child1Id) o;

        if (childOrder != child1Id.childOrder) return false;
        return parent.equals(child1Id.parent);
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + childOrder;
        return result;
    }
}

package com.ntu.graphs.entity;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
public class Author {

    private Integer order;
    @TargetNode
    private final Person person;

    public Author(Integer order, Person person) {
        this.order = order;
        this.person = person;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "Author{" +
                "order=" + order +
                ", person=" + person +
                '}';
    }
}

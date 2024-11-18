package org.example.smackwebserver.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "product_comments")
@AttributeOverride(name = "parentId", column = @Column(name = "dynamic_id"))
public class DynamicComment extends Comment {
}

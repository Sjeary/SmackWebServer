package org.example.smackwebserver.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "product_comments")
@AttributeOverride(name = "parentId", column = @Column(name = "product_id"))
public class ProductComment extends Comment {
}

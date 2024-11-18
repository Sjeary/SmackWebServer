package org.example.smackwebserver.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "product_comments")
@AttributeOverride(name = "parentId", column = @Column(name = "spot_id"))
public class SpotComment extends Comment {
}

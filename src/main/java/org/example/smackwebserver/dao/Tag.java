package org.example.smackwebserver.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; // tag/节点ID

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name; // tag/节点名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @JsonBackReference // 指定为被控方
    private Set<Dynamic> dynamics = new HashSet<>();

    @JsonIgnoreProperties("tags")
    public Set<Dynamic> getDynamics() {
        return dynamics;
    }

    public void setDynamics(Set<Dynamic> dynamics) {
        this.dynamics = dynamics;
    }
}

package org.example.smackwebserver.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    boolean existsByName(String name);

    Page<Tag> findByNameContaining(String keyword, Pageable pageable);

    Page<Tag> findByUserId(long userId, Pageable pageable);
}

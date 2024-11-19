package org.example.smackwebserver.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicRepository extends JpaRepository<Dynamic, Long> {

    Page<Dynamic> findByUserId(long userId, Pageable pageable);

    @Query("SELECT d FROM Dynamic d JOIN d.tags t WHERE t.name = :tagName")
    Page<Dynamic> findByTagName(@Param("tagName") String tagName, Pageable pageable);

    @Query("SELECT d FROM Dynamic d JOIN d.tags t WHERE t.id = :tagId")
    Page<Dynamic> findByTagId(@Param("tagId") int tagId, Pageable pageable);
}

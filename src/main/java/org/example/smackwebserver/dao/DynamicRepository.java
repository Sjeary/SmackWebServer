package org.example.smackwebserver.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicRepository extends JpaRepository<Dynamic, Long> {

    List<Dynamic> findByUserId(long userId);

}

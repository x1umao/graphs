package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Journal;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends Neo4jRepository<Journal,Long> {
    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r")
    void deleteAll();

    @Query("match(j:Journal) where j.title starts with $keyword return j")
    List<Journal> findJournalsByKeyword(@Param("keyword") String keyword);
}

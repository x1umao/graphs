package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Journal;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends Neo4jRepository<Journal,Long> {
    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r")
    void deleteAll();
}

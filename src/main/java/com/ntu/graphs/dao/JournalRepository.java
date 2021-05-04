package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.vo.JournalListVO;
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

    @Query("match (j:Journal) where toLower(j.title) contains $keyword return count(j)")
    Long countByKeyword(String keyword);

    @Query("match (j:Journal) where toLower(j.title) contains $keyword return j skip $page limit 5")
    List<Journal> findByPage(@Param("page") int page, @Param("keyword") String keyword);

    @Query("MATCH p=()-[r:PUBLISHED_IN]->(:Journal{title: $title}) RETURN count(p)")
    Long countArticleByJournalTitle(@Param("title") String title);

    Journal findJournalByTitle(String title);

    @Query("match (:Article{title:$title})-[:PUBLISHED_IN]->(j:Journal) return j")
    Journal findJournalByArticleTitle(@Param("title") String title);
}

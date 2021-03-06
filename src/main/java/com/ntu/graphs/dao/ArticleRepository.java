package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Article;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends Neo4jRepository<Article, Long> {
    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r")
    void deleteAll();

    //靠name查找文章
    @Query("match p=(:Person {name: $name})-[:WROTE]->(:Article) return p")
    List<Article> findArticlesByAuthorName(@Param("name") String name);

    //query for list
    @Query("match (a:Article) where toLower(a.title) contains $keyword return a skip $page limit 5")
    List<Article> findByPage(@Param("page") int page, @Param("keyword") String keyword);

    @Query("match (p:Person)-[:WROTE{order:1}]->(:Article {title:$title}) return p.name")
    String findFirstAuthorByArticleName(@Param("title") String title);

    @Query("match (a:Article) return a")
    List<Article> findAll();

    @Query("match (:Article {title:$title})-[]->(j:Journal) return j.title")
    String findJournalByArticleName(@Param("title") String title);

    Article findArticleByTitle(String title);

    @Query("match p=(:Person{name:$name})-[:WROTE]->(a:Article) return count(a)")
    int countArticleByPersonName(@Param("name") String name);

    @Query("match (a:Article)-[:PUBLISHED_IN]->(j:Journal{title:$title}) return count(a)")
    int countArticlesByJournalTitle(@Param("title") String title);

    @Query("match (a:Article) where a.title starts with $keyword return a")
    List<Article> findArticlesByKeyword(@Param("keyword") String keyword);

    @Query("match (p:Person{name:$author}) " +
            "match (a:Article{title:$title}) " +
            "merge (p)-[:WROTE{order:$order}]->(a)")
    void saveWroteRelation(String title, String author, int order);

    @Query("match (a:Article{title:$article}) " +
            "match (j:Journal{title:$journal}) " +
            "merge (a)-[:PUBLISHED_IN]->(j)")
    void savePublishedInRelation(String article, String journal);

    @Query("match (a:Article) where toLower(a.title) contains $keyword return count(a)")
    long countByKeyword(String keyword);

    @Query("match p=(a:Article)-[:PUBLISHED_IN]->(j:Journal{title:$title}) return p")
    List<Article> findArticlesByJournal(String title);
}

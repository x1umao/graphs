package com.ntu.graphs.dao;

import com.ntu.graphs.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends Neo4jRepository<Person,Long> {

    Person findByName(String name);

    @Query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r")
    void deleteAll();

    boolean existsByName(String name);

    @Query("match (p:Person) where p.name contains $keyword return p skip $page limit 5")
    List<Person> findByPage(@Param("page") int page, @Param("keyword") String keyword);

    @Query("match p=(:Person{name:$name})-[:WROTE]->(a:Article) return count(a)")
    int countArticleByPersonName(@Param("name") String name);

    @Query("match (p:Person)-[w:WROTE]->(:Article{title:$title}) where not p.name = $name return p limit 5")
    List<Person> findRelatedPersonsByTitle(@Param("title") String title,@Param("name") String name);

    @Query("match (a:Person) where a.name starts with $keyword return a")
    List<Person> findPersonsByKeyword(@Param("keyword") String keyword);
}

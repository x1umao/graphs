package com.ntu.graphs.entity;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private int year;

    @Relationship(type = "PUBLISHED_IN")
    private Journal journal;

    @Relationship(type = "WROTE", direction = Relationship.Direction.INCOMING)
    private Set<Author> authors = new HashSet<>();


    public Article() {
    }

    public Article(String title) {
        this.title = title;
    }

    public Article(String title, Journal journal) {
        this.title = title;
        this.journal = journal;
    }

    public Long getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author){
        if(authors==null){
            authors = new HashSet<>();
        }
        authors.add(author);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", journal=" + journal +
                ", authors=" + authors +
                '}';
    }
}

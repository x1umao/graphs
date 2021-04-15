package com.ntu.graphs.controller;

import com.ntu.graphs.dao.AdminRepository;
import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.dao.JournalRepository;
import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Author;
import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.entity.Person;
import com.ntu.graphs.util.graphUtil;
import com.ntu.graphs.vo.EchartsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class HelloWorld {

    private final PersonRepository personRepository;
    private final JournalRepository journalRepository;
    private final ArticleRepository articleRepository;
    private final AdminRepository adminRepository;


    public HelloWorld(PersonRepository personRepository, JournalRepository journalRepository, ArticleRepository articleRepository, AdminRepository adminRepository) {
        this.personRepository = personRepository;
        this.journalRepository = journalRepository;
        this.articleRepository = articleRepository;
        this.adminRepository = adminRepository;

    }


    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/relation")
    public String setRelation(){
        personRepository.deleteAll();
        Article article = new Article("book");
        Journal journal = new Journal("science");
        article.setJournal(journal);
        for(int i = 0;i<10;i++) {
            Person person = new Person("person"+i,"male");
            article.addAuthor(new Author(i+1,person));
            personRepository.save(person);
        }
        articleRepository.save(article);
        Person xiaoming = personRepository.findByName("person0");
        for (int i = 0; i < 5; i++) {
            Article a = new Article("essay"+i);
            a.setYear(200*10+i);
            a.addAuthor(new Author(1,xiaoming));
            Journal j = new Journal("journal"+i);
            a.setJournal(j);
            articleRepository.save(a);
        }
        Article article1 = new Article("未知");
        articleRepository.save(article1);
        return "Inserted successfully";
    }

    @RequestMapping("/duplicate")
    public String isDuplicate(){
        personRepository.deleteAll();
        String name = "xiaoming";
        Person person = new Person(name, "male");
        personRepository.save(person);
        for(int i = 0;i<10;i++){
            if(!personRepository.existsByName(name)) {
                personRepository.save(person);
            }
        }
        return "duplicate test";
    }
    @RequestMapping("/count")
    public Long CountNodes(){
        for (int i = 0; i < 20; i++) {
            articleRepository.save(new Article("book"+i));
        }
        return personRepository.count();
    }


    @GetMapping("/article")
    public EchartsVO getArticle(){
        List<Article> all = articleRepository.findArticlesByAuthorName("person0");
        System.out.println(all);
        return graphUtil.articlesToEcharts(all);
    }
    @GetMapping("/user")
    public List<Article> getPerson(){
        return articleRepository.findArticlesByAuthorName("xiaoming0");
    }

}

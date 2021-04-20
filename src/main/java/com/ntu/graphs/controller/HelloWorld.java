package com.ntu.graphs.controller;

import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.dao.JournalRepository;
import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Author;
import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.entity.Person;
import com.ntu.graphs.util.CSVUtil;
import com.ntu.graphs.util.graphUtil;
import com.ntu.graphs.vo.EchartsVO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

@RestController

public class HelloWorld {

    private final PersonRepository personRepository;
    private final JournalRepository journalRepository;
    private final ArticleRepository articleRepository;

    private int counter;

    public HelloWorld(PersonRepository personRepository, JournalRepository journalRepository, ArticleRepository articleRepository) {
        this.personRepository = personRepository;
        this.journalRepository = journalRepository;
        this.articleRepository = articleRepository;
        counter = 0;
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

    @GetMapping("/parseCSV")
    public List<Article> parseCSV(){
        counter++;
        articleRepository.deleteAll();
        long start = System.currentTimeMillis();
        List<Article> articles = new ArrayList<>();

        String filePath = new File("").getAbsolutePath();
        String path = filePath + "\\DB\\database.csv";

        // header
        // year,article,journal,authors

        //store nodes
        Map<String,Person> personNodes = new HashMap<>();
        Set<Article> articleNodes = new HashSet<>();
        Set<Journal> journalNodes = new HashSet<>();

        //store relations
        Map<String,String[]> wroteMap = new HashMap<>();
        Map<String,String> publishInMap = new HashMap<>();

        int counter = 0;

        try (Reader in = new FileReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String year = record.get(0);
                String articleTitle = record.get(1);
                String journalTitle = record.get(2);
                String[] persons = record.get(3).split(";");
                counter += persons.length;
                String gender = record.get(4);
                if(gender.length()==0){
                    gender = "N.A";
                }
                //保存relation
                wroteMap.put(articleTitle,persons);

                //装载node
                Article article = new Article();
                article.setYear(Integer.parseInt(year));
                article.setTitle(articleTitle);
                articleNodes.add(article);

                if(persons.length!=0){
                    //update the first author
                    String firstAuthorName = persons[0];
                    personNodes.put(firstAuthorName,new Person(firstAuthorName,gender));

                    for(int i = 1;i<persons.length;i++){
                        personNodes.put(persons[i],new Person(persons[i],"N.A"));
                    }
                }

                if(journalTitle.length()!=0){
                    publishInMap.put(articleTitle,journalTitle);
                    journalNodes.add(new Journal(journalTitle));
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("person:"+personNodes.size());
        System.out.println("article:"+articleNodes.size());
        System.out.println("journal:"+journalNodes.size());
        System.out.println("wrote:"+wroteMap.size());
        System.out.println("published:"+publishInMap.size());

        personRepository.saveAll(personNodes.values());
        articleRepository.saveAll(articleNodes);
        journalRepository.saveAll(journalNodes);

        for(String title:wroteMap.keySet()){
            String[] authors = wroteMap.get(title);
            for(int i = 0;i<authors.length;i++){
                articleRepository.saveWroteRelation(title,authors[i],i);
            }
        }

        for(String articleTitle:publishInMap.keySet()){
            articleRepository.savePublishedInRelation(articleTitle,publishInMap.get(articleTitle));
        }
        System.out.println(System.currentTimeMillis()-start);
        return articles;
    }

}

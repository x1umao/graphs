package com.ntu.graphs.service;

import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Author;
import com.ntu.graphs.entity.Person;
import com.ntu.graphs.util.graphUtil;
import com.ntu.graphs.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long countNodes() {
        return articleRepository.count();
    }


    public void getListingModel(Model model, String keyword) {

        List<Article> articles = articleRepository.findByPage(0, keyword);
        List<ArticleListVO> articleListVOs = new ArrayList<>();
        System.out.println(articles.size());

        for (Article article : articles) {
            String title = article.getTitle();
            String name = articleRepository.findFirstAuthorByArticleName(title);
            String journalTitle = articleRepository.findJournalByArticleName(title);
            articleListVOs.add(new ArticleListVO(title, article.getYear(), journalTitle, name));
        }
        model.addAttribute("articles", articleListVOs);
        long totalNodes = articleRepository.countByKeyword(keyword);;
        model.addAttribute("totalNodes", totalNodes);
    }

    public List<Article> getArticlesByAuthorName(PersonDetailVO personDetailVO, String name) {
        return articleRepository.findArticlesByAuthorName(name);
    }

    public ArticleDetailVO getArticleDetailVO(String title) {
        ArticleDetailVO articleDetailVO = new ArticleDetailVO();

        //query article
        Article article = articleRepository.findArticleByTitle(title);

        //transform Graph
        List<Article> articles = new ArrayList<>();
        articles.add(article);
        EchartsVO echartsVO = graphUtil.articlesToEcharts(articles);
        System.out.println(echartsVO);


        //抽取related persons
        List<Author> authors = new ArrayList<>(article.getAuthors());
        //按照order排序
        authors.sort((o1, o2) -> {
            return o1.getOrder() - o2.getOrder();
        });
        //only keep firstAuthor in article object
        Set<Author> firstAuthor = new HashSet<>();
        if(authors.size()!=0){
            firstAuthor.add(authors.get(0));
        }

        article.setAuthors(firstAuthor);


        List<PersonListVO> relatedPersons = new ArrayList<>();
        //仅放前五人进入
        for (int i = 0; i < authors.size()&&i<5; i++) {
            Person person = authors.get(i).getPerson();
            PersonListVO personListVO = new PersonListVO(person.getName(),
                    person.getGender(),
                    articleRepository.countArticleByPersonName(person.getName()));
            relatedPersons.add(personListVO);
        }

        //count how many articles in journal
        int jCounter = 0;
        if(article.getJournal()!=null){
            jCounter = articleRepository.countArticlesByJournalTitle(article.getJournal().getTitle());
        }


        //push data into object
        articleDetailVO.setArticle(article);
        articleDetailVO.setjCounter(jCounter);
        articleDetailVO.setRelatedPersons(relatedPersons);
        articleDetailVO.setEchartsVO(echartsVO);

        return articleDetailVO;
    }

    public List<ArticleListVO> loadMoreListing(int page, String keyword) {
        List<Article> articles = articleRepository.findByPage(page*5,keyword);
        List<ArticleListVO> articleListVOs = new ArrayList<>();
        for (Article article : articles) {
            String title = article.getTitle();
            String name = articleRepository.findFirstAuthorByArticleName(title);
            String journalTitle = articleRepository.findJournalByArticleName(title);
            articleListVOs.add(new ArticleListVO(title, article.getYear(), journalTitle, name));
        }
        return articleListVOs;
    }

    public long countNodeByKeyword(String keyword) {
        return articleRepository.countByKeyword(keyword);
    }

    public void setCoauthorsToEchartsVO(List<Article> articles, EchartsVO echartsVO) {
        Map<String,String> link = new HashMap<>();
        Map<String,Map<String,String>> newNode = new HashMap<>();
        List<Map<String, String>> links = echartsVO.getLinks();
        List<Map<String, String>> nodes = echartsVO.getNodes();
        for(Article article:articles){
            System.out.println(article);
            String title = article.getTitle();
            String name = "";
            long articleId = article.getId();
            for(Author author: article.getAuthors()){
                name = author.getPerson().getName();
            }

            Set<Author> authors = articleRepository.findArticleByTitle(article.getTitle()).getAuthors();
            for(Author author:authors){
                long authorId = author.getPerson().getId();
                int order = author.getOrder();
                String authorName = author.getPerson().getName();
                if(authorName.equals(name)){
                    continue;
                }
                link.put("source",String.valueOf(authorId));
                link.put("target",String.valueOf(articleId));
                link.put("value",String.valueOf(order));
                links.add(new HashMap<>(link));

                Map<String,String> node = new HashMap<>();
                node.put("id",String.valueOf(authorId));
                node.put("name",authorName);
                node.put("gender",author.getPerson().getGender());
                node.put("category","3");
                newNode.put(authorName,new HashMap<>(node));
            }
        }
        nodes.addAll(newNode.values());
        echartsVO.setLinks(links);
        echartsVO.setNodes(nodes);

        Map<String,String> category = new HashMap<>();
        category.put("name","Co-Authors");
        echartsVO.getCategory().add(category);
    }

    public List<Article> getArticlesByJournalTitle(String title) {
        return articleRepository.findArticlesByJournal(title);
    }
}

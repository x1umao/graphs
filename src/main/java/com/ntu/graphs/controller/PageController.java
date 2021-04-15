package com.ntu.graphs.controller;

import com.ntu.graphs.entity.Article;
import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import com.ntu.graphs.util.graphUtil;
import com.ntu.graphs.vo.ArticleDetailVO;
import com.ntu.graphs.vo.EchartsVO;
import com.ntu.graphs.vo.PersonDetailVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


@Controller
public class PageController {

    private final ArticleService articleService;
    private final PersonService personService;
    private final JournalService journalService;

    public PageController(ArticleService articleService, PersonService personService, JournalService journalService) {
        this.articleService = articleService;
        this.personService = personService;
        this.journalService = journalService;
    }


    @GetMapping(value = {"/","index","index.html"})
    public String index(Model model) {
        model.addAttribute("person", personService.countNodes());
        model.addAttribute("article", articleService.countNodes());
        model.addAttribute("journal", journalService.countNodes());
        return "index";
    }


    @GetMapping("/listing")
    public String listing(@RequestParam("category") int category, Model model){
        //0:person, 1:article, 2:journal
        if(category == 0){
            personService.getListingModel(model,0);
            model.addAttribute("category",0);
        }else if(category == 1){
            articleService.getListingModel(model,0);
            model.addAttribute("category",1);
        }else if(category == 2){
            journalService.getListingModel(model,0);
            model.addAttribute("category",2);
        }
        return "listing";
    }

    @GetMapping("/person-detail")
    public String personDetail(@RequestParam("name") String name, Model model){
        PersonDetailVO personDetailVO = new PersonDetailVO();

        //query himself
        personService.getPersonAndCounter(personDetailVO,name);

        //query graph
        List<Article> articles = articleService.getArticlesByAuthorName(personDetailVO, name);
        EchartsVO echartsVO = graphUtil.articlesToEcharts(articles);
        personDetailVO.setEchartsVO(echartsVO);

        //query related persons
        Set<PersonListVO> relatedPerson = personService.getRelatedPersonByArticle(articles,name);
        personDetailVO.setRelatedPerson(relatedPerson);
        System.out.println(articles.size());

        model.addAttribute("pDetailVO",personDetailVO);

        return "person-detail";
    }

    @GetMapping("/article-detail")
    public String articleDetail(@RequestParam("title") String title, Model model){
        ArticleDetailVO articleDetailVO = articleService.getArticleDetailVO(title);
        System.out.println(articleDetailVO);
        model.addAttribute("aDetailVO",articleDetailVO);

        return "article-detail";
    }

}

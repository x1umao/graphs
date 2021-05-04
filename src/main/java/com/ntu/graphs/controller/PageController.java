package com.ntu.graphs.controller;

import com.ntu.graphs.entity.Article;
import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import com.ntu.graphs.util.graphUtil;
import com.ntu.graphs.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


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


    @GetMapping(value = {"/", "index", "index.html"})
    public String index(Model model) {
        model.addAttribute("person", personService.countNodes());
        model.addAttribute("article", articleService.countNodes());
        model.addAttribute("journal", journalService.countNodes());
        return "index";
    }


    @GetMapping("/listing")
    public String listing(@RequestParam("category") int category,
                          @RequestParam(value = "keyword", defaultValue = "") String keyword,
                          Model model) {
        //0:person, 1:article, 2:journal
        if (category == 0) {
            personService.getListingModel(model, keyword.toLowerCase());
            model.addAttribute("category", 0);
        } else if (category == 1) {
            articleService.getListingModel(model, keyword.toLowerCase());
            model.addAttribute("category", 1);
        } else if (category == 2) {
            journalService.getListingModel(model, keyword.toLowerCase());
            model.addAttribute("category", 2);
        }
        model.addAttribute("keyword", keyword);
        return "listing";
    }

    @GetMapping("/person-detail")
    public String personDetail(@RequestParam("name") String name, Model model) {
        PersonDetailVO personDetailVO = new PersonDetailVO();

        //query himself
        personService.getPersonAndCounter(personDetailVO, name);

        //query graph
        List<Article> articles = articleService.getArticlesByAuthorName(personDetailVO, name);
        EchartsVO echartsVO = graphUtil.articlesToEcharts(articles);
        articleService.setCoauthorsToEchartsVO(articles, echartsVO);//增加其他作者节点。
        personDetailVO.setEchartsVO(echartsVO);

        //query related persons
        Set<PersonListVO> relatedPerson = new HashSet<>(personService.getRelatedPersonByArticle(articles, name));
        personDetailVO.setRelatedPerson(relatedPerson);

        model.addAttribute("pDetailVO", personDetailVO);

        return "person-detail";
    }

    @GetMapping("/article-detail")
    public String articleDetail(@RequestParam("title") String title, Model model) {
        ArticleDetailVO articleDetailVO = articleService.getArticleDetailVO(title);
        model.addAttribute("aDetailVO", articleDetailVO);
        return "article-detail";
    }

    @GetMapping("/journal-detail")
    public String journalDetail(@RequestParam("title") String title, Model model) {
        JournalDetailVO journalDetailVO = new JournalDetailVO();

        journalDetailVO.setTitle(title);
        journalDetailVO.setCounter(journalService.countArticleByJournalTitle(title));

        //query graph
        List<Article> articles = articleService.getArticlesByJournalTitle(title);
        EchartsVO echartsVO = graphUtil.articlesToEcharts(articles);
        journalDetailVO.setEchartsVO(echartsVO);

        Set<PersonListVO> relatedPerson = new HashSet<>(personService.getRelatedPersonByArticle(articles, ""));
        journalDetailVO.setRelatedPerson(relatedPerson);

        model.addAttribute("jDetailVO", journalDetailVO);
        return "journal-detail";
    }

}

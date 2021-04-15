package com.ntu.graphs.controller;

import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import com.ntu.graphs.vo.ArticleListVO;
import com.ntu.graphs.vo.LoadMoreVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
public class DataController {

    private final ArticleService articleService;
    private final PersonService personService;
    private final JournalService journalService;

    public DataController(ArticleService articleService, PersonService personService, JournalService journalService) {
        this.articleService = articleService;
        this.personService = personService;
        this.journalService = journalService;
    }


    @RequestMapping("/loadmore")
    public LoadMoreVO loadMore(@RequestParam("category") int category,
                          @RequestParam("page") int page){
        LoadMoreVO loadMoreVO = new LoadMoreVO();
        if(category == 0){
            loadMoreVO.setPersonListVOS(personService.loadMoreListing(page));
        }else if(category == 1){
            loadMoreVO.setArticleListVOS(articleService.loadMoreListing(page));
        }else if(category == 2){
            journalService.loadMoreListing(page);
        }
        System.out.println(category);
        System.out.println(page);
        return loadMoreVO;
    }
}

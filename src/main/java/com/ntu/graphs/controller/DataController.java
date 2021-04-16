package com.ntu.graphs.controller;

import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import com.ntu.graphs.vo.ArticleListVO;
import com.ntu.graphs.vo.LoadMoreVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.web.bind.annotation.*;


import java.util.*;

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


    @GetMapping("/loadmore")
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

    @GetMapping("/search")
    public Map<String,Integer> search(@RequestParam("keyword") String keyword){
        Map<String,Integer> result = new LinkedHashMap<>();
        personService.searchKeyword(result,keyword);
        if(result.size()<5){
            articleService.searchKeyword(result,keyword);
        }
//        if(result.size()<5){
//            journalService.searchKeyword(result,keyword);
//        }
        return result;
    }

}

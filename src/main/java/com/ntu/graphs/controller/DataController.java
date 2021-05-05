package com.ntu.graphs.controller;

import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import com.ntu.graphs.vo.LoadMoreVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

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
                               @RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam("page") int page) {
        LoadMoreVO loadMoreVO = new LoadMoreVO();
        if (category == 0) {
            loadMoreVO.setPersonListVOS(personService.loadMoreListing(page, keyword));
        } else if (category == 1) {
            loadMoreVO.setArticleListVOS(articleService.loadMoreListing(page, keyword));
        } else if (category == 2) {
            loadMoreVO.setJournalListVOS(journalService.loadMoreListing(page, keyword));
        }
        return loadMoreVO;
    }

    @GetMapping("/search")
    public Map<String, Long> search(@RequestParam("keyword") String keyword) {
        Map<String, Long> result = new LinkedHashMap<>();
        keyword = keyword.toLowerCase();
        result.put("person", personService.countNodeByKeyword(keyword));
        result.put("article", articleService.countNodeByKeyword(keyword));
        result.put("journal", journalService.countNodeByKeyword(keyword));
        return result;
    }

}

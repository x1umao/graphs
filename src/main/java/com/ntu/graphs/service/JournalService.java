package com.ntu.graphs.service;

import com.ntu.graphs.dao.JournalRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.vo.JournalDetailVO;
import com.ntu.graphs.vo.JournalListVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class JournalService {
    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Long countNodes() {
        return journalRepository.count();
    }

    public void getListingModel(Model model, String keyword) {
        List<Journal> journals = journalRepository.findByPage(0,keyword);
        List<JournalListVO> journalListVOs = new ArrayList<>();
        for(Journal j: journals){
            JournalListVO journalListVO = new JournalListVO(j.getTitle(),journalRepository.countArticleByJournalTitle(j.getTitle()));
            journalListVOs.add(journalListVO);
        }

        model.addAttribute("journals",journalListVOs);
        model.addAttribute("totalNodes",journalRepository.countByKeyword(keyword));
        model.addAttribute("category",2);
    }

    public List<JournalListVO> loadMoreListing(int page, String keyword) {
        List<Journal> journals = journalRepository.findByPage(page*5,keyword);
        List<JournalListVO> journalListVOs = new ArrayList<>();
        for(Journal j:journals){
            JournalListVO journalListVO = new JournalListVO(j.getTitle(),journalRepository.countArticleByJournalTitle(j.getTitle()));
            journalListVOs.add(journalListVO);
        }
        return journalListVOs;
    }

    public void searchKeyword(Map<String, Integer> result, String keyword) {
        List<Journal> journals = journalRepository.findJournalsByKeyword(keyword);
        for(Journal j:journals){
            if(result.size()==5){
                return;
            }
            result.put(j.getTitle(),2);
        }
    }

    public long countNodeByKeyword(String keyword) {
        return journalRepository.countByKeyword(keyword);
    }

    public Long countArticleByJournalTitle(String title) {
        return journalRepository.countArticleByJournalTitle(title);
    }
}

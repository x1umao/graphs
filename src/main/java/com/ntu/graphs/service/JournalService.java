package com.ntu.graphs.service;

import com.ntu.graphs.dao.JournalRepository;
import com.ntu.graphs.entity.Journal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    public void getListingModel(Model model) {
    }

    public void loadMoreListing(int page) {
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

    public Long countNodeByKeyword(String keyword) {
        return 0L;
    }
}

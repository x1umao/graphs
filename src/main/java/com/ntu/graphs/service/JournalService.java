package com.ntu.graphs.service;

import com.ntu.graphs.dao.JournalRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class JournalService {
    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Long countNodes() {
        return journalRepository.count();
    }

    public void getListingModel(Model model, int page) {
    }
}

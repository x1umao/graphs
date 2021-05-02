package com.ntu.graphs.vo;

import java.util.List;

public class LoadMoreVO {
    private List<PersonListVO> personListVOS;
    private List<ArticleListVO> articleListVOS;
    private List<JournalListVO> journalListVOS;

    public LoadMoreVO() {
    }

    public List<PersonListVO> getPersonListVOS() {
        return personListVOS;
    }

    public void setPersonListVOS(List<PersonListVO> personListVOS) {
        this.personListVOS = personListVOS;
    }

    public List<ArticleListVO> getArticleListVOS() {
        return articleListVOS;
    }

    public void setArticleListVOS(List<ArticleListVO> articleListVOS) {
        this.articleListVOS = articleListVOS;
    }

    public List<JournalListVO> getJournalListVOS() {
        return journalListVOS;
    }

    public void setJournalListVOS(List<JournalListVO> journalListVOS) {
        this.journalListVOS = journalListVOS;
    }

    @Override
    public String toString() {
        return "LoadMoreVO{" +
                "personListVOS=" + personListVOS +
                ", articleListVOS=" + articleListVOS +
                ", journalListVOS=" + journalListVOS +
                '}';
    }
}

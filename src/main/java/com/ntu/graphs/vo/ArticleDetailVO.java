package com.ntu.graphs.vo;

import com.ntu.graphs.entity.Article;

import java.util.List;

public class ArticleDetailVO {
    private Article article;//仅存放第一作者
    private int jCounter;//the number of article of journal
    private List<PersonListVO> relatedPersons;//除第一作者外的其他作者
    private EchartsVO echartsVO;

    public ArticleDetailVO() {
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getjCounter() {
        return jCounter;
    }

    public void setjCounter(int jCounter) {
        this.jCounter = jCounter;
    }

    public List<PersonListVO> getRelatedPersons() {
        return relatedPersons;
    }

    public void setRelatedPersons(List<PersonListVO> relatedPersons) {
        this.relatedPersons = relatedPersons;
    }

    public EchartsVO getEchartsVO() {
        return echartsVO;
    }

    public void setEchartsVO(EchartsVO echartsVO) {
        this.echartsVO = echartsVO;
    }

    @Override
    public String toString() {
        return "ArticleDetailVO{" +
                "article=" + article +
                ", jCounter=" + jCounter +
                ", relatedPersons=" + relatedPersons +
                ", echartsVO=" + echartsVO +
                '}';
    }
}

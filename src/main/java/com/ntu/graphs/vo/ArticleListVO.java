package com.ntu.graphs.vo;

public class ArticleListVO {
    private String title;
    private int year;
    private String journalTitle;
    private String firstAuthor;

    public ArticleListVO(String title, int year, String journalTitle, String firstAuthor) {
        this.title = title;
        this.year = year;
        this.journalTitle = journalTitle;
        this.firstAuthor = firstAuthor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getJournalTitle() {
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getFirstAuthor() {
        return firstAuthor;
    }

    public void setFirstAuthor(String firstAuthor) {
        this.firstAuthor = firstAuthor;
    }

    @Override
    public String toString() {
        return "ArticleListVO{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", journalTitle='" + journalTitle + '\'' +
                ", firstAuthor='" + firstAuthor + '\'' +
                '}';
    }
}

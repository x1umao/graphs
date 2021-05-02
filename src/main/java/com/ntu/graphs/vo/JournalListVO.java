package com.ntu.graphs.vo;

public class JournalListVO {
    private String title;
    private Long counter;

    public JournalListVO(String title, Long counter) {
        this.title = title;
        this.counter = counter;
    }

    public JournalListVO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "JournalListVO{" +
                "title='" + title + '\'' +
                ", counter=" + counter +
                '}';
    }
}

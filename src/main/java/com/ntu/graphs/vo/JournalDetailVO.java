package com.ntu.graphs.vo;

import java.util.Set;

public class JournalDetailVO {

    private String title;
    private Long counter;
    private Set<PersonListVO> relatedPerson;
    private EchartsVO echartsVO;

    public JournalDetailVO() {
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

    public Set<PersonListVO> getRelatedPerson() {
        return relatedPerson;
    }

    public void setRelatedPerson(Set<PersonListVO> relatedPerson) {
        this.relatedPerson = relatedPerson;
    }

    public EchartsVO getEchartsVO() {
        return echartsVO;
    }

    public void setEchartsVO(EchartsVO echartsVO) {
        this.echartsVO = echartsVO;
    }

    @Override
    public String toString() {
        return "JournalDetailVO{" +
                "journal=" + title +
                ", counter=" + counter +
                ", relatedPerson=" + relatedPerson +
                ", echartsVO=" + echartsVO +
                '}';
    }
}

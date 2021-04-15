package com.ntu.graphs.vo;

import com.ntu.graphs.entity.Person;

import java.util.Set;


public class PersonDetailVO {

    private Person person;
    private Integer counter;
    private Set<PersonListVO> relatedPerson;
    private EchartsVO echartsVO;

    public PersonDetailVO() {
        counter = 0;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
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
}

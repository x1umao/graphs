package com.ntu.graphs.service;

import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Person;
import com.ntu.graphs.vo.PersonDetailVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Long countNodes() {
        return personRepository.count();
    }

    //per 5 persons
    public void getListingModel(Model model) {
        List<Person> persons = personRepository.findByPage(0);
        List<PersonListVO> personListVOSs = new ArrayList<>();
        long totalNodes = 0;
        for(Person p:persons){
            PersonListVO personListVO = new PersonListVO(p.getName(),p.getGender(), personRepository.countArticleByPersonName(p.getName()));
            personListVOSs.add(personListVO);
        }
        model.addAttribute("persons",personListVOSs);
        totalNodes = personRepository.count();
        model.addAttribute("totalNodes",totalNodes);
        model.addAttribute("category",0);
    }

    public void getPersonAndCounter(PersonDetailVO personDetailVO, String name) {
        Person person = personRepository.findByName(name);
        int counter = personRepository.countArticleByPersonName(name);
        personDetailVO.setPerson(person);
        personDetailVO.setCounter(counter);
    }


    public Set<PersonListVO> getRelatedPersonByArticle(List<Article> articles, String name) {
        Set<PersonListVO> personListVOs = new HashSet<>();
        for(Article article: articles){
            List<Person> persons = personRepository.findRelatedPersonsByTitle(article.getTitle(),name);
            for(Person person:persons){
                int counter = personRepository.countArticleByPersonName(person.getName());
                personListVOs.add(new PersonListVO(person.getName(),person.getGender(),counter));
                if(personListVOs.size()==5){
                    return personListVOs;
                }
            }
        }
        return personListVOs;
    }

    public List<PersonListVO> loadMoreListing(int page) {
        List<Person> persons = personRepository.findByPage(page*5);
        List<PersonListVO> personListVOSs = new ArrayList<>();
        long totalNodes = 0;
        for(Person p:persons){
            PersonListVO personListVO = new PersonListVO(p.getName(),p.getGender(), personRepository.countArticleByPersonName(p.getName()));
            personListVOSs.add(personListVO);
        }
        return personListVOSs;
    }
}

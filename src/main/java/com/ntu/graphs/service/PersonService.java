package com.ntu.graphs.service;

import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Person;
import com.ntu.graphs.vo.PersonDetailVO;
import com.ntu.graphs.vo.PersonListVO;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

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
    public void getListingModel(Model model, String keyword) {
        List<Person> persons = personRepository.findByPage(0,keyword);
        List<PersonListVO> personListVOs = new ArrayList<>();
        for(Person p:persons){
            PersonListVO personListVO = new PersonListVO(p.getName(),p.getGender(), personRepository.countArticleByPersonName(p.getName()));
            personListVOs.add(personListVO);
        }
        model.addAttribute("persons",personListVOs);
        model.addAttribute("totalNodes",personRepository.countByKeyword(keyword));
        model.addAttribute("category",0);
    }

    public void getPersonAndCounter(PersonDetailVO personDetailVO, String name) {
        Person person = personRepository.findByName(name);
        int counter = personRepository.countArticleByPersonName(name);
        personDetailVO.setPerson(person);
        personDetailVO.setCounter(counter);
    }


    public Collection<PersonListVO> getRelatedPersonByArticle(List<Article> articles, String name) {
        Map<String,PersonListVO> personListVOs = new HashMap<>();
        for(Article article: articles){
            List<Person> persons = personRepository.findRelatedPersonsByTitle(article.getTitle(),name);
            for(Person person:persons){
                int counter = personRepository.countArticleByPersonName(person.getName());
                personListVOs.put(person.getName(),new PersonListVO(person.getName(),person.getGender(),counter));
                if(personListVOs.size()==5){
                    return personListVOs.values();
                }
            }
        }
        return personListVOs.values();
    }

    public List<PersonListVO> loadMoreListing(int page, String keyword) {
        List<Person> persons = personRepository.findByPage(page*5,keyword);
        List<PersonListVO> personListVOs = new ArrayList<>();
        for(Person p:persons){
            PersonListVO personListVO = new PersonListVO(p.getName(),p.getGender(), personRepository.countArticleByPersonName(p.getName()));
            personListVOs.add(personListVO);
        }
        return personListVOs;
    }

    public Long countNodeByKeyword(String keyword) {
        return personRepository.countByKeyword(keyword);
    }
}

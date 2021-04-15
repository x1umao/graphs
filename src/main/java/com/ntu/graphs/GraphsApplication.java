package com.ntu.graphs;

import com.ntu.graphs.dao.AdminRepository;
import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Admin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import static com.ntu.graphs.util.shellUtil.shellTestOnWin;

@SpringBootApplication
@EnableNeo4jRepositories
public class GraphsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphsApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(PersonRepository personRepository, AdminRepository adminRepository, ArticleRepository articleRepository){
        return args -> {

            shellTestOnWin();

            personRepository.deleteAll();
            Admin xiaoming = new Admin("xiaoming", "123456");
            adminRepository.save(xiaoming);

        };
    }
}

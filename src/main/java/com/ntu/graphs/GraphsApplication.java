package com.ntu.graphs;

import com.ntu.graphs.dao.AdminRepository;
import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Admin;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
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

    @Bean
    //配置http某个端口自动跳转https
    public TomcatServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {

            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcat;
    }

    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //监听的http端口
        connector.setPort(80);
        connector.setSecure(false);
        //跳转的https端口
        connector.setRedirectPort(443);
        return connector;
    }

}

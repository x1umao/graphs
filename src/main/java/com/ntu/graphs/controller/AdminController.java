package com.ntu.graphs.controller;

import com.ntu.graphs.entity.User;
import com.ntu.graphs.service.ArticleService;
import com.ntu.graphs.service.FileService;
import com.ntu.graphs.service.JournalService;
import com.ntu.graphs.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
public class AdminController {

    private final ArticleService articleService;
    private final PersonService personService;
    private final JournalService journalService;
    private final FileService fileService;

    public AdminController(ArticleService articleService, PersonService personService, JournalService journalService, FileService fileService) {
        this.articleService = articleService;
        this.personService = personService;
        this.journalService = journalService;
        this.fileService = fileService;
    }

    @GetMapping("/admin-dash")
    public String adminDash(Model model, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("username",user.getUsername());
        model.addAttribute("person", personService.countNodes());
        model.addAttribute("article", articleService.countNodes());
        model.addAttribute("journal", journalService.countNodes());
        return "admin-dash";
    }

    /**
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
        System.out.println(file);
        if (file.isEmpty()) {
            return "Please upload a new DB file";
        }else if (fileService.getLock()){
            return "The database is being written";
        }
        else{
            return fileService.upload(file);
        }
    }

    @GetMapping("/admin-validate")
    public String dataValidate(Model model){
        fileService.validate(model);
        return "admin-validate";
    }

    @GetMapping("/invalid")
    @ResponseBody
    public String invalid(){
        return fileService.invalid();
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateNeo4j(){
        if(fileService.lock()){
            fileService.updateNeo4j();
            return "ok";
        }else{
            return "no";
        }
    }

    @GetMapping("/download")
    @ResponseBody
    public String downloadFile(HttpServletResponse response) {
        return fileService.download(response);
    }


}

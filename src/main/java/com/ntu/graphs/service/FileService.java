package com.ntu.graphs.service;

import com.ntu.graphs.dao.ArticleRepository;
import com.ntu.graphs.dao.JournalRepository;
import com.ntu.graphs.dao.PersonRepository;
import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.entity.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FileService {

    //store nodes
    private final Map<String, Person> personNodes;
    private final Map<String,Article> articleNodes;
    private final Map<String,Journal> journalNodes;

    //store relations
    private final Map<String, String[]> wroteMap;
    private final Map<String, String> publishInMap;

    private boolean lock;// locked: true, unlocked: false

    private final ArticleRepository articleRepository;
    private final PersonRepository personRepository;
    private final JournalRepository journalRepository;


    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    public FileService(ArticleRepository articleRepository, PersonRepository personRepository, JournalRepository journalRepository) {
        this.lock = false;
        personNodes = new HashMap<>();
        articleNodes = new HashMap<>();
        journalNodes = new HashMap<>();
        wroteMap = new HashMap<>();
        publishInMap = new HashMap<>();
        this.articleRepository = articleRepository;
        this.personRepository = personRepository;
        this.journalRepository = journalRepository;
    }

    public String upload(MultipartFile file) {
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("File format is：" + suffixName);
            // 设置文件存储路径
            String filePath = new File("").getAbsolutePath();
            String path = filePath + File.separator + "DB" + File.separator + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            return "success";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return "Fail to upload.";
    }

    public String download(HttpServletResponse response){
        String fileName = "database.csv";// 文件名
        if (fileName != null) {
            //设置文件路径
            String filePath = new File("").getAbsolutePath();
            File file = new File(filePath + File.separator + "DB" + File.separator + fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    return "success";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return "fail";
    }


    public void validate(Model model) {

        int counter = parseCSV();
        model.addAttribute("counter", counter);
        model.addAttribute("person", personNodes.size());
        model.addAttribute("article", articleNodes.size());
        model.addAttribute("journal", journalNodes.size());

    }

    public int parseCSV(){
        String filePath = new File("").getAbsolutePath();
        String path = filePath + File.separator + "DB" + File.separator + "database.csv";

        // header
        // year,article,journal,authors

        int counter = 0;
        try (Reader in = new FileReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                counter++;
                String year = record.get(0);
                String articleTitle = record.get(1);
                String journalTitle = record.get(2);
                String[] persons = record.get(3).split(";");
                String gender = record.get(4);
                if (gender.length() == 0) {
                    gender = "N.A";
                }
                //保存relation
                wroteMap.put(articleTitle, persons);

                //装载node
                if(!articleNodes.containsKey(articleTitle)){
                    Article article = new Article();
                    article.setYear(Integer.parseInt(year));
                    article.setTitle(articleTitle);
                    articleNodes.put(articleTitle,article);
                }


                if (persons.length != 0) {
                    //update the first author
                    String firstAuthorName = persons[0];
                    personNodes.put(firstAuthorName, new Person(firstAuthorName, gender));

                    for (int i = 1; i < persons.length; i++) {
                        personNodes.put(persons[i], new Person(persons[i], "N.A"));
                    }
                }

                if (journalTitle.length() != 0) {
                    publishInMap.put(articleTitle, journalTitle);
                    if(!journalNodes.containsKey(journalTitle)){
                        journalNodes.put(journalTitle,new Journal(journalTitle));
                    }
                }

            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return counter;
    }

    public void updateNeo4j() {
        System.out.println(personNodes.size());
        System.out.println(articleNodes.size());
        System.out.println(journalNodes.size());
        try {
            articleRepository.deleteAll();
            personRepository.saveAll(personNodes.values());
            articleRepository.saveAll(articleNodes.values());
            journalRepository.saveAll(journalNodes.values());

            for (String title : wroteMap.keySet()) {
                String[] authors = wroteMap.get(title);
                for (int i = 0; i < authors.length; i++) {
                    articleRepository.saveWroteRelation(title, authors[i], i+1);
                }
            }

            for (String articleTitle : publishInMap.keySet()) {
                articleRepository.savePublishedInRelation(articleTitle, publishInMap.get(articleTitle));
            }
        } finally {
            lock = false;
        }


    }

    public boolean lock() {
        if (lock) {
            return false;
        } else {
            lock = true;
            return true;
        }
    }

    public boolean getLock() {
        return lock;
    }

    public String invalid() {
        personNodes.clear();
        articleNodes.clear();
        journalNodes.clear();

        //store relations
        wroteMap.clear();
        publishInMap.clear();
        return "ok";
    }
}

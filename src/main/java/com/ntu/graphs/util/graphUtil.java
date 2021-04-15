package com.ntu.graphs.util;

import com.ntu.graphs.entity.Article;
import com.ntu.graphs.entity.Author;
import com.ntu.graphs.entity.Journal;
import com.ntu.graphs.vo.EchartsVO;

import java.util.*;
// Person:0 Article:1 Journal:2
public class graphUtil {
    public static EchartsVO articlesToEcharts(List<Article> articles){
        EchartsVO echartsVO = new EchartsVO();
        Map<Long, Map<String,String>> nodesMap = new HashMap<>();
        Map<Long, Map<String,String>> linksMap = new HashMap<>();

        for(Article article:articles){
            Map<String, String> node = new LinkedHashMap<>();
            Map<String, String> link = new LinkedHashMap<>();
            //保存article节点
            long articleId = article.getId();
            if(!nodesMap.containsKey(articleId)){
                node.put("id",String.valueOf(article.getId()));
                node.put("name",article.getTitle());
                node.put("value",String.valueOf(article.getYear()));
                node.put("category","1");
                nodesMap.put(article.getId(),new LinkedHashMap<>(node));
                node.clear();
            }
            //保存journal节点
            Journal journal = article.getJournal();
            if(journal!=null){
                long journalId = journal.getId();
                if(!nodesMap.containsKey(journalId)){
                    node.put("id",String.valueOf(journalId));
                    node.put("name",journal.getTitle());
                    node.put("category","2");
                    nodesMap.put(journalId,new LinkedHashMap<>(node));
                    node.clear();
                }
                //保存PUBLICAN_IN关系
                link.put("source",String.valueOf(articleId));
                link.put("target",String.valueOf(journalId));
                linksMap.put(articleId*10+journalId,new LinkedHashMap<>(link));
                link.clear();
            }


            //保存person节点
            Set<Author> authors = article.getAuthors();
            for(Author author:authors){
                long id = author.getPerson().getId();
                if(!nodesMap.containsKey(id)){
                    node.put("id",String.valueOf(id));
                    node.put("name",author.getPerson().getName());
                    node.put("gender",author.getPerson().getGender());
                    node.put("category","0");
                    nodesMap.put(id,new LinkedHashMap<>(node));
                    node.clear();
                }
                //添加wrote relation
                link.put("source",String.valueOf(id));
                link.put("target",String.valueOf(articleId));
                linksMap.put(id*10+articleId,new LinkedHashMap<>(link));
                link.clear();
            }
        }

        //将数据封装进VO
        echartsVO.setNodes(new ArrayList<>(nodesMap.values()));
        echartsVO.setLinks(new ArrayList<>(linksMap.values()));

        return echartsVO;
    }
}

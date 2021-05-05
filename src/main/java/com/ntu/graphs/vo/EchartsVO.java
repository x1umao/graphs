package com.ntu.graphs.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EchartsVO {

    private List<Map<String, String>> nodes;
    private List<Map<String, String>> links;
    private List<Map<String, String>> category;

    public EchartsVO(List<Map<String, String>> nodes, List<Map<String, String>> links) {
        this.nodes = nodes;
        this.links = links;
        //装载categories
        category = new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
        temp.put("name", "Person");
        category.add(new HashMap<>(temp));
        temp.put("name", "Article");
        category.add(new HashMap<>(temp));
        temp.put("name", "Journal");
        category.add(new HashMap<>(temp));
    }

    public EchartsVO() {
        //装载categories
        category = new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
        temp.put("name", "Person");
        category.add(new HashMap<>(temp));
        temp.put("name", "Article");
        category.add(new HashMap<>(temp));
        temp.put("name", "Journal");
        category.add(new HashMap<>(temp));
    }

    public List<Map<String, String>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Map<String, String>> nodes) {
        this.nodes = nodes;
    }

    public List<Map<String, String>> getLinks() {
        return links;
    }

    public void setLinks(List<Map<String, String>> links) {
        this.links = links;
    }

    public List<Map<String, String>> getCategory() {
        return category;
    }

    public void setCategory(List<Map<String, String>> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "EchartsVO{" +
                "nodes=" + nodes +
                ", links=" + links +
                ", category=" + category +
                '}';
    }
}

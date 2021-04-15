package com.ntu.graphs.vo;

public class PersonListVO {
    private String name;
    private String gender;
    private int counter;

    public PersonListVO(String name, String gender, int counter) {
        this.name = name;
        this.gender = gender;
        this.counter = counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", counter=" + counter +
                '}';
    }
}

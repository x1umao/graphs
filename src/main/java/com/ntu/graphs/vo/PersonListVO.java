package com.ntu.graphs.vo;

public class PersonListVO {
    private String name;
    private String gender;
    private String status;
    private int counter;

    public PersonListVO(String name, String gender, String status, int counter) {
        this.name = name;
        this.gender = gender;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "PersonListVO{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", counter=" + counter +
                '}';
    }
}

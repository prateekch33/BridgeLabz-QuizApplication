package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Participant {
    String name,roll;
    HashMap<Integer,Boolean> correctSubmissions=new HashMap<>();
    public Participant(String name,String roll) {
        this.name=name;
        this.roll=roll;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public HashMap<Integer,Boolean> getCorrectSubmissions() {
        return correctSubmissions;
    }

    public void setCorrectSubmissions(HashMap<Integer,Boolean> correctSubmissions) {
        this.correctSubmissions = correctSubmissions;
    }
}

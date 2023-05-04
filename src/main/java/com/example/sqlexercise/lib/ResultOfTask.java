package com.example.sqlexercise.lib;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultOfTask implements Serializable {

    public ArrayList<ArrayList<String>> sheet;
    public boolean incomplete;
    public boolean ordered = false;
    public String error = null;

    public ResultOfTask(){
        this.sheet = new ArrayList<>();
        incomplete = false;
    }

    @Override
    public String toString() {
        return "ResultOfTask{" +
                "sheet=" + sheet.toString() +
                ", incomplete=" + incomplete +
                ", ordered=" + ordered +
                ", error='" + error + '\'' +
                '}';
    }
}

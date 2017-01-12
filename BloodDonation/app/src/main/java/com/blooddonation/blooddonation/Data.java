package com.blooddonation.blooddonation;

/**
 * Created by ouiza on 11/01/17.
 */


public class Data {
    public boolean isChecked;
    public String answer;

    public Data(boolean isChecked, String answer) {
        this.isChecked = isChecked;
        this.answer = answer;
    }

    public Data() {
        this.isChecked = true;
        this.answer = "";
     }
}

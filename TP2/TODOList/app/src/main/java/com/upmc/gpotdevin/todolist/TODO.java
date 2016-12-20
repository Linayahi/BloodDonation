package com.upmc.gpotdevin.todolist;

/**
 * Created by gpotdevin on 29/11/2016.
 */

public class TODO {

    public String text;
    public boolean isChecked;

    TODO(String text, boolean isChecked){
        this.text = text;
        this.isChecked = isChecked;
    }
}

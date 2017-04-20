package com.example.admin.atmlocation.models;

/**
 * ItemListBank class
 * Created by naunem on 20/04/2017.
 */

public class ItemListBank {
    String name;
    boolean check;

    public ItemListBank(String name) {
        this.name = name;
    }

    public ItemListBank(String name, boolean check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

package com.example.admin.findatm.models;

/**
 * ItemListBank class
 * Created by naunem on 20/04/2017.
 */

public class ItemListBank {
    private String name;
    private boolean check;

    public ItemListBank(String name) {
        this.name = name;
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

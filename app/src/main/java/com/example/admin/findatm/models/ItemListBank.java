package com.example.admin.findatm.models;

import lombok.Getter;
import lombok.Setter;

/**
 * ItemListBank class
 * Created by naunem on 20/04/2017.
 */

@Setter
@Getter
public class ItemListBank {
    private String name;
    private boolean check;

    public ItemListBank(String name) {
        this.name = name;
    }
}

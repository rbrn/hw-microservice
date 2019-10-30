package com.db.hackathon.service.slope_one;

public class Item {

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    private String itemName;
}

package com.example.atlantis.model;

import java.io.Serializable;

public class Service implements Serializable {
    private String id;
    private String title;
    private int iconResId;
    private String description;
    private String category;

    public Service() {
    }

    public Service(String id, String title, int iconResId, String description, String category) {
        this.id = id;
        this.title = title;
        this.iconResId = iconResId;
        this.description = description;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

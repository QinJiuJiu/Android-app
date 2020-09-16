package com.example.memo.Files;

public class FilesItem {
    private String title;
    private String subtitle;

    public FilesItem(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle){
        this.title = subtitle;
    }
}

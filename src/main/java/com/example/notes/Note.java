package com.example.notes;

import javafx.scene.Group;

public class Note {
    private String title;
    private String text;
    private String date;
    private Group group;

    public Note(String title, String text, String date) {
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public Note() {
        this("", "", "");
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}

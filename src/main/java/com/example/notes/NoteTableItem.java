package com.example.notes;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class NoteTableItem {
    private Rectangle rect;
    private Label label;
    private Group group;
    private boolean editON = false;
    private boolean isDeleted = false;
    public static int size = 0;

    NoteTableItem(double x, double y) {

        this.rect = new Rectangle(x, y + 15, 280, 30);
        this.rect.setFill(Color.LIGHTGREY);

        Label name = new Label();
        name.setText("");
        name.setFont(new Font("Arial", 20 + size));
        name.setTextFill(Color.BLACK);
        name.setTranslateX(10 + x);
        name.setTranslateY(17 + y);
        this.label = name;

        this.group = new Group(this.rect, this.label);
        this.group.setOnMouseClicked(event -> editMode());
        this.group.setOnMouseEntered(event -> {
            this.rect.setFill(Color.GRAY);
            this.label.setTextFill(Color.WHITE);
        });
        this.group.setOnMouseExited(event -> {
            this.rect.setFill(Color.LIGHTGREY);
            this.label.setTextFill(Color.BLACK);
        });
    }

    public void editMode() {
        this.editON = !this.editON;
        Main.editMode();
    }

    public Group getGroup() { return group; }
    public Rectangle getRect() { return rect; }
    public void setRect(Rectangle rect) { this.rect = rect; }
    public Label getLabel() { return label; }
    public void setLabel(Label label) { this.label = label; }
    public void setGroup(Group group) { this.group = group; }
    public boolean isEditON() { return editON; }
    public void setEditON(boolean editON) { this.editON = editON; }
    public boolean isDeleted() { return isDeleted; }
    public void setDeleted(boolean deleted) { isDeleted = deleted; }

    @Override
    public String toString() {
        return "NoteTableItem{" +
                "label=" + label +
                '}';
    }
}

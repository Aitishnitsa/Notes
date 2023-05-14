package com.example.notes;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Background {
    public static Rectangle bg;
    public static Group bgGroup = new Group();
    public static Group searchGroup = new Group();
    public static Group group;

    public Background() {
        bg = new Rectangle(0,0, 300, 1500);
        bg.setFill(Color.WHITE);
        bgGroup.getChildren().addAll(bg);
        group = new Group(bgGroup, searchGroup);
        Main.group.getChildren().add(bgGroup);
    }

    public static Group getGroup() { return group; }
}

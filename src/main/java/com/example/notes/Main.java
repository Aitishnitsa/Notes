package com.example.notes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    public static Scene scene;
    public static Group group = new Group();
    public static Group notesGroup = new Group();
    public static Group tmp = new Group();
    public static ScrollPane scrollPane;
    public static BorderPane layout;
    public static Background background;
    public static List<Note> notes;
    public static File folder = new File("yourNotes");
    public static File[] listOfNames = folder.listFiles();
    public static NoteTableItem[] items;
    public static boolean showSearch = false;
    public static TextField search;
    public static Button searchButton;
    public static Rectangle bgRectangle;

    private void createBackground() {
        background = new Background();
        group.getChildren().add(Background.getGroup());
    }

    public static void getNotes() {
        int size = listOfNames.length;
        notes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String name = listOfNames[i].getName();
            String txt = getTXT(listOfNames[i]);
            String date = getDate(listOfNames[i]);
            notes.add(i, new Note(name, txt, date));
//            System.out.println(notes.get(i));
        }
    }

    public static void generateItems() {
        bgRectangle = new Rectangle(0,0, 300, 1500);
        bgRectangle.setFill(Color.WHITE);
        notesGroup.getChildren().add(bgRectangle);
        items = new NoteTableItem[notes.size()];
        double x, y;
        for (int i = 0; i < items.length; i++) {
            x = 5;
            y = i * 50;
            String name = notes.get(i).getTitle();
            if (name.contains(".txt")) name = name.substring(0, name.indexOf(".txt"));
            items[i] = new NoteTableItem(x, y);
            items[i].getLabel().setText(name);
//            System.out.println(i + " item generated");
        }
        for (NoteTableItem item : items) {
            notesGroup.getChildren().add(item.getGroup());
        }
    }

    public static void removeItem() {
        int count = 0;
        NoteTableItem[] tmp;
        for (NoteTableItem noteTableItem : items) {
            if (noteTableItem.isDeleted()) {
                notesGroup.getChildren().remove(noteTableItem.getGroup());
                noteTableItem.getGroup().setVisible(false);
                count++;
            }
        }
        tmp = new NoteTableItem[items.length - count];
        count = 0;
        for (NoteTableItem noteTableItem : items) {
            if (!noteTableItem.isDeleted()) {
                tmp[count] = noteTableItem;
                count++;
            }
        }
        items = tmp;
    }

    public static void deleteNote(int i) {
        items[i].setDeleted(true);
        String name = notes.get(i).getTitle();
        if (!name.contains(".txt")) name += ".txt";
        notes.remove(i);
        removeItem();
        File file = new File("yourNotes/" + name);
        if (file.delete()) {
            System.out.println("Deleted the file: " + file.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        Background.group.getChildren().remove(notesGroup);
        generateItems();
        Background.group.getChildren().add(notesGroup);
    }

    public static String getTXT(File fileName) {
        String txt = "";
        try {
            Scanner myReader = new Scanner(fileName);
            while (myReader.hasNextLine()) {
                txt = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return txt;
    }

    public static String getDate(File fileName) {
        Path path = fileName.toPath();
        BasicFileAttributes file_att;
        try {
            file_att = Files.readAttributes(
                    path, BasicFileAttributes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file_att.creationTime().toString();
    }

    public static void editMode() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].isEditON()) {
                EditNote.window(i);
                notes.get(i).setTitle(EditNote.title);
                notes.get(i).setText(EditNote.text);
//                deleteNote(i);
                try {
                    File file = new File("yourNotes/" + EditNote.title + ".txt");
                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    } else {
                        System.out.println("File already exists.");
                    }

                    FileWriter fileWriter = new FileWriter("yourNotes/" + EditNote.title + ".txt");
                    fileWriter.write(EditNote.text);
                    fileWriter.close();
                    System.out.println("Successfully wrote to the file.");

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                Background.group.getChildren().remove(notesGroup);
                generateItems();
                Background.group.getChildren().add(notesGroup);
                items[i].setEditON(false);
            }
        }
    }

    private static void searchActions() {
        List <String> txt = new ArrayList<>();
        List <String> tmpList = new ArrayList<>();
        searchButton.setOnAction(event -> {
            for (int i = 0; i < listOfNames.length; i++) {
                String s = getTXT(listOfNames[i]).toLowerCase();
                txt.add(i, s);
                System.out.println(i + ") " + txt.get(i));
            }
            for (int i = 0; i < txt.size(); i++) {
                if (txt.get(i).contains(search.getText())) {
                    tmpList.add(listOfNames[i].getName());
                }
            }
            if (tmpList.size() == 0) tmpList.add("Нотатки відсутні");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Інформація");
            alert.setHeaderText("Слово \"" + search.getText() + "\" знайдено у цих нотатках:");
            alert.setContentText(tmpList.toString());
            alert.showAndWait();
            txt.clear();
            tmpList.clear();
            showSearch = false;
        });
    }

    private class Keyboard implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
            switch (event.getCode()) {
                case F -> {
                    showSearch = !showSearch;
                    if (showSearch) {
                        search = new TextField();
                        search.setPromptText("Пошук");
                        searchButton = new Button();
                        searchButton.setText("OK");
                        searchButton.setLayoutX(150);
//                        Background.searchGroup.getChildren().addAll(search, searchButton);
                        tmp.getChildren().addAll(search, searchButton);
                        if (!group.getChildren().contains(tmp)) group.getChildren().add(tmp);
                        searchActions();
                    } else {
//                        Background.group.getChildren().remove(Background.searchGroup);
                        group.getChildren().remove(tmp);
                    }
                }
                case I -> {
                    for (Note note : notes) System.out.println(note);
                    for (File listOfName : listOfNames) System.out.println(listOfName);
                }
            }
        }
    }

    @Override
    public void start(Stage stage) {
        createBackground();
        getNotes();
        generateItems();

        scrollPane = new ScrollPane(group);
        scrollPane.setMaxHeight(Background.bg.getHeight());
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        layout = new BorderPane();
        TopMenu.createMenu();
        layout.setCenter(scrollPane);

        scene = new Scene(layout, 315, 500);
        scene.setOnKeyPressed(new Keyboard());

        Background.bgGroup.getChildren().add(notesGroup);
        stage.setScene(scene);
        stage.setTitle("Нотатки");
        stage.show();
    }

    public static void main(String[] args) { launch(args); }

}


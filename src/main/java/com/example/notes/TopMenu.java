package com.example.notes;

import javafx.application.Platform;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class TopMenu {
    public static void createMenu(){
        Menu mainMenu = new Menu("Меню");

        MenuItem newNote = new MenuItem("Створити нотатку");
        newNote.setOnAction(e -> {
            AddNote.window();
            Main.notes.add(new Note(AddNote.title, AddNote.text, ""));

            try {
                File file = new File("yourNotes/" + AddNote.title + ".txt");
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }

                FileWriter fileWriter = new FileWriter("yourNotes/" + AddNote.title + ".txt");
                fileWriter.write(AddNote.text);
                fileWriter.close();
                System.out.println("Successfully wrote to the file.");

            } catch (IOException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
            Main.background.group.getChildren().remove(Main.notesGroup);
            Main.generateItems();
            Main.background.group.getChildren().add(Main.notesGroup);

        });
        mainMenu.getItems().add(newNote);


        MenuItem removeFile = new MenuItem("Видалити");
        removeFile.setOnAction(e -> {
            DeleteNote.dialogWindow();
        });
        mainMenu.getItems().add(removeFile);
        mainMenu.getItems().add(new SeparatorMenuItem());

        CheckMenuItem fontSize = new CheckMenuItem("Більший розмір шрифту");
        fontSize.setOnAction(e -> {
//            System.out.println("snap");
            if (fontSize.isSelected()) Note.size = 5;
            else Note.size = 0;
            Main.background.group.getChildren().remove(Main.notesGroup);
            Main.generateItems();
            Main.background.group.getChildren().add(Main.notesGroup);
        });
        mainMenu.getItems().add(fontSize);
        mainMenu.getItems().add(new SeparatorMenuItem());

        MenuItem exitFile = new MenuItem("Вихід");
        exitFile.setOnAction(e -> Platform.exit());
        mainMenu.getItems().add(exitFile);

        Menu sorting = new Menu("Сортування");

        MenuItem dateSort = new MenuItem("За датою");
        dateSort.setOnAction(e -> {
            sortByDate(Main.notes);

            Main.background.group.getChildren().remove(Main.notesGroup);
            Main.generateItems();
            Main.background.group.getChildren().add(Main.notesGroup);
        });
        sorting.getItems().add(dateSort);

        MenuItem titleSort = new MenuItem("За алфавітом");
        titleSort.setOnAction(e -> {
            sortByName(Main.notes);

            Main.background.group.getChildren().remove(Main.notesGroup);
            Main.generateItems();
            Main.background.group.getChildren().add(Main.notesGroup);
        });
        sorting.getItems().add(titleSort);

        Menu helpMenu = new Menu("Допомога");
        MenuItem help = new MenuItem("Керівництво користувача");
        help.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Керівництво користувача");
            alert.setHeaderText("Інструкція з використання");
            alert.setContentText("""
                    1) нова нотатка: Меню -> Створити нотатку
                    2) видалення нотатки: Меню -> Видалити
                    3) редагування нотатки: натиснути на нотатку зі списку на головному екрані
                    4) активувати/деактивувати пошук: кнопка 'F' на клавіатурі""");
            alert.showAndWait();
        });
        MenuItem info = new MenuItem("Контакти");
        info.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Контакти");
            alert.setHeaderText("Електронна адреса");
            alert.setContentText("aitishnitsa@gmail.com");
            alert.showAndWait();
        });
        helpMenu.getItems().addAll(help, info);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(mainMenu, sorting, helpMenu);
        Main.layout.setTop(menuBar);
    }

    // сортування за допомогою вбудованого метода sort()
    private static void sortByDate(List<Note> arr) {
        arr.sort(new Comparator<Note>() {
            public int compare(Note o1, Note o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }

    // сортування бульбашкою
    private static void sortByName(List<Note> arr) {
        String temp;
        for (int j = 0; j < arr.size() - 1; j++) {
            for (int i = j + 1; i < arr.size(); i++) {
                if (arr.get(j).getTitle().compareTo(arr.get(i).getTitle()) > 0) {
                    temp = arr.get(j).getTitle();
                    arr.get(j).setTitle(arr.get(i).getTitle());
                    arr.get(i).setTitle(temp);
                }
            }
        }
    }

}

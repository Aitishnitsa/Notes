package com.example.notes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class DeleteNote {
    private static boolean answer = false;
    public static String s;
    public static boolean dialogWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Видалити нотатку");
        ComboBox comboBox = new ComboBox<>();
        for (int i = 0; i < Main.notes.size(); i++) {
            comboBox.getItems().addAll(Main.notes.get(i).getTitle());
        }
        comboBox.setPromptText("Оберіть нотатку для видалення: ");

        Button button = new Button("ОК");
        button.setOnAction(event -> {
            answer = true;
            for (int i = 0; i < Main.notes.size(); i++) {
                if (comboBox.getValue() == Main.notes.get(i).getTitle()) {
                    s = Main.notes.get(i).getTitle();
                    Main.notesGroup.getChildren().remove(s);
                    Main.deleteNote(i);
                }
            }
            window.close();
        });
        button.setPrefSize(50,25);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(comboBox, button);
        Scene scene = new Scene(layout, 200, 100);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}

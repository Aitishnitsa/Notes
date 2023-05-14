package com.example.notes;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddNote {
    private static boolean answer = false;
    public static String title = "";
    public static String text = "";
    public static boolean window() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Додати нотатку");

        TextField textField = new TextField();
        textField.setPromptText("Назва");
        textField.setFont(new Font(20));

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setFont(new Font(15));

        Button button = new Button("ОК");
        button.setOnAction(event -> {
            answer = true;
            title = textField.getText();
            text = textArea.getText();
            window.close();
        });

        button.setPrefSize(150,50);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(textField, textArea, button);
        Scene scene = new Scene(layout, 275, 350);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
}

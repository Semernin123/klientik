package com.example.client.controller;


import com.example.client.Application;
import com.example.client.Entity.AuthorEntity;
import com.example.client.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.client.controller.MainController.authorsData;

public class EditAuthorController {

    public static String api = "http://localhost:2825/api/v1/book/";

    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField AuthorName_field;

    @FXML
    private TextField AuthorLast_field;

    @FXML
    private TextField AuthorSur_field;

    @FXML
    private GridPane tableBooks;

    private Stage editBookStage;
    private AuthorEntity author;
    private int authorID;
    private boolean okClicked = false;


    public void setDialogStage(Stage authorStage) {
        this.editBookStage = authorStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setLabels(AuthorEntity bookIn, int book_id) {
        this.author = bookIn;
        this.authorID = book_id;

        AuthorName_field.setText(author.getName());
        AuthorSur_field.setText(author.getSurname());
        AuthorLast_field.setText(author.getLastname());

    }

    @FXML
    void initialize() {
        assert AuthorName_field != null : "fx:id=\"AuthorName_field\" was not injected: check your FXML file 'EditAuthor.fxml'.";
        assert AuthorLast_field != null : "fx:id=\"AuthorLast_field\" was not injected: check your FXML file 'EditAuthor.fxml'.";
        assert AuthorSur_field != null : "fx:id=\"AuthorSur_field\" was not injected: check your FXML file 'EditAuthor.fxml'.";
        assert tableBooks != null : "fx:id=\"tableBooks\" was not injected: check your FXML file 'EditAuthor.fxml'.";

    }

    @FXML
    private void SaveBook() throws IOException {
        if (isInputValid()) {
            author.setName(AuthorName_field.getText());
            author.setSurname(AuthorSur_field.getText());
            author.setLastname(AuthorLast_field.getText());

            okClicked = true;
            editBookStage.close();
            authorsData.set(authorID, author);
        }
    }

    @FXML
    private void CloseBook() {
        editBookStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        try {
            if (!AuthorName_field.getText().matches("[\\sA-ZА-Яa-za-я]{2,10}"))
                errorMessage += "Имя, введено некорректно \n";
            if (!AuthorSur_field.getText().matches("[\\sA-ZА-Яa-za-я]{3,20}"))
                errorMessage += "Фамилия, введена некорректно \n";
            if (!AuthorLast_field.getText().matches("[\\sA-ZА-Яa-za-я]{3,20}"))
                errorMessage += "Отчество, введено некорректно \n";
        } catch (Exception e) {
            errorMessage += "Пустое поле!";
        }


        if (errorMessage.length() == 0) return true;
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(editBookStage);
            alert.setTitle("Ошибка заполнения");
            alert.setHeaderText("Пожалуйста, укажите корректные значения текстовых полей");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}




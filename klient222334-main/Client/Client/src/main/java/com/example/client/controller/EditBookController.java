package com.example.client.controller;


import com.example.client.Application;
import com.example.client.Entity.AuthorEntity;
import com.example.client.Entity.BookEntity;
import com.example.client.Entity.PublisherEntity;
import com.example.client.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;

import static com.example.client.controller.MainController.authorsData;
import static com.example.client.controller.MainController.booksData;
import static com.example.client.controller.MainController.publishersData;

public class EditBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    public static ObservableList<AuthorEntity> authorCombo = FXCollections.observableArrayList();
    public static ObservableList<PublisherEntity> publisherCombo = FXCollections.observableArrayList();

    @FXML
    private TextField AddKind;

    @FXML
    private TextField AddTitle;

    @FXML
    private TextField AddYear;

    @FXML
    private ComboBox<AuthorEntity> SetAuthor;

    @FXML
    private ComboBox<PublisherEntity> SetPublisher;
    private Stage editBookStage;
    private BookEntity book;
    private int bookID;
    private boolean okClicked = false;

    public static String api = "http://localhost:2825/api/v1/";

    static HTTPUtils http = new HTTPUtils();
    static Gson gson = new Gson();

    public void setDialogStage (Stage bookStage) { this.editBookStage = bookStage; }

    public boolean isOkClicked() { return okClicked; }

    public void setLabels (BookEntity bookIn, int book_id) {
        this.book = bookIn;
        this.bookID = book_id;

        AddTitle.setText(book.getTitle());
        AddYear.setText(book.getYear());
        AddKind.setText(book.getKind());
    }

    @FXML
    private void SaveBook() throws IOException {
        if (isInputValid()) {
            book.setTitle(AddTitle.getText());
            book.setAuthor(SetAuthor.getValue());
            book.setPublisher(SetPublisher.getValue());
            book.setYear(AddYear.getText());
            book.setKind(AddKind.getText());

            okClicked = true;
            editBookStage.close();
            booksData.set(bookID, book);
        }
    }
    @FXML
    private void CloseBook() {
        editBookStage.close();
    }



        @FXML
        void initialize() throws Exception {

            assert AddKind != null : "fx:id=\"AddKind\" was not injected: check your FXML file 'AddBook.fxml'.";
            assert AddTitle != null : "fx:id=\"AddTitle\" was not injected: check your FXML file 'AddBook.fxml'.";
            assert AddYear != null : "fx:id=\"AddYear\" was not injected: check your FXML file 'AddBook.fxml'.";
            assert SetAuthor != null : "fx:id=\"SetAuthor\" was not injected: check your FXML file 'AddBook.fxml'.";
            assert SetPublisher != null : "fx:id=\"SetPublisher\" was not injected: check your FXML file 'AddBook.fxml'.";
            if (authorCombo.size() != authorsData.size() || publisherCombo.size() != publishersData.size()){
            updateMap();
            }
            updateComboBox();

        }

    private void updateComboBox() throws Exception {
        SetAuthor.getItems().addAll(authorCombo);
        SetPublisher.getItems().addAll(publisherCombo);
    }
    private void updateMap(){
        for(int i = 0;i < authorsData.size();i++){
            authorCombo.add(authorsData.get(i));
        }
        for(int i = 0;i < publishersData.size();i++){
            publisherCombo.add(publishersData.get(i));

        }
    }
    private boolean isInputValid() {
        String errorMessage = "";
        try {
            if (!AddTitle.getText().matches("[\\sA-ZА-Яa-za-я]{1,10}"))
                errorMessage += "Название книги, введено некорректно \n";
            if (SetAuthor.getSelectionModel().getSelectedItem() == null)
                errorMessage += "Автор должен быть выбран \n";
            if (SetPublisher.getSelectionModel().getSelectedItem() == null)
                errorMessage += "Издательство должно быть выбрано\n";
            if (!AddKind.getText().matches("[\\sA-ZА-Яa-za-я]{1,10}") || AddKind.getText() == null)
                errorMessage += "Раздел содержания, введен некорректно!\n";
            else {
                try {
                    Integer.parseInt(AddYear.getText());
                } catch (NumberFormatException e) {
                    errorMessage += "Не корректное значение года выпуска книги (должно быть целочисленным) !\n";
                }
            }
            if (!AddYear.getText().matches("[\\d0-9]{3,4}") || AddYear.getText() == null)
                errorMessage += "Год выпуска введен некорректно! \n";
        }catch (Exception e){
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




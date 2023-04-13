package com.example.client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.client.Application;
import com.example.client.Entity.PublisherEntity;
import com.example.client.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.example.client.controller.MainController.publishersData;

public class EditPublisherController {
        public static String api = "http://localhost:2825/api/v1/book/";

        static HTTPUtils http = new HTTPUtils();
        static Gson gson = new Gson();
        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField PublisherCity;

        @FXML
        private TextField PublisherName;
        private Stage editBookStage;
        private PublisherEntity publisher;
        private int publisherID;
        private boolean okClicked = false;

        public void setDialogStage (Stage publisherStage) { this.editBookStage = publisherStage; }

        public boolean isOkClicked() { return okClicked; }

        public void setLabels (PublisherEntity bookIn, int book_id) {
                this.publisher = bookIn;
                this.publisherID = book_id;

                PublisherName.setText(publisher.getPublisher());
                PublisherCity.setText(publisher.getCity());

        }


        @FXML
        private void SaveBook() throws IOException {
                if (isInputValid()) {
                        publisher.setPublisher(PublisherName.getText());
                        publisher.setCity(PublisherCity.getText());


                        okClicked = true;
                        editBookStage.close();
                        publishersData.set(publisherID, publisher);
                }
        }
        @FXML
        private void CloseBook() {
                editBookStage.close();
        }

        @FXML
        void initialize() {
            assert PublisherCity != null : "fx:id=\"PublisherCity\" was not injected: check your FXML file 'EditPublisher.fxml'.";
            assert PublisherName != null : "fx:id=\"PublisherName\" was not injected: check your FXML file 'EditPublisher.fxml'.";

        }
        private boolean isInputValid() {
                String errorMessage = "";
                try {
                        if (!PublisherName.getText().matches("[\\sA-ZА-Яa-za-я]{2,20}"))
                                errorMessage += "Назание, введено некорректно \n";
                        if (!PublisherCity.getText().matches("[\\sA-ZА-Яa-za-я]{3,20}"))
                                errorMessage += "Город, введен некорректно \n";
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


        @FXML
        private void addPublisher() throws IOException {
                try {
                        PublisherEntity xxxBook = new PublisherEntity();
                        publishersData.add(xxxBook);
                        Application.showPublisherEditDialog(xxxBook, publishersData.size() - 1);
                        Long id = addBook(xxxBook);
                        xxxBook.setId(id);
                }catch (Exception e){}
        }

        public static Long addBook(PublisherEntity author) throws IOException {
                System.out.println(author.toString());
                author.setId(null);
                String res = http.post(api + "add", gson.toJson(author).toString());
                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                Long tempId = jsonObject.getAsJsonObject("data").get("id").getAsLong();
                return tempId;

        }
}




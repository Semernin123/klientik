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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class MainController {

        public static String apiBook = "http://localhost:2825/api/v1/book/";
        public static String apiAuthor = "http://localhost:2825/api/v1/author/";
        public static String apiPublisher = "http://localhost:2825/api/v1/publisher/";
        public static ObservableList<BookEntity> booksData = FXCollections.observableArrayList();
        public static ObservableList<PublisherEntity> publishersData = FXCollections.observableArrayList();
        public static ObservableList<AuthorEntity> authorsData = FXCollections.observableArrayList();
        static HTTPUtils http = new HTTPUtils();
        static Gson gson = new Gson();

        @FXML
        private TableColumn<AuthorEntity, String> authorLastname;

        @FXML
        private TableColumn<AuthorEntity, String> authorName;

        @FXML
        private TableColumn<AuthorEntity, String> authorSurname;

        @FXML
        private TableColumn<BookEntity, String> bookAuthor;

        @FXML
        private TableColumn<BookEntity, String> bookChapter;

        @FXML
        private TableColumn<BookEntity, String> bookName;

        @FXML
        private TableColumn<BookEntity, String> bookPublisher;

        @FXML
        private TableColumn<BookEntity, String> bookYear;

        @FXML
        private TableColumn<PublisherEntity, String> publisherCity;

        @FXML
        private TableColumn<PublisherEntity, String> publisherName;

        @FXML
        private TableView<BookEntity> tableBooks;

        @FXML
        private TableView<PublisherEntity> tablePublisher;
        @FXML
        private TableView<AuthorEntity> tableAuthor;

        @FXML
        private void initialize() throws Exception {
                getData();
                updateTable();
        }

        private void updateTable() throws Exception {
                bookName.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("title"));
                bookPublisher.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("publisher"));
                bookYear.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("year"));
                bookChapter.setCellValueFactory(new PropertyValueFactory<BookEntity, String>( "kind"));
                bookAuthor.setCellValueFactory(new PropertyValueFactory<BookEntity, String>("author"));
                tableBooks.setItems(booksData);

                authorName.setCellValueFactory(new PropertyValueFactory<AuthorEntity, String>("name"));
                authorSurname.setCellValueFactory(new PropertyValueFactory<AuthorEntity, String>("lastname"));
                authorLastname.setCellValueFactory(new PropertyValueFactory<AuthorEntity, String>("surname"));
                tableAuthor.setItems(authorsData);

                publisherName.setCellValueFactory(new PropertyValueFactory<PublisherEntity, String>("publisher"));
                publisherCity.setCellValueFactory(new PropertyValueFactory<PublisherEntity, String>("city"));
                tablePublisher.setItems(publishersData);
        }

        //Добавление
        @FXML
        private void addBOOK() throws IOException {
                try {
                        BookEntity tempBook = new BookEntity();
                        booksData.add(tempBook);
                        Application.showBookEditDialog(tempBook, booksData.size() - 1);
                        if(tempBook.getTitle() == null){
                                booksData.remove(booksData.size() - 1);
                        } else{
                        Long id = addBook(tempBook);
                        tempBook.setId(id);
                        }
                }catch (Exception e){}
        }
        @FXML
        public static Long addBook(BookEntity book) throws IOException {
                System.out.println(book.toString());
                book.setId(null);
                String res = http.post(apiBook + "add", gson.toJson(book).toString());
                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                System.out.println(res);
                Long tempId = jsonObject.getAsJsonObject("data").get("id").getAsLong();
                return tempId;

        }


        @FXML
        private void addAuthor() throws IOException {
                        AuthorEntity zzzBook = new AuthorEntity();
                        authorsData.add(zzzBook);
                        Application.showAuthorEditDialog(zzzBook, authorsData.size() - 1);
                if(zzzBook.getName() == null){
                        authorsData.remove(authorsData.size() - 1);
                } else {
                        Long id = addAuthor(zzzBook);
                        zzzBook.setId(id);
                }
        }
        public static Long addAuthor(AuthorEntity author) throws IOException {
                System.out.println(author.toString());
                author.setId(null);
                String res = http.post(apiAuthor + "add", gson.toJson(author).toString());
                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                System.out.println(res);
                Long tempId = jsonObject.getAsJsonObject("author").get("id").getAsLong();
                System.out.println(tempId);
                return tempId;

        }


        @FXML
        private void addPublisher() throws IOException {
                try {
                        PublisherEntity xxxBook = new PublisherEntity();
                        publishersData.add(xxxBook);
                        Application.showPublisherEditDialog(xxxBook, publishersData.size() - 1);
                        if(xxxBook.getPublisher() == null){
                                publishersData.remove(publishersData.size() - 1);
                        } else {
                                Long id = addPublisher(xxxBook);
                                xxxBook.setId(id);
                        }
                }catch (Exception e){}
        }
        @FXML
        public static Long addPublisher(PublisherEntity publisher) throws IOException {
                System.out.println(publisher.toString());
                publisher.setId(null);
                String res = http.post(apiPublisher + "add", gson.toJson(publisher).toString());
                JsonObject jsonObject = new JsonParser().parse(res).getAsJsonObject();
                Long tempId = jsonObject.getAsJsonObject("publisher").get("id").getAsLong();
                return tempId;

        }

        //Редактирование

        @FXML
        private void EditBook() throws IOException {
                BookEntity selectedPerson = tableBooks.getSelectionModel().getSelectedItem();
                if (selectedPerson != null){
                        Application.showBookEditDialog(selectedPerson, booksData.indexOf(selectedPerson));
                        http.put(apiBook + "update", gson.toJson(selectedPerson).toString());}
                else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Hичего не выбрно");
                        alert.setHeaderText("Отсутствует выбраный польватель");
                        alert.setContentText("Пожалуйста, выберите пользвоателя в таблице");
                        alert.showAndWait();
                }
        }
        @FXML
        private void EditAuthor() throws IOException {
                AuthorEntity selectedPerson = tableAuthor.getSelectionModel().getSelectedItem();
                if (selectedPerson != null){
                        Application.showAuthorEditDialog(selectedPerson, authorsData.indexOf(selectedPerson));
                        http.put(apiAuthor + "update", gson.toJson(selectedPerson).toString());}
                else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Hичего не выбрно");
                        alert.setHeaderText("Отсутствует выбраный польватель");
                        alert.setContentText("Пожалуйста, выберите пользвоателя в таблице");
                        alert.showAndWait();
                }
        }
        @FXML
        private void EditPublisher() throws IOException {
                PublisherEntity selectedPerson = tablePublisher.getSelectionModel().getSelectedItem();
                if (selectedPerson != null){
                        Application.showPublisherEditDialog(selectedPerson, publishersData.indexOf(selectedPerson));
                        http.put(apiPublisher + "update", gson.toJson(selectedPerson).toString());}
                else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Hичего не выбрно");
                        alert.setHeaderText("Отсутствует выбраный польватель");
                        alert.setContentText("Пожалуйста, выберите пользвоателя в таблице");
                        alert.showAndWait();
                }
        }

        //Удаление
        @FXML
        private void click_removeBook() throws IOException {
                BookEntity selectedPerson = tableBooks.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                        System.out.println(selectedPerson.getId());
                        http.delete(apiBook + "delete/", selectedPerson.getId());
                        booksData.remove(selectedPerson);
                } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ничего не выбрано");
                        alert.setHeaderText("Отсутствует выбраный пользователь");
                        alert.setContentText("Пожалуйста, выберите пользователя в таблице");
                        alert.showAndWait();
                }
        }
        @FXML
        private void click_removeAuthor() throws IOException {
                AuthorEntity selectedPerson = tableAuthor.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                        System.out.println(selectedPerson.getId());
                        http.delete(apiAuthor + "delete/", selectedPerson.getId());
                        authorsData.remove(selectedPerson);
                } else {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ничего не выбрано");
                        alert.setHeaderText("Отсутствует выбраный пользователь");
                        alert.setContentText("Пожалуйста, выберите пользователя в таблице");
                        alert.showAndWait();
                }
        }
        @FXML
        private void click_removePublisher() throws IOException {
                PublisherEntity selectedPerson = tablePublisher.getSelectionModel().getSelectedItem();
                if (selectedPerson != null) {
                        System.out.println(selectedPerson.getId());
                        http.delete(apiPublisher + "delete/", selectedPerson.getId());
                        publishersData.remove(selectedPerson);
                } else {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Ничего не выбрано");
                        alert.setHeaderText("Отсутствует выбраный пользователь");
                        alert.setContentText("Пожалуйста, выберите пользователя в таблице");
                        alert.showAndWait();
                }
        }

        //Табличные методы

        public static void getData() throws Exception {
                String res = http.get(apiBook, "all");
                System.out.println(res);
                JsonObject base = gson.fromJson(res, JsonObject.class);
                JsonArray dataArr = base.getAsJsonArray("data");
                for (int i = 0; i < dataArr.size(); i++) {
                        BookEntity newBook = gson.fromJson(dataArr.get(i).toString(), BookEntity.class);
                        booksData.add(newBook);
                }
                res = http.get(apiAuthor, "all");
                System.out.println(res);
                base = gson.fromJson(res, JsonObject.class);
                dataArr = base.getAsJsonArray("data");
                for (int i = 0; i < dataArr.size(); i++) {
                        AuthorEntity newAuthor = gson.fromJson(dataArr.get(i).toString(), AuthorEntity.class);
                        authorsData.add(newAuthor);
                }
                res = http.get(apiPublisher, "all");
                System.out.println(res);
                base = gson.fromJson(res, JsonObject.class);
                dataArr = base.getAsJsonArray("data");
                for (int i = 0; i < dataArr.size(); i++) {
                        PublisherEntity newPublisher = gson.fromJson(dataArr.get(i).toString(), PublisherEntity.class);
                        publishersData.add(newPublisher);
                }
        }
}




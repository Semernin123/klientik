package com.example.client;

import com.example.client.Entity.AuthorEntity;
import com.example.client.Entity.BookEntity;
import com.example.client.Entity.PublisherEntity;
import com.example.client.controller.EditBookController;
import com.example.client.controller.EditAuthorController;
import com.example.client.controller.EditPublisherController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private AnchorPane main;


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("main.fxml"));
            main = (AnchorPane) loader.load();


            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(main);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean showAuthorEditDialog (AuthorEntity bookObj, int id) {
        try {
// Загрузка fxml создание сцены для всплывающего окна.
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(Application.class.getResource("EditAuthor.fxml"));
            AnchorPane page = (AnchorPane) Loader.load();
// Создание окна Stage.
            Stage authorStage = new Stage();
            authorStage.setTitle("Редактирование автора");
            authorStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            EditAuthorController controller = Loader.getController();
            controller.setDialogStage(authorStage);
            controller.setLabels(bookObj, id);
            authorStage.setScene(scene);

            authorStage.showAndWait();
            return true;  //isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean showPublisherEditDialog (PublisherEntity bookObj, int id) {
        try {
// Загрузка fxml создание сцены для всплывающего окна.
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(Application.class.getResource("EditPublisher.fxml"));
            AnchorPane page = (AnchorPane) Loader.load();
// Создание окна Stage.
            Stage publisherStage = new Stage();
            publisherStage.setTitle("Редактирование книги");
            publisherStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            EditPublisherController controller = Loader.getController();
            controller.setDialogStage(publisherStage);
            controller.setLabels(bookObj, id);
            publisherStage.setScene(scene);

            publisherStage.showAndWait();
            return true;  //isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean showBookEditDialog (BookEntity bookObj, int id) {
        try {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(Application.class.getResource("AddBook.fxml"));
            AnchorPane page = (AnchorPane) Loader.load();
            Stage bookStage = new Stage();
            bookStage.setTitle("Добавление книги");
            bookStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            EditBookController controller = Loader.getController();
            controller.setDialogStage(bookStage);
            controller.setLabels(bookObj, id);
            bookStage.setScene(scene);

            bookStage.showAndWait();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
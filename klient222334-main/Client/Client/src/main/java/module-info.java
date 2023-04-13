module com.example.client {
    requires javafx.controls; requires javafx.fxml;
    requires static lombok;
    requires okhttp3;

    requires com.google.gson;

    exports com.example.client;
    exports com.example.client.controller;
    exports com.example.client.Entity;
    opens com.example.client.Entity to com.google.gson;
    exports com.example.client.response;
    exports com.example.client.service;
    exports com.example.client.utils;
    opens com.example.client.controller to javafx.fxml;
    opens com.example.client to javafx.fxml;

}
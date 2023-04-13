package com.example.client.Entity;

import javafx.fxml.FXML;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class AuthorEntity {
    private Long id;
    private String name;
    private String lastname;
    private String surname;


    @Override
    public String toString() {
        return  surname + " " + name.charAt(0) + ". " + lastname.charAt(0) + ".";
    }
}

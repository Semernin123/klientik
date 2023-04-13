package com.example.client.response;

import com.example.client.Entity.BookEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookListResponse extends BaseResponse {
    public List<BookEntity> data;

    public BookListResponse(List<BookEntity> data) {
        super(true, "Cписок Kниг");
        this.data = data;
    }


}
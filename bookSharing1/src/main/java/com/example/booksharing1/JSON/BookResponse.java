package com.example.booksharing1.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class BookResponse {

        @JsonProperty("ownedBooks")
        List<Book> book;

        public List<Book> getBooks () {
            return book;
        }

        public void setBooks (List<Book> books) {
            book = books;
        }

}

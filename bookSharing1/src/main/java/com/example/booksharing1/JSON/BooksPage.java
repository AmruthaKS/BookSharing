package com.example.booksharing1.JSON;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class BooksPage implements Serializable {

    int size;
    int offset;
    List<Book> books;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<Book> getBooks() {
        if(books == null)
            books = new ArrayList<Book>();
        return books;
    }

    public BooksPage() {

    }

    public BooksPage(int size, int offset, List<Book> books) {
        this.size = size;
        this.offset = offset;
        this.books = books;
    }
}

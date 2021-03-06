package com.example.booksharing1.JSON;



import java.util.List;

public class OwnedBooksPage {
    private int offset;
    private List<OwnedBook> ownedBooks;

    private int size;

    public OwnedBooksPage() {
    }

    public OwnedBooksPage(int offset, int size, List<OwnedBook> ownedBooks) {
        this.offset = offset;
        this.size = size;
        this.ownedBooks = ownedBooks;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<OwnedBook> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(List<OwnedBook> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

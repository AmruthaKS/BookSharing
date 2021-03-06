package com.example.booksharing1.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Book implements Serializable {

        private Long nodeId;


        private String id;


        private Integer goodreadsId;

        private String authorName;
        private String goodreadsAuthorId;

        private String name;
        private String isbn;
        private String isbn13;
        private int publishedYear;
        private String description;
        private String publisher;
        private Integer numberOfPages;

        private String status;

        private String borrowerId;
        private String dueDate;

    private String bookType;  //Owned, borrowed, wishlist
    public Book(String Bid ) { id = Bid ;}
    public Book() { }

    public Book(String name, String isbn) {
        this.name = name;
        this.isbn = isbn;
    }

    public Book(String id, String name, String isbn) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
    }

    public Book(Integer goodreadsId, String authorName, String goodreadsAuthorId, String name, String isbn, String isbn13, int publishedYear, String description, String publisher, Integer numberOfPages, String imageUrl) {
        this.goodreadsId = goodreadsId;
        this.authorName = authorName;
        this.goodreadsAuthorId = goodreadsAuthorId;
        this.name = name;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.publishedYear = publishedYear;
        this.description = description;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.imageUrl = imageUrl;
    }

    public Book(Long nodeId, String id, Integer goodreadsId, String authorName, String goodreadsAuthorId, String name, String isbn, String isbn13, int publishedYear, String description, String publisher, Integer numberOfPages, String imageUrl) {
        this.nodeId = nodeId;
        this.id = id;
        this.goodreadsId = goodreadsId;
        this.authorName = authorName;
        this.goodreadsAuthorId = goodreadsAuthorId;
        this.name = name;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.publishedYear = publishedYear;
        this.description = description;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.imageUrl = imageUrl;
    }


    public Map<String, Object> getAdditionalProperties() {
        if(additionalProperties == null)
            additionalProperties = new HashMap<String, Object>();
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }


    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

        public String getPublisher() {
            return publisher;
        }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
        public Integer getNumberOfPages() {
            return numberOfPages;
        }
    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

        private String imageUrl;



        public int getPublishedYear() {
            return publishedYear;
        }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

        public String getImageUrl() {
            return imageUrl;
        }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



        public Integer getGoodreadsId() {
            return goodreadsId;
        }
    public void setGoodreadsId(Integer goodreadsId) {
        this.goodreadsId = goodreadsId;
    }


        public String getAuthorName() {
            return authorName;
        }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

        public String getGoodreadsAuthorId() {
            return goodreadsAuthorId;
        }

    public void setGoodreadsAuthorId(String goodreadsAuthorId) {
        this.goodreadsAuthorId = goodreadsAuthorId;
    }

        public Long getNodeId() {
            return nodeId;
        }
    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }


        public String getId() {
            return id;
        }
    public void setId(String id) {
        this.id = id;
    }

        public String getName() {
            return name;
        }
    public void setName(String name) {
        this.name = name;
    }


        public String getIsbn() {
            return isbn;
        }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }



        public String getDescription() {
            return description;
        }

    public void setDescription(String description) {
        this.description = description;
    }

        public String getIsbn13() {
            return isbn13;
        }
    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

        public String getStatus() { return  status; }
    public void setStatus(String Status) {
        this.status = Status;
    }


        public String getBorrowerId() { return borrowerId; }
    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId;
    }
        public String getDueDate() { return  dueDate; }


}

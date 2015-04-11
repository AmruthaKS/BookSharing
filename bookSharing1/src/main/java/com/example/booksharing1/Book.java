package com.example.booksharing1;

import java.util.List;

public class Book {

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

        private String createdDate;
        private String status;
        private String lastModifiedDate;
        private String borrowerId;
        private String dueDate;
        private String contractPeriodInDays;

        public String getPublisher() {
            return publisher;
        }


        public Integer getNumberOfPages() {
            return numberOfPages;
        }


        private String imageUrl;



        public int getPublishedYear() {
            return publishedYear;
        }



        public String getImageUrl() {
            return imageUrl;
        }



        public Integer getGoodreadsId() {
            return goodreadsId;
        }



        public String getAuthorName() {
            return authorName;
        }



        public String getGoodreadsAuthorId() {
            return goodreadsAuthorId;
        }



        public Long getNodeId() {
            return nodeId;
        }



        public String getId() {
            return id;
        }


        public String getName() {
            return name;
        }



        public String getIsbn() {
            return isbn;
        }



        public String getDescription() {
            return description;
        }



        public String getIsbn13() {
            return isbn13;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getStatus() { return  status; }

        public String getLastModifiedDate() { return lastModifiedDate; }

        public String getBorrowerId() { return borrowerId; }

        public String getDueDate() { return  dueDate; }

        public String getContractPeriodInDays() { return  contractPeriodInDays; }


	 	
}

package com.example.booksharing1;

import com.example.booksharing1.JSON.Book;
import com.example.booksharing1.JSON.BooksPage;
import com.example.booksharing1.JSON.OwnedBook;
import com.example.booksharing1.JSON.OwnedBooksPage;
import com.example.booksharing1.JSON.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HttpRestClient {
    private static final String IP = "106.216.168.192:8389";
    private static final String BASE_URL = "http://"+IP+"/neo4j/v1/";
    private static RestTemplate restTemplate = new RestTemplate();

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    // Get user details based on the fbID
    public User getUser(String url) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        User user = new User();
        try {
            user = restTemplate.getForObject(getAbsoluteUrl(url), User.class);
            System.out.println("user details are"+user.getEmail());
        } catch (Exception e){
            System.out.print("exception in getUser !!!!!!!!!!"+e.getMessage());
        }
        return user;
    }

    // Get the books which the user owns
    public List<OwnedBook> getOwnedBooks(String url) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        List<OwnedBook> book = null;
        try {
            book = restTemplate.getForObject(getAbsoluteUrl(url), OwnedBooksPage.class).getOwnedBooks();
        } catch (Exception e) {
            System.out.print("exception in getOwnedBooks !!!!!!!!!!"+e.getMessage());
        }
        return book;
    }

    // Get the books which the user owns
    public List<Book> getReadBooks(String url) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        BooksPage searchResult = new BooksPage();
        try {

            searchResult = restTemplate.getForObject(getAbsoluteUrl(url), BooksPage.class);
        } catch (Exception e){
            System.out.print("exception in getReadBooks !!!!!!!!!!"+e.getMessage());
        }

        return searchResult.getBooks();
    }

    // mark the book as owned for a user
    public String addBookToUser(String url, Book b) {
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        String response = "";
        try {
            response = restTemplate.postForObject(getAbsoluteUrl(url), b, String.class,b.getId());
        } catch(Exception e ){
            System.out.print("exception in addBookToUser !!!!!!!! "+e.getMessage());
        }
        return response;
    }

}



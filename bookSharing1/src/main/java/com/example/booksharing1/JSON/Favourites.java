package com.example.booksharing1.JSON;

import java.io.Serializable;
import java.util.Set;

public class Favourites implements Serializable{


        Set<String> favourites;

        public Set<String> getFavourites() {
            return favourites;
        }

        public void setFavourites(Set<String> favourites) {
            this.favourites = favourites;
        }

    public Favourites (Set<String> favourites) {
        this.favourites = favourites;
    }

    public Favourites () {

    }




 }


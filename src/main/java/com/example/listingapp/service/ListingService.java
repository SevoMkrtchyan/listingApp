package com.example.listingapp.service;


import com.example.listingapp.entity.Listing;

import java.util.List;

public interface ListingService {


    List<Listing> findAll();

    boolean saveListing(Listing listing);

    Listing getListingById(int id);

    boolean deleteListingById(int id);

}

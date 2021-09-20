package com.example.listingapp.service;


import com.example.listingapp.entity.Category;
import com.example.listingapp.entity.Listing;
import com.example.listingapp.entity.User;

import java.util.List;

public interface ListingService {


    List<Listing> findAll();

    boolean saveListing(Listing listing);

    Listing findListingById(int id);

    List<Listing> findListingByCategoryId(Category category);

    List<Listing> findListingByUserEmail(String email);

    boolean deleteListingById(int id);

    Listing updateListing(Listing listing);


}

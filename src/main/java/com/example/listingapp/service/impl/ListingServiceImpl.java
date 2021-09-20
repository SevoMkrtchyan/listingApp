package com.example.listingapp.service.impl;

import com.example.listingapp.entity.Listing;
import com.example.listingapp.repository.ListingRepository;
import com.example.listingapp.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;

    @Override
    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    @Override
    public boolean saveListing(Listing listing) {
        if (listing != null && listing.getCategory() != null && listing.getUser() != null) {
            listingRepository.save(listing);
            return true;
        }
        return false;
    }

    @Override
    public Listing getListingById(int id) {
        Optional<Listing> byId = listingRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public boolean deleteListingById(int id) {
        if (getListingById(id) != null) {
            List<Listing> listings = listingRepository.findAll();
            listings.remove(getListingById(id));
            return true;
        }
        return false;
    }

}

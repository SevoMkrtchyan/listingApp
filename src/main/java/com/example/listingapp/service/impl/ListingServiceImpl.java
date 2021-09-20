package com.example.listingapp.service.impl;

import com.example.listingapp.entity.Category;
import com.example.listingapp.entity.Listing;
import com.example.listingapp.entity.User;
import com.example.listingapp.repository.ListingRepository;
import com.example.listingapp.repository.UserRepository;
import com.example.listingapp.service.CategoryService;
import com.example.listingapp.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;

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
    public Listing findListingById(int id) {
        Optional<Listing> byId = listingRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public boolean deleteListingById(int id) {
        if (findListingById(id) != null) {
            List<Listing> listings = listingRepository.findAll();
            listings.remove(findListingById(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean updateListing(Listing listing) {
        if (listing != null) {
            Optional<User> userFromDbByListingUser = userRepository.findById(listing.getId());
            if (userFromDbByListingUser.isPresent()) {
                listingRepository.save(listing);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<Listing> findListingByUserEmail(String email) {
        Optional<User> userFromDB = userRepository.findByEmail(email);
        if (userFromDB.isPresent()) {
            return listingRepository.findAllByUserEmail(email);
        }
        return null;
    }

    @Override
    public List<Listing> findListingByCategoryId(Category category) {
        Category categoryFromDB = categoryService.findCategoryById(category.getId());
        if (categoryFromDB != null) {
            return listingRepository.findAllByCategory(category);
        }
        return null;
    }

}

package com.example.listingapp.service.impl;

import com.example.listingapp.entity.Category;
import com.example.listingapp.entity.Listing;
import com.example.listingapp.entity.User;
import com.example.listingapp.service.ListingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ListingServiceImplTest {

    @Autowired
    private ListingService listingService;

    @Test
    void saveListing_if_user_is_null() {
        boolean result = listingService.saveListing(Listing.builder()
                .title("testTitle")
                .description("testDesc")
                .price(456)
                .category(Category.builder().name("testCategory").build())
                .user(null)
                .build());
        assertFalse(result);
    }

    @Test
    void saveListing_if_category_is_null() {
        boolean result = listingService.saveListing(Listing.builder()
                .title("testTitle")
                .description("testDesc")
                .price(456)
                .category(null)
                .user(User.builder()
                        .name("testUser")
                        .surname("testSurname")
                        .email("test@mail.ru")
                        .build())
                .build());
        assertFalse(result);
    }

    @Test
    void saveListing() {
        boolean result = listingService.saveListing(Listing.builder()
                .title("testTitle1")
                .description("testDesc1")
                .price(456)
                .category(Category.builder().name("testCategory1").build())
                .user(User.builder()
                        .name("testUser1")
                        .surname("testSurname1")
                        .email("test1@mail.ru")
                        .build())
                .build());
        assertTrue(result);
    }

    @Test
    void findListingById_if_exist() {
        Listing byId = listingService.findListingById(5);
        assertNotNull(byId);
    }

    @Test
    void findListingById_if_not_exist() {
        Listing byId = listingService.findListingById(556465);
        assertNull(byId);
    }

    @Test
    void deleteListingById_if_exist() {
        boolean result = listingService.deleteListingById(6);
        assertTrue(result);
    }

    @Test
    void deleteListingById_if_not_exist() {
        boolean result = listingService.deleteListingById(5645);
        assertFalse(result);
    }

    @Test
    void updateListing_if_exist() {
        Listing listing = listingService.updateListing(Listing.builder()
                .id(7)
                .title("test7Title")
                .description("test7Desc")
                .price(0)
                .build());
        assertNotNull(listing);
    }

    @Test
    void updateListing_if_not_exist() {
        Listing listing = listingService.updateListing(Listing.builder()
                .id(9999)
                .title("test7Title")
                .description("test7Desc")
                .price(0)
                .build());
        assertNull(listing);
    }

    @Test
    void findListingByUserEmail_if_exist() {
        List<Listing> listings = listingService.findListingByUserEmail("qwerty@mail.ru");
        assertNotNull(listings);
    }

    @Test
    void findListingByUserEmail_if_not_exist() {
        List<Listing> listings = listingService.findListingByUserEmail("qwer4564564ty@mail.ru");
        assertNull(listings);
    }

    @Test
    void findListingByUserEmail_if_empty() {
        List<Listing> listings = listingService.findListingByUserEmail("");
        assertNull(listings);
    }

    @Test
    void findListingByUserEmail_if_null() {
        List<Listing> listings = listingService.findListingByUserEmail("");
        assertNull(listings);
    }

    @Test
    void findListingByCategoryId_if_exist() {
        List<Listing> byCategoryId = listingService.findListingByCategoryId(Category.builder().id(4).build());
        assertNotNull(byCategoryId);
    }

    @Test
    void findListingByCategoryId_if_not_exist() {
        List<Listing> byCategoryId = listingService.findListingByCategoryId(Category.builder().id(4456).build());
        assertNull(byCategoryId);
    }

}
package com.example.listingapp.endpoint;

import com.example.listingapp.entity.Listing;
import com.example.listingapp.service.CategoryService;
import com.example.listingapp.service.ListingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/listings")
@RequiredArgsConstructor
@Slf4j
public class ListingEndpoint {

    private final ListingService listingService;
    private final CategoryService categoryService;

    @GetMapping()
    public List<Listing> findAll() {
        return listingService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Listing> saveListing(@RequestBody Listing listing) {
        if (listingService.saveListing(listing)) {
            log.info("Listing was saved at {} by User with email {} ", new Date(), listing.getUser().getEmail());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Listing> deleteListingById(@PathVariable(name = "id") int id) {
        Listing listing = listingService.findListingById(id);
        if (listing == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Listing with {} id was deleted at {} ", id, new Date());
        listingService.deleteListingById(listing.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable(name = "id") int id) {
        Listing listing = listingService.findListingById(id);
        if (listing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listing);
    }

    @PutMapping
    public ResponseEntity<Listing> updateListing(@RequestBody Listing listing) {
        Listing updatedListing = listingService.updateListing(listing);
        if (updatedListing != null) {
            return ResponseEntity.ok(updatedListing);
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    @GetMapping("/byUser/{email}")
    public ResponseEntity<List<Listing>> getListingsByUserEmail(@PathVariable(name = "email") String email) {
        List<Listing> listingFromDB = listingService.findListingByUserEmail(email);
        if (listingFromDB != null) {
            return ResponseEntity.ok(listingFromDB);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<Listing>> getListingsByCategoryId(@PathVariable(name = "categoryId") int id) {
        try {
            List<Listing> listingsFromDbByCategoryID = listingService.findListingByCategoryId(categoryService.findCategoryById(id));
            return ResponseEntity.ok(listingsFromDbByCategoryID);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

}

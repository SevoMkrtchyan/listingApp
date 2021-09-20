package com.example.listingapp.endpoint;


import com.example.listingapp.entity.Listing;
import com.example.listingapp.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/listing")
@RequiredArgsConstructor
public class ListingEndpoint {

    private final ListingService listingService;

    @GetMapping()
    public List<Listing> findAll() {
        return listingService.findAll();
    }

    @PostMapping()
    public ResponseEntity<Listing> saveListing(@RequestBody Listing listing) {
        if (listingService.saveListing(listing)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Listing> deleteListingById(@PathVariable(name = "id") int id) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return ResponseEntity.notFound().build();
        }
        listingService.deleteListingById(listing.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable(name = "id") int id) {
        Listing listing = listingService.getListingById(id);
        if (listing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listing);
    }

}

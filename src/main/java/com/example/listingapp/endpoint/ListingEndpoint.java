package com.example.listingapp.endpoint;

import com.example.listingapp.dto.ListingCreateDto;
import com.example.listingapp.dto.ListingDto;
import com.example.listingapp.dto.UserDto;
import com.example.listingapp.entity.Listing;
import com.example.listingapp.service.CategoryService;
import com.example.listingapp.service.ListingService;
import com.example.listingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/listings")
@RequiredArgsConstructor
@Slf4j
public class ListingEndpoint {

    private final ListingService listingService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @GetMapping()
    public List<ListingDto> findAll() {
        List<Listing> listings = listingService.findAll();
        List<ListingDto> listingDtos = listings.stream().map(e -> modelMapper.map(e, ListingDto.class)).collect(Collectors.toList());
        listingDtos.forEach(e -> {
            for (Listing listing : listings) {
                if (listing.getUser() != null) {
                    e.setUserDto(modelMapper.map(listing.getUser(), UserDto.class));
                }
            }
        });
        return listingDtos;
    }

    @PostMapping()
    public ResponseEntity saveListing(@RequestBody ListingCreateDto listing) {
        if (listingService.saveListing(parseCreateListingToListing(listing))) {
            log.info("Listing was saved by User with email {} ", listing.getUserDto().getEmail());
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
        listingService.deleteListingById(listing.getId());
        log.info("Listing with {} id was deleted", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ListingDto> getListingById(@PathVariable(name = "id") int id) {
        Listing listing = listingService.findListingById(id);
        if (listing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(parseListingToDto(listing));
    }

    @PutMapping
    public ResponseEntity<ListingDto> updateListing(@RequestBody ListingCreateDto listing) {
        Listing updatedListing = listingService.updateListing(parseCreateListingToListing(listing));
        if (updatedListing != null) {
            return ResponseEntity.ok(parseListingToDto(updatedListing));
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    @GetMapping("/byUser/{email}")
    public ResponseEntity<List<ListingDto>> getListingsByUserEmail(@PathVariable(name = "email") String email) {
        List<Listing> listingFromDB = listingService.findListingByUserEmail(email);
        List<ListingDto> listingDtoList = new LinkedList<>();
        if (listingFromDB != null) {
            listingFromDB.forEach(this::parseListingToDto);
            return ResponseEntity.ok(listingDtoList);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<ListingDto>> getListingsByCategoryId(@PathVariable(name = "categoryId") int id) {
        try {
            List<Listing> listingsFromDbByCategoryID = listingService.findListingByCategoryId(categoryService.findCategoryById(id));
            List<ListingDto> listingDtoList = new LinkedList<>();
            if (listingsFromDbByCategoryID != null) {
                listingsFromDbByCategoryID.forEach(this::parseListingToDto);
                return ResponseEntity.ok(listingDtoList);
            }
            return ResponseEntity.notFound().build();
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ListingDto parseListingToDto(Listing listing) {
        ListingDto listingDto = modelMapper.map(listing, ListingDto.class);
        listingDto.setUserDto(modelMapper.map(listing.getUser(), UserDto.class));
        return listingDto;
    }

    private Listing parseCreateListingToListing(ListingCreateDto listingCreateDto) {
        Listing listing = modelMapper.map(listingCreateDto, Listing.class);
        listing.setUser(userService.findUserByEmail(listingCreateDto.getUserDto().getEmail()));
        return listing;
    }

}

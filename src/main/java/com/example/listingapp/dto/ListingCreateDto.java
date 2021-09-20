package com.example.listingapp.dto;

import com.example.listingapp.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingCreateDto {

    private String title;
    private String description;
    private int price;
    private Category category;
    private UserDto userDto;

}
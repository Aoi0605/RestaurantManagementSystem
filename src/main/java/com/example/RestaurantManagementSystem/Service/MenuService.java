package com.example.RestaurantManagementSystem.Service;

import java.util.List;

import com.example.RestaurantManagementSystem.Dto.ItemRequest;
import com.example.RestaurantManagementSystem.Entity.Items;

public interface MenuService {

    List<Items> getItemById(Long itemId);

    Long createdItem(ItemRequest itemRequest);

    void updateItemById(ItemRequest itemRequest);

    void deleteItemById(Long itemId);
}

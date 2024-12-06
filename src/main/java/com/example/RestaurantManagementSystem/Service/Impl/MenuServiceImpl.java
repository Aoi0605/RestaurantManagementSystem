package com.example.RestaurantManagementSystem.Service.Impl;

import java.beans.Transient;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.RestaurantManagementSystem.Dao.MenuDao;
import com.example.RestaurantManagementSystem.Dto.ItemRequest;
import com.example.RestaurantManagementSystem.Entity.Items;
import com.example.RestaurantManagementSystem.Service.MenuService;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Items> getItemById(Long itemId) {
        List<Items> itemList = menuDao.getItemById(itemId);
        return itemList;
    }

    @Transactional
    @Override
    public Long createdItem(ItemRequest itemRequest) {
        // 插入 items 表
        Long itemId = menuDao.insertItem(itemRequest);

        // 插入 details 表
        menuDao.insertDetail(itemRequest);

        // 插入 item_detail 表
        menuDao.insertItemDetail(itemRequest);
        
        return itemId;
    }

    @Transactional
    @Override
    public void updateItemById(ItemRequest itemRequest) {
        menuDao.updateItem(itemRequest);
        menuDao.updateDetail(itemRequest);
    }

    @Override
    public void deleteItemById(Long itemId) {
        menuDao.deleteItem(itemId);        
    }


}

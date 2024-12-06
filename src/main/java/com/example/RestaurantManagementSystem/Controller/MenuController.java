package com.example.RestaurantManagementSystem.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.RestaurantManagementSystem.Dto.ItemRequest;
import com.example.RestaurantManagementSystem.Entity.Items;
import com.example.RestaurantManagementSystem.Service.MenuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/items")
public class MenuController {

    @Autowired
    private MenuService menuService;
    
    @GetMapping("/{itemId}")
    public ResponseEntity<List<Items>> getItem(@PathVariable Long itemId) {
        List<Items> itemList = menuService.getItemById(itemId);

        //如果值所找到的數據不為 null 則回傳 200 ok 靜態碼
        if (itemList != null) {
            return ResponseEntity.status(HttpStatus.OK).body(itemList);
        } else {
            //找不到，回傳 404 not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createIems")
    public ResponseEntity<List<Items>> createIems(@RequestBody ItemRequest itemRequest) {

        Long itemId = menuService.createdItem(itemRequest);
        List<Items> itemList = menuService.getItemById(itemId);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemList);
    }
    
    //productRequest 內的參數允許修改，故沿用。
    @PutMapping("/{itemId}")
    public ResponseEntity<List<Items>> updateProduct(@PathVariable Long itemId,
                                                @RequestBody ItemRequest itemRequest) {
        List<Items> itemList = menuService.getItemById(itemId);
        //檢查欲修改商品是否存在
        if (itemList != null) {
            itemRequest.setItemId(itemId);
            //修改商品數據
            menuService.updateItemById(itemRequest);
            //從資料庫取得更新後的商品數據
            List<Items> updatedProduct = menuService.getItemById(itemId);
            //將修改好的數據回傳給前端
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteItems(@PathVariable Long itemId) {
        //刪除的 API 不用加上是否有這個商品的判斷，商品只要不存在即可。
        menuService.deleteItemById(itemId);
        //204 NO_CONTENT 表示數據已被刪除。
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

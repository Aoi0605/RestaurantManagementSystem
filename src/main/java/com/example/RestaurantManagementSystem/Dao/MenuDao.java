package com.example.RestaurantManagementSystem.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.RestaurantManagementSystem.Dto.ItemRequest;
import com.example.RestaurantManagementSystem.Entity.Items;

import javafx.scene.control.Menu;

@Mapper
public interface MenuDao extends BaseMapper<Items>{

    @Select("SELECT " +
        "i.item_id, i.name AS name, i.description AS description, " +
        "i.image AS image, i.price AS price, " +
        "d.detail_id, d.title AS title " +
        "FROM items i " +
        "LEFT JOIN item_detail idt ON i.item_id = idt.item_id " +
        "LEFT JOIN details d ON idt.detail_id = d.detail_id " +
        "WHERE i.item_id = #{itemId}")
    List<Items> getItemById(Long itemId);


    @Insert("INSERT INTO items (name, description, image, price) " +
            "VALUES (#{name}, #{description}, #{image}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId")
    Long insertItem(ItemRequest itemRequest);

    // 插入 details 表
    @Insert("INSERT INTO details (title) VALUES (#{title})")
    @Options(useGeneratedKeys = true, keyProperty = "detailId")
    int insertDetail(ItemRequest itemRequest);

    // 插入 item_detail 表
    @Insert("INSERT INTO item_detail (item_id, detail_id) VALUES (#{itemId}, #{detailId})")
    int insertItemDetail(ItemRequest itemRequest);

    @Update("UPDATE items SET name = #{name}, description = #{description}, image = #{image}, price = #{price} WHERE item_id = #{itemId}")
    int updateItem(ItemRequest itemRequest);

    // 更新 details 表
    @Update("UPDATE details SET title = #{title} WHERE detail_id = #{detailId}")
    int updateDetail(ItemRequest itemRequest);

    @Delete("DELETE FROM items WHERE item_id = #{itemId}")
    int deleteItem(Long itemId);

    // 刪除 item_detail 表中的關聯記錄
    @Delete("DELETE FROM item_detail WHERE item_id = #{itemId}")
    int deleteItemDetail(Long itemId);
}

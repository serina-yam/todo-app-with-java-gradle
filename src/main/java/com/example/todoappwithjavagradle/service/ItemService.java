package com.example.todoappwithjavagradle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.todoappwithjavagradle.entity.Item;
import com.example.todoappwithjavagradle.repository.ItemRepository;

/**
 * アイテム情報 Service
 */
@Service
public class ItemService {

    /**
    * アイテム情報 Repository
    */
    @Autowired
    ItemRepository itemRepository;
    public List<Item> searchAll() {
        // アイテムTBLの内容を全検索
        return itemRepository.findAll();
    }
}

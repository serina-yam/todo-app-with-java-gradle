package com.example.todoappwithjavagradle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoappwithjavagradle.entity.Item;

/**
 * アイテム情報 Repository
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>  {

}

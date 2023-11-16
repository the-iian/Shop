package com.jpabook.shop.service;

import com.jpabook.shop.domain.item.Item;
import com.jpabook.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService { // itemRepository에 의해 위임만하는 class


    @Autowired
    private final ItemRepository itemRepository;

    @Transactional // readOnly면 저장이 안되므로 Transactional로 우선권 갖게하기
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    // 단건 조회 @Transactional(readOnly = true)에 의해 읽기전용으로 수행됨
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 전체 조회 @Transactional(readOnly = true)에 의해 읽기전용으로 수행됨
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
}

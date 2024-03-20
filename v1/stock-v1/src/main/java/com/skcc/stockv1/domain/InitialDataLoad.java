package com.skcc.stockv1.domain;

import com.skcc.stockv1.domain.data.Item;
import com.skcc.stockv1.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@Component
public class InitialDataLoad {

    private final StockRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("stockCount", 100000);
        params.put("item", Item.AMERICANO);
        repository.updateItemCount(params);
        params.put("item", Item.LATTE);
        repository.updateItemCount(params);
        params.put("item", Item.CAPPUCCINO);
        repository.updateItemCount(params);
    }
}

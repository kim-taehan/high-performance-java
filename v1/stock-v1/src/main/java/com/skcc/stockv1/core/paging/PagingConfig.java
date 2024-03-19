package com.skcc.stockv1.core.paging;

import com.skcc.stockv1.core.paging.aspect.PagingAspect;
import com.skcc.stockv1.core.paging.mapper.PagingMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingConfig {
    @Bean
    public PagingAspect pagingQueryAspect() {
        return new PagingAspect(pagingMapper());
    }


    @Bean
    @ConditionalOnMissingClass("com.skcc.stockv1.core.paging.mapper.PagingMapper")
    public PagingMapper pagingMapper() {
        return new PagingMapper();
    }

}

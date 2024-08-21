package com.br.rodrigoarcanjo.placeService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

import com.br.rodrigoarcanjo.placeService.domain.PlaceRepository;
import com.br.rodrigoarcanjo.placeService.domain.PlaceService;

@Configuration
@EnableR2dbcAuditing
public class PlaceConfig {

    @Bean
    PlaceService placeService(PlaceRepository placeRepository) {
        return new PlaceService(placeRepository);
    }
    
}
package com.br.rodrigoarcanjo.placeService.util;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Example;

import com.br.rodrigoarcanjo.placeService.domain.Place;

public class QueryBuilder {
    private QueryBuilder() {
    }
  
    public static Example<Place> makeQuery(Place planet) {
      ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues();
      return Example.of(planet, exampleMatcher);
    }
  }
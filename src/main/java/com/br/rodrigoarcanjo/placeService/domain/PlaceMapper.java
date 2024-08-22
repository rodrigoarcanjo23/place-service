package com.br.rodrigoarcanjo.placeService.domain;

import org.springframework.util.StringUtils;

import com.br.rodrigoarcanjo.placeService.api.PlaceRequest;
import com.br.rodrigoarcanjo.placeService.api.PlaceResponse;

public class PlaceMapper {
    public static Place updatePlaceFromDTO(PlaceRequest placeRequest, Place place) {
      final String name = StringUtils.hasText(placeRequest.name()) ? placeRequest.name() : place.name();
      final String city = StringUtils.hasText(placeRequest.city()) ? placeRequest.city() : place.city();
      final String state = StringUtils.hasText(placeRequest.state()) ? placeRequest.state() : place.state();
      return new Place(place.id(), name, place.slug(), city, state, place.createdAt(), place.updatedAt());
    }
  
    public static PlaceResponse toResponse(Place place) {
      return new PlaceResponse(place.name(), place.slug(),
          place.city(), place.state(), place.createdAt(), place.updatedAt());
    }
  }
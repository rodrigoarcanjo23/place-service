package com.br.rodrigoarcanjo.placeService.web;

import com.br.rodrigoarcanjo.placeService.api.PlaceResponse;
import com.br.rodrigoarcanjo.placeService.domain.Place;

public class PlaceMapper {
    public static PlaceResponse fromPlaceToResponse(Place place) {
        return new PlaceResponse(place.name(), place.slug(), place.state(), place.createdAt(), place.updateAt());
    }
}

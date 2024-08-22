package com.br.rodrigoarcanjo.placeService.domain;

import com.br.rodrigoarcanjo.placeService.api.PlaceRequest;
import com.github.slugify.Slugify;

import reactor.core.publisher.Mono;

public class PlaceService {
    private PlaceRepository placeRepository;
    private Slugify slg;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        var place = new Place(null, placeRequest.name(), slg.slugify(placeRequest.name()),
        placeRequest.state(), null, null);
        return placeRepository.save(place);
    }
}

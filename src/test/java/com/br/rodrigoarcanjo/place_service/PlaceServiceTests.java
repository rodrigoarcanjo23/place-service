package com.br.rodrigoarcanjo.place_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.br.rodrigoarcanjo.placeService.api.PlaceRequest;
import com.br.rodrigoarcanjo.placeService.domain.Place;
import com.br.rodrigoarcanjo.placeService.domain.PlaceRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class PlaceServiceTests {
  public static final Place CENTRAL_PERK = new Place(
      1L, "Central Perk", "central-perk", "NY", "NY", null, null);

  @Autowired
  WebTestClient webTestClient;

  @Autowired
  PlaceRepository placeRepository;

  @Test
  public void testCreatePlaceSuccess() {
    final String name = "Valid Name";
    final String city = "Valid City";
    final String state = "Valid State";
    final String slug = "valid-name";

    webTestClient
        .post()
        .uri("/places")
        .bodyValue(
            new PlaceRequest(name, city, state))
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .jsonPath("name").isEqualTo(name)
        .jsonPath("city").isEqualTo(city)
        .jsonPath("state").isEqualTo(state)
        .jsonPath("slug").isEqualTo(slug)
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testCreatePlaceFailure() {
    final String name = "";
    final String state = "";
    final String city = "";

    webTestClient
        .post()
        .uri("/places")
        .bodyValue(
            new PlaceRequest(name, city, state))
        .exchange()
        .expectStatus().isBadRequest();
  }

  @Test
  public void testEditPlaceSuccess() {
    final String newName = "New Name";
    final String newCity = "New City";
    final String newState = "New State";
    final String newSlug = "new-name";

    // Updates name, city and state.
    webTestClient
        .patch()
        .uri("/places/1")
        .bodyValue(
            new PlaceRequest(newName, newCity, newState))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(newName)
        .jsonPath("city").isEqualTo(newCity)
        .jsonPath("state").isEqualTo(newState)
        .jsonPath("slug").isEqualTo(newSlug)
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only name
    webTestClient
        .patch()
        .uri("/places/1")
        .bodyValue(
            new PlaceRequest(CENTRAL_PERK.name(), null, null))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("city").isEqualTo(newCity)
        .jsonPath("state").isEqualTo(newState)
        .jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only city
    webTestClient
        .patch()
        .uri("/places/1")
        .bodyValue(
            new PlaceRequest(null, CENTRAL_PERK.city(), null))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("city").isEqualTo(CENTRAL_PERK.city())
        .jsonPath("state").isEqualTo(newState)
        .jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();

    // Updates only state
    webTestClient
        .patch()
        .uri("/places/1")
        .bodyValue(
            new PlaceRequest(null, null, CENTRAL_PERK.state()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("city").isEqualTo(CENTRAL_PERK.city())
        .jsonPath("state").isEqualTo(CENTRAL_PERK.state())
        .jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testGetSuccess() {
    webTestClient
        .get()
        .uri("/places/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("state").isEqualTo(CENTRAL_PERK.state())
        .jsonPath("slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("createdAt").isNotEmpty()
        .jsonPath("updatedAt").isNotEmpty();
  }

  @Test
  public void testGetFailure() {
    webTestClient
        .get()
        .uri("/places/11")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  public void testListAllSuccess() {
    webTestClient
        .get()
        .uri("/places")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$[0].name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("$[0].slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("$[0].state").isEqualTo(CENTRAL_PERK.state())
        .jsonPath("$[0].createdAt").isNotEmpty()
        .jsonPath("$[0].updatedAt").isNotEmpty();
  }

  @Test
  public void testListByNameSuccess() {
    webTestClient
        .get()
        .uri("/places?name=%s".formatted(CENTRAL_PERK.name()))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$.length()").isEqualTo(1)
        .jsonPath("$[0].name").isEqualTo(CENTRAL_PERK.name())
        .jsonPath("$[0].slug").isEqualTo(CENTRAL_PERK.slug())
        .jsonPath("$[0].state").isEqualTo(CENTRAL_PERK.state())
        .jsonPath("$[0].createdAt").isNotEmpty()
        .jsonPath("$[0].updatedAt").isNotEmpty();
  }

  @Test
  public void testListByNameNotFound() {
    webTestClient
        .get()
        .uri("/places?name=name")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$.length()").isEqualTo(0);
  }
}
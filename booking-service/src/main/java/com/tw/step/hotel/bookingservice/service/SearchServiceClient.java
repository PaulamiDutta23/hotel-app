package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.view.BookingRequest;
import com.tw.step.hotel.bookingservice.view.HotelView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SearchServiceClient {

    private final WebClient webClient;

    public SearchServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<HotelView> bookHotel(BookingRequest bookingRequest) {

        return webClient
                .post()
                .uri("http://search-service:5000/internal/api/search/book")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookingRequest)
                .retrieve()
                .toEntity(HotelView.class)
                .block();
    }
}

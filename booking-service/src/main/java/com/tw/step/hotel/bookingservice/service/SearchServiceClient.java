package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.view.BookingRequest;
import com.tw.step.hotel.bookingservice.view.HotelView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.naming.InsufficientResourcesException;

@Service
public class SearchServiceClient {

    private final WebClient webClient;

    public SearchServiceClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<HotelView> bookHotel(BookingRequest bookingRequest) throws InsufficientResourcesException {
        try {
            return webClient
                    .post()
                    .uri("http://search-service:5000/internal/api/hotel/book")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(bookingRequest)
                    .retrieve()
                    .toEntity(HotelView.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new InsufficientResourcesException(e.getMessage());
        }
    }
}

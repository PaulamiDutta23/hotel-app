package com.example.hotelApp.domain.controller;

import com.example.hotelApp.domain.service.HotelService;
import com.example.hotelApp.domain.view.HotelView;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final Logger logger = LoggerFactory.getLogger(SearchController.class);
    private final HotelService hotelService;

    SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelView>> searchHotels(@RequestParam(name="city") String city, HttpServletRequest req) {
        logger.info("{} {}", req.getMethod(), req.getRequestURI());

        List<HotelView> allHotels = hotelService.getHotels(city);
        return ResponseEntity.ok(allHotels);
    }
}

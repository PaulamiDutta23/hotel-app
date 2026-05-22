package com.tw.step.hotel.searchservice.controller;

import com.tw.step.hotel.searchservice.service.HotelService;
import com.tw.step.hotel.searchservice.view.HotelView;
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
        logger.info("{} {} city={}", req.getMethod(), req.getRequestURI(), city);

        List<HotelView> allHotels = hotelService.getHotels(city);
        return ResponseEntity.ok(allHotels);
    }
}

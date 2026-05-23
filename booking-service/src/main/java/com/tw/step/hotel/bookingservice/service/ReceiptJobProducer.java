package com.tw.step.hotel.bookingservice.service;

import com.tw.step.hotel.bookingservice.view.BookingView;
import com.tw.step.hotel.bookingservice.view.ReceiptJobView;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ReceiptJobProducer {
    private  final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String QUEUE = "receiptJobs";

    public ReceiptJobProducer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendReceiptJob(BookingView bookingView){
        ReceiptJobView receiptJobView = new ReceiptJobView(bookingView.id(), bookingView.totalPrice(), bookingView.totalRooms(), bookingView.hotelName(), bookingView.username());
        String json = objectMapper.writeValueAsString(receiptJobView);
        redisTemplate.opsForList().leftPush(QUEUE,json);
        System.out.println("Job pushed!");
    }
}

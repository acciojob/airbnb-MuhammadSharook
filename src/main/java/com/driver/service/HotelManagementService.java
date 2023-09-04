package com.driver.service;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repository.HotelManagemtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagementService {
    HotelManagemtRepository hotelManagemtRepository = new HotelManagemtRepository();

    public static String addHotel(@RequestBody Hotel hotel){
        return HotelManagemtRepository.addHotel(hotel);
    }

    public static Integer addUser(User user) {
        return HotelManagemtRepository.addUser(user);
    }

    public static String getHotelWithMostFacilities() {
        return HotelManagemtRepository.getHotelWithMostFacilities();
    }

    public static int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        booking.setBookingId(uuid.toString());
        return HotelManagemtRepository.bookARoom(booking);
    }

    public static int getBookings(Integer aadharCard) {
        return HotelManagemtRepository.getBookings(aadharCard);
    }

    public static Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return HotelManagemtRepository.updateFacilities(newFacilities,hotelName);
    }
}

package com.driver.controllers;

import com.driver.Repository.HotelManagemtRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelManagementService {

    HotelManagemtRepository hotelManagemtRepository = new HotelManagemtRepository();

    public String addHotel(Hotel hotel) {
        return hotelManagemtRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagemtRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagemtRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {
        return hotelManagemtRepository.bookARoom(booking);
    }

    public int getBookings(Integer aadharCard) {
        return hotelManagemtRepository.getBookings(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagemtRepository.updateFacilities(newFacilities,hotelName);
    }
}
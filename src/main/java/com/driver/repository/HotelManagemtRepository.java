package com.driver.repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

@Repository
public class HotelManagemtRepository {

    static HashMap<String,Hotel> hoteldb = new HashMap<>();
    static HashMap<Integer, User> userdb = new HashMap<>();
    static HashMap<String, Booking> bookingsdb = new HashMap<>();
    static HashMap<Integer, List<Booking>> per_person_bookings = new HashMap<>();

    public static String addHotel(Hotel hotel)
    {
        if(hotel == null || hotel.getHotelName() == null || hoteldb.containsKey(hotel.getHotelName()))
        {
            return "FAILURE";
        }
        else
        {
            hoteldb.put(hotel.getHotelName(),hotel);
            return "SUCCESS";
        }
    }

    public static Integer addUser(User user) {
        userdb.put(user.getAadharCardNo(),user);
        return user.getAadharCardNo();
    }

    public static String getHotelWithMostFacilities() {
        int max_facilities = 0;
        String max_hotelname = "";
        TreeMap<String, Hotel> tm = new TreeMap<>(hoteldb);

        for(String s : tm.keySet())
        {
            int no_facilities = hoteldb.get(s).getFacilities().size();
            if(no_facilities > max_facilities)
            {
                max_facilities = no_facilities;
                max_hotelname = s;
            }
        }

        return max_hotelname;
    }

    public static int bookARoom(Booking booking) {
        String booking_Id = booking.getBookingId();
        bookingsdb.put(booking_Id,booking);

        int available_rooms = hoteldb.get(booking.getHotelName()).getAvailableRooms();
        if(booking.getNoOfRooms() > available_rooms)
        {
            return -1;
        }
        else {
            List<Booking> bookings = per_person_bookings.getOrDefault(booking.getBookingAadharCard(),new ArrayList<>());
            bookings.add(booking);
            per_person_bookings.put(booking.getBookingAadharCard(),bookings);
            int total_price = booking.getNoOfRooms() * hoteldb.get(booking.getHotelName()).getPricePerNight();
            booking.setAmountToBePaid(total_price);
            return total_price;
        }
    }

    public static int getBookings(Integer aadharCard) {
        return per_person_bookings.get(aadharCard).size();
    }

    public static Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hoteldb.get(hotelName);
        List<Facility> facilities = hotel.getFacilities();

        for(Facility f : newFacilities)
        {
            if(!solve(f,facilities))
            {
                facilities.add(f);
            }
        }
        hotel.setFacilities(facilities);
        return hotel;
    }

    private static boolean solve(Facility f, List<Facility> newFacilities) {
        for(Facility facility : newFacilities)
        {
            if(facility == f)
            {
                return true;
            }
        }
        return false;
    }
}

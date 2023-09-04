package com.driver.Repository;

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

     HashMap<String,Hotel> hoteldb = new HashMap<>();
     HashMap<Integer, User> userdb = new HashMap<>();
     HashMap<String, Booking> bookingsdb = new HashMap<>();
     HashMap<Integer, List<Booking>> per_person_bookings = new HashMap<>();

    public  String addHotel(Hotel hotel)
    {
        if(hotel == null || hotel.getHotelName() == null)
        {
            return "FAILURE";
        }
        else if(hoteldb.containsKey(hotel.getHotelName()))
        {
            return "FAILURE";
        }
        hoteldb.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public  Integer addUser(User user) {
        userdb.put(user.getAadharCardNo(),user);
        return user.getAadharCardNo();
    }

    public  String getHotelWithMostFacilities() {
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
            } else if (max_facilities == no_facilities && s.compareTo(max_hotelname) < 0) {
                max_hotelname = s;
            }
        }

        return max_hotelname;
    }

    public  int bookARoom(Booking booking) {
        String booking_Id = booking.getBookingId();
        bookingsdb.put(booking_Id,booking);

        int available_rooms = hoteldb.get(booking.getHotelName()).getAvailableRooms();
        if(available_rooms < booking.getNoOfRooms())
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

    public  int getBookings(Integer aadharCard) {
        return per_person_bookings.get(aadharCard).size();
    }

    public  Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
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

    private  boolean solve(Facility f, List<Facility> newFacilities) {
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

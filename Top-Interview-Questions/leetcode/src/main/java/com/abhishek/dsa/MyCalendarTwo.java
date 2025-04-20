package com.abhishek.dsa;

import java.util.Map;
import java.util.TreeMap;

public class MyCalendarTwo {

    TreeMap<Integer, Integer> bookings;
    int maxOverlappingBookings;


    public MyCalendarTwo() {
        bookings = new TreeMap<>();
        maxOverlappingBookings = 2;

    }

    public boolean book(int startTime, int endTime) {

        bookings.put(startTime, bookings.getOrDefault(startTime, 0) + 1);
        bookings.put(endTime, bookings.getOrDefault(endTime, 0) - 1);

        if(!checkOverlappingBookings()){
            return true;
        }else{
            bookings.put(startTime, bookings.get(startTime) - 1);
            bookings.put(endTime, bookings.get(endTime) + 1);

            if(bookings.get(startTime) == 0) bookings.remove(startTime);
            if(bookings.get(endTime) == 0) bookings.remove(endTime);

            return false;
        }
    }

    private boolean checkOverlappingBookings(){

        int prefixSum = 0;
        for(Map.Entry<Integer, Integer> booking : bookings.entrySet()){
            prefixSum += booking.getValue();
            if(prefixSum > maxOverlappingBookings) return true;
        }
        return false;
    }

}

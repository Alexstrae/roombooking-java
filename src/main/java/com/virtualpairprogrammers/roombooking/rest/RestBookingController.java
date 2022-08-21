package com.virtualpairprogrammers.roombooking.rest;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtualpairprogrammers.roombooking.data.BookingRepository;
import com.virtualpairprogrammers.roombooking.model.entities.Booking;

@RestController
@RequestMapping("/api/bookings")
public class RestBookingController {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@GetMapping("/{date}")
	public List<Booking> getBookingsByDate(@PathVariable("date") String date) {
		return bookingRepository.findAllByDate(Date.valueOf(date));
	}
	
	@DeleteMapping("/{id}")
	public void deleteBooking(@PathVariable("id") long id) {
		bookingRepository.deleteById(id);
	}
	
	@GetMapping
	public Booking getBooking(@RequestParam("id") long id) {
		return bookingRepository.findById(id).get();
	}
	
}
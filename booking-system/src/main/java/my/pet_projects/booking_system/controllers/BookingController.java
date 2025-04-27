package my.pet_projects.booking_system.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.BookingRequest;
import my.pet_projects.booking_system.dto.responses.BookingResponse;
import my.pet_projects.booking_system.models.BookingStatus;
import my.pet_projects.booking_system.services.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequestMapping("/bookings")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final ModelMapper modelMapper;

    @PostMapping("/new")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.createBooking(bookingRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) throws AccessDeniedException {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Бронь успешна отменена");
    }

    @GetMapping("/show")
    public ResponseEntity<List<BookingResponse>> getAllBookings(
            @RequestParam(required = false) List<BookingStatus> status) {

        return ResponseEntity.ok(bookingService.getUserBookings(status)
                .stream().map(booking -> modelMapper.map(booking, BookingResponse.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.getBooking(id));
    }
}

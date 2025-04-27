package my.pet_projects.booking_system.services;

import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.BookingRequest;
import my.pet_projects.booking_system.dto.responses.BookingResponse;
import my.pet_projects.booking_system.exceptions.ConflictException;
import my.pet_projects.booking_system.exceptions.ResourceNotFoundException;
import my.pet_projects.booking_system.models.Booking;
import my.pet_projects.booking_system.models.BookingStatus;
import my.pet_projects.booking_system.models.Room;
import my.pet_projects.booking_system.models.User;
import my.pet_projects.booking_system.repositories.BookingsRepository;
import my.pet_projects.booking_system.repositories.RoomsRepository;
import my.pet_projects.booking_system.repositories.UsersRepository;
import my.pet_projects.booking_system.security.MyUserDetails;
import my.pet_projects.booking_system.security.MyUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingsRepository bookingsRepository;
    private final RoomsRepository roomsRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        MyUserDetails currentUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentUserDetails.getUser();

        if (bookingRequest.getEndTime().isBefore(bookingRequest.getStartTime().plusHours(1))) {
            throw new IllegalArgumentException("Минимальное время бронирования - 1 час");
        }

        int countOfOverlaps = bookingsRepository.countOverLappingBookings(
                bookingRequest.getRoomId(),
                bookingRequest.getStartTime(),
                bookingRequest.getEndTime());

        if (countOfOverlaps > 0){
            throw new ConflictException("Комната уже забронирована на это время");
        }

        Optional<Room> room = roomsRepository.findById(bookingRequest.getRoomId());

        if (room.isEmpty()) {
            throw new ResourceNotFoundException("Комната с таким Id не существует ");
        }

        Booking booking = new Booking();
        booking.setUser(currentUser);
        booking.setRoom(room.get());
        booking.setStartTime(bookingRequest.getStartTime());
        booking.setEndTime(bookingRequest.getEndTime());

        return modelMapper.map(bookingsRepository.save(booking), BookingResponse.class);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) throws AccessDeniedException {
        Booking booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Бронь с таким id не найдена"));

        MyUserDetails currentUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentUserDetails.getUser();
        if (!isOwnerOrAdmin(currentUser, booking)){
            throw new AccessDeniedException("У вас нет разрешения на удаление");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingsRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(List<BookingStatus> statuses) {
        MyUserDetails currentUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = currentUserDetails.getUser();
        return bookingsRepository.findByUserIdAndStatus(
                currentUser.getId(),
                statuses!=null && !statuses.isEmpty()
                        ? statuses
                        : Arrays.asList(BookingStatus.values()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsToBeNotified(LocalDateTime start, LocalDateTime end) {
        return bookingsRepository.findByStartTimeBetweenAndNotifiedFalse(start, end);
    }

    @Override
    @Transactional
    public void saveBooking(Booking booking) {
        bookingsRepository.save(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsToBeCompleted(LocalDateTime currentTime, BookingStatus status) {
        return bookingsRepository.findByEndTimeBeforeAndStatus(currentTime, status);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long bookingId) {
        Booking booking = bookingsRepository.findById(bookingId).orElseThrow(
                () -> new ResourceNotFoundException("Бронь с таким id не найдена"));

        return modelMapper.map(booking, BookingResponse.class);
    }


    private boolean isOwnerOrAdmin(User user, Booking booking) {
        return booking.getUser().getId().equals(user.getId()) || user.getRoles().contains("ROLE_ADMIN");
    }

}

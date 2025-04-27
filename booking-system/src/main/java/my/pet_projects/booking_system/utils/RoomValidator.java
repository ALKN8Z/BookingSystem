package my.pet_projects.booking_system.utils;
import lombok.RequiredArgsConstructor;
import my.pet_projects.booking_system.dto.requests.RoomRequest;
import my.pet_projects.booking_system.models.Room;
import my.pet_projects.booking_system.services.RoomsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RoomValidator implements Validator {

    private final RoomsService roomsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Room.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomRequest roomRequest = (RoomRequest) target;
        if (roomsService.findRoomByName(roomRequest.getName()) != null) {
            errors.rejectValue("name", "", "Комната с таким названием уже существует");
        }
    }
}

package meeting_room_booking_system.controller;

import meeting_room_booking_system.dtos.user_dtos.DeleteUserRequest;
import meeting_room_booking_system.dtos.user_dtos.UserLoginRequest;
import meeting_room_booking_system.dtos.user_dtos.UserRequest;
import meeting_room_booking_system.dtos.user_dtos.UserResponse;
import meeting_room_booking_system.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping ("/users")
public class UserController {
    // for dependency injection
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    } // end of constructor

    @PostMapping ("/create")
    public void createUser (@Valid @RequestBody UserRequest newUser) {
        userService.saveUser(newUser);
    } // end of save user method

    @DeleteMapping ("/delete")
    public void deleteUser (@Valid @RequestBody DeleteUserRequest request) {
        userService.deleteUser(request);
    } // end of delete user method

    @PostMapping ("/login")
    public ResponseEntity <UserResponse> loginUser (@Valid @RequestBody UserLoginRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    } // end of login method
} // end of class body

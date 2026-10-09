package meeting_room_booking_system;

import meeting_room_booking_system.models.User;
import meeting_room_booking_system.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class ApplicationTesting {

    public static void main(String[] args) {
        // starts Spring Boot using THIS class instead of the JavaFX application
        SpringApplication.run(ApplicationTesting.class, args);
    }

    @Bean // tells Spring to manage the object returned by this method.
    public CommandLineRunner runConsoleTest(UserService userService) {
        return args -> {
            // Creating a Scanner to read keyboard inputs from the terminal
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=== CREATE USER CONSOLE TEST ===");

            System.out.print("Enter user email: ");
            String email = scanner.nextLine();

            System.out.print("Enter user password: ");
            String password = scanner.nextLine();

            System.out.println("Saving user to MySQL database...");

            try {
                // Instantiating the user with our clean, custom constructor
                User newUser = new User(email, password);

                // Saving through the service layer
                userService.saveUser(newUser);

                System.out.println("SUCCESS: User successfully saved to the database!");
            } catch (Exception e) {
                System.err.println("ERROR: An error occurred while saving the user.");
                e.printStackTrace();
            }

            System.out.println("=================================\n");
            scanner.close();

            // Optional: Closes the application automatically after the test finishes
            System.exit(0);
        };
    }
}

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main 
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookingManager bookingManager = new BookingManager();          // instance to handle booking operations
    private static final String[] roomTypes = {"Classroom", "Lab", "Seminar Room", "Committee Room"};
    
    public static void main(String[] args) 
    {
        System.out.println("------- SCIS School Resource Booking System -------");
        while (true) 
        {
            System.out.println("\nMain Menu:");
            System.out.println("1. View Resources\n2. Booking\n3. Check Availability\n4. Exit");
            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) 
            {
                case 1: view(); break;                  // view all available resources
                case 2: create(); break;                // create a new booking
                case 3: availability(); break;          // checking room availability
                case 4: 
                    System.out.println("\nSaving bookings...");
                    bookingManager.saveBookings();
                    return;             // exit
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void create() 
    {
        System.out.println("\n-----------Booking-----------");
        Resource room = selectRoom();
        if (room == null) return;
        System.out.print("\nEnter your user ID:\nIf student (XXMCCEXX / XXMCMEXX) or \nIf Teacher(XXXX): ");
        String userId = scanner.nextLine();
        if (!isValidUserId(userId)) 
        {
            System.out.println("Error: user not found - invalid user ID format");
            return;
        }
        // booking times and purpose
        LocalDateTime[] times = getBookingTimes();
        if (times == null) return;
        System.out.print("Enter booking purpose: ");
        String purpose = scanner.nextLine();
        // create booking
        try 
        {
            Booking booking = bookingManager.createBooking(room, userId, times[0], times[1], purpose);
            System.out.println("\nBooking created successfully!");
            System.out.println("Booking ID: " + booking.getBookingId());
        } 
        catch (Exception e) 
        {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    // validating user id
    private static boolean isValidUserId(String userId) 
    {
        if (userId.length() == 4) 
        {
            return userId.matches("[A-Za-z0-9]{4}");
        }

        if (userId.length() == 8) 
        {
            return userId.matches("[A-Za-z0-9]{2}MCCE[A-Za-z0-9]{2}") || userId.matches("[A-Za-z0-9]{2}MCME[A-Za-z0-9]{2}");
        }
        return false;
    }
    private static Resource selectRoom() 
    {
        // displaying room types
        System.out.println("Select room type:");
        for (int i = 0; i < roomTypes.length; i++) 
        {
            System.out.println((i + 1) + ". " + roomTypes[i]);
        }
        System.out.println((roomTypes.length + 1) + ". Back to Main Menu");
        System.out.print("Enter your choice: ");
        // room type 
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == roomTypes.length + 1) return null;
        if (choice < 1 || choice > roomTypes.length) 
        {
            System.out.println("Invalid choice.");
            return null;
        }
        // available rooms 
        String type = roomTypes[choice - 1];
        List<Resource> rooms = bookingManager.getResourcesByType(type);
        if (rooms.isEmpty()) 
        {
            System.out.println("No " + type + "s available.");
            return null;
        }
        // displaying available rooms
        System.out.println("\nAvailable " + type + "s:");
        for (int i = 0; i < rooms.size(); i++) 
        {
            System.out.println((i + 1) + ". " + rooms.get(i));
        }
        // user's selection
        System.out.print("Select any options from above: ");
        int roomChoice = scanner.nextInt();
        scanner.nextLine();

        return (roomChoice > 0 && roomChoice <= rooms.size()) ? rooms.get(roomChoice - 1) : null;
    }

    private static LocalDateTime[] getBookingTimes() 
    {
        System.out.println("\nEnter booking time (24-hour format):");
        System.out.print("Start Hour (0-23): ");
        int startHour = scanner.nextInt();
        System.out.print("Start Minute (0-59): ");
        int startMinute = scanner.nextInt();
        System.out.print("Duration in hours: ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        // start and end times
        LocalDateTime startTime = LocalDateTime.now()
                .withHour(startHour)
                .withMinute(startMinute)
                .withSecond(0)
                .withNano(0);
        LocalDateTime endTime = startTime.plusHours(duration);

        return new LocalDateTime[]{startTime, endTime};
    }

    private static void view() 
    {
        System.out.println("\n ------------Resources Availability------------");
        for (String type : roomTypes) 
        {
            System.out.println("\n" + type + "s:");
            List<Resource> rooms = bookingManager.getResourcesByType(type);
            if (rooms.isEmpty()) 
            {
                System.out.println("None available");
            } 
            else 
            {
                for (Resource room : rooms) 
                {
                    // displayingdetails of room type
                    switch (type) 
                    {
                        case "Classroom": ((Classroom)room).display(); break;
                        case "Lab": ((Lab)room).display(); break;
                        case "Seminar Room": ((SeminarRoom)room).display(); break;
                        case "Committee Room": ((CommitteeRoom)room).display(); break;
                    }
                }
            }
        }
    }

    private static void availability() 
    {
        System.out.println("\nEnter time to check availability (24-hour format):");
        LocalDateTime[] times = getBookingTimes();
        if (times == null) return;
        System.out.println("\nRoom Status for " + times[0] + " to " + times[1] + ":");
        for (String type : roomTypes) 
        {
            System.out.println("\n" + type + "s:");
            displayRoomStatus(type, times[0], times[1]);
        }
    }

    private static void displayRoomStatus(String type, LocalDateTime startTime, LocalDateTime endTime) 
    {
        // lists of available and booked rooms
        List<Resource> available = bookingManager.getAvailableResourcesByType(startTime, endTime, type);
        List<Resource> booked = bookingManager.getBookedResourcesByType(startTime, endTime, type);
        // displaying available rooms
        System.out.println("**Available:");
        if (available.isEmpty()) 
        { System.out.println("-- None available"); } 
        else 
        {
            for (Resource room : available) 
            {
                System.out.println("-- " + room);
            }
        }
        // displaying booked rooms
        System.out.println("**Booked:");
        if (booked.isEmpty()) { System.out.println("-- None booked"); } 
        else 
        {
            for (Resource room : booked) 
            {
                System.out.println("-- " + room);
            }
        }
    }
}
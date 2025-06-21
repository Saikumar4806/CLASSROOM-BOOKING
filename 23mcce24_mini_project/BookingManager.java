import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BookingManager 
{
    private List<Booking> bookings;      // for list of bookings
    private List<Resource> resources;    // for list of resources
    private Random random;               // for random booking ids
    private Scanner scanner;             // for user input
    private static final String BOOKINGS_FILE = "bookings.txt";

    // constructor 
    public BookingManager() 
    {
        bookings = new ArrayList<>();
        resources = new ArrayList<>();
        random = new Random();
        scanner = new Scanner(System.in);
        initializeResources();
        loadBookings(); // load existing bookings when starting
    }

    // loading bookings 
    private void loadBookings() 
    {
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) 
        {
            System.out.println("No previous bookings file found.");
            return;
        }
        
        try 
        {
            List<Booking> savedBookings = Booking.loadBookings(BOOKINGS_FILE);
            if (savedBookings != null) 
            {
                bookings = savedBookings;
                System.out.println("Successfully loaded " + bookings.size() + " previous bookings.");
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error loading bookings: " + e.getMessage());
            System.out.println("Starting with empty bookings list.");
        }
    }

    // saving bookings
    public void saveBookings() 
    {
        try 
        {
            File file = new File(BOOKINGS_FILE);
            if (!file.exists()) { file.createNewFile(); }
            Booking.saveBookings(bookings, BOOKINGS_FILE);
            System.out.println("Successfully saved " + bookings.size() + " bookings.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving bookings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeResources() 
    {
        // initializing classrooms
        for (int i = 1; i <= 13; i++) 
        {
            resources.add(new Classroom("CL" + i, "Class " + i, "Ambedkar Lecture Hall", "Classroom"));
        }
        // initializing lab
        resources.add(new Lab("LAB1", "AI Lab", "AI Lab", "Lab"));
        // initializing seminar rooms
        resources.add(new SeminarRoom("SR1", "New SCIS Seminar Room", "New SCIS", "Seminar Room"));
        resources.add(new SeminarRoom("SR2", "Ambedkar Lecture Hall Seminar Room", "Ambedkar Lecture Hall", "Seminar Room"));
        // initializing committee rooms
        resources.add(new CommitteeRoom("CR1", "Old SCIS Committee Room", "Old SCIS", "Committee Room"));
    }

    public Resource selectRoom() 
    {
        // display room types
        System.out.println("Select room type:\n1. Classroom\n2. Lab\n3. Seminar Room\n4. Committee Room");
        int choice = scanner.nextInt();
        scanner.nextLine();
        String type;
        switch (choice) 
        {
            case 1: type = "Classroom"; break;
            case 2: type = "Lab"; break;
            case 3: type = "Seminar Room"; break;
            case 4: type = "Committee Room"; break;
            default:
                System.out.println("Invalid choice!");
                return null;
        }
        // get available rooms
        List<Resource> rooms = getResourcesByType(type);
        if (rooms.isEmpty()) { return null; }
        // display available rooms
        System.out.println("Available " + type + "s:");
        for (int i = 0; i < rooms.size(); i++) 
        {
            System.out.println((i + 1) + ". " + rooms.get(i));
        }
        // get user's room selection
        System.out.print("Select any options from above: ");
        int roomChoice = scanner.nextInt();
        scanner.nextLine();
        return (roomChoice > 0 && roomChoice <= rooms.size()) ? rooms.get(roomChoice - 1) : null;
    }

    // get all resources 
    public List<Resource> getResourcesByType(String type) 
    {
        List<Resource> filtered = new ArrayList<>();
        for (Resource r:resources) 
        {
            if (r.getType().equals(type)) filtered.add(r);
        }
        return filtered;
    }

    // create a new booking
    public Booking createBooking(Resource resource, String userId, LocalDateTime startTime, LocalDateTime endTime, String purpose) 
    {
        if (!resources.contains(resource)) { throw new IllegalArgumentException("Resource not found\n--------------------------------"); }
        if (BookingConflict(resource, startTime, endTime)) { throw new IllegalStateException("Time slot already booked\n--------------------------"); }
        String bookingId = String.valueOf(random.nextInt(9000) + 1000);
        Booking booking = new Booking(bookingId, resource, userId, startTime, endTime, purpose);
        bookings.add(booking);
        saveBookings();     // saving after adding new booking
        return booking;
    }

    // check if there is any booking clash at a given time slot
    private boolean BookingConflict(Resource resource, LocalDateTime startTime, LocalDateTime endTime) 
    {
        for (Booking b : bookings) 
        {
            if (b.getResource().equals(resource)) 
            {
                LocalDateTime existingStart = b.getstart();
                LocalDateTime existingEnd = b.getend();
                if (!(endTime.isBefore(existingStart) || startTime.isAfter(existingEnd))) { return true; }
            }
        }
        return false;
    }

    // get all available resources for a given time slot
    public List<Resource> getAvailableResourcesByType(LocalDateTime startTime, LocalDateTime endTime, String type) 
    {
        List<Resource> available = new ArrayList<>();
        for (Resource r : resources) 
        {
            if (r.getType().equals(type) && !BookingConflict(r, startTime, endTime)) { available.add(r); }
        }
        return available;
    }

    // get all bookings 
    public List<Booking> getBookingsByUser(String userId) 
    {
        List<Booking> userBookings = new ArrayList<>();
        for (Booking b : bookings) 
        {
            if (b.getUserId().equals(userId)){ userBookings.add(b); }
        }
        return userBookings;
    }

    // get all booked resources for a given time slot
    public List<Resource> getBookedResourcesByType(LocalDateTime startTime, LocalDateTime endTime, String type) 
    {
        List<Resource> booked = new ArrayList<>();
        for (Booking b : bookings) 
        {
            Resource r = b.getResource();
            if (r.getType().equals(type) && BookingConflict(r, startTime, endTime)) { booked.add(r); }
        }
        return booked;
    }
}
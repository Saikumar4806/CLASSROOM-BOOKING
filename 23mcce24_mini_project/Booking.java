import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String bookingId;           // for booking id
    private Resource resource;          // for resource
    private String userId;              // for user id
    private LocalDateTime start;        // for start time
    private LocalDateTime end;          // for end time
    private String purpose;             // for purpose

    // constructor
    public Booking(String bookingId, Resource resource, String userId, LocalDateTime start, LocalDateTime end, String purpose) 
    {
        this.bookingId = bookingId;
        this.resource = resource;
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.purpose = purpose;
    }

    // getters for booking properties
    public String getBookingId() { return bookingId; }
    public Resource getResource() { return resource; }
    public String getUserId() { return userId; }
    public LocalDateTime getstart() { return start; }
    public LocalDateTime getend() { return end; }
    public String getPurpose() { return purpose; }

    // string representation of the booking
    @Override
    public String toString() 
    {
        return "Booking{" + "bookingId='" +bookingId+ '\'' +", resource=" +resource+", userId='" +userId+ '\'' +", start=" +start+", end=" +end+", purpose='" +purpose+ '\'' +"}";
    }

    // method to save a list of bookings to a file
    public static void saveBookings(List<Booking> bookings, String filename) throws IOException 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) 
        {
            for (Booking booking : bookings) 
            {
                Resource resource = booking.getResource();
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n", booking.getBookingId(), booking.getUserId(), resource.getId(), resource.getName(), resource.getLocation(), resource.getType(), booking.getstart(), booking.getend(), booking.getPurpose()));
            }
        }
    }

    // method to load bookings from a file
    public static List<Booking> loadBookings(String filename) throws IOException 
    {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length == 9) 
                {
                    String bookingId = parts[0];
                    String userId = parts[1];
                    String resourceId = parts[2];
                    String resourceName = parts[3];
                    String resourceLocation = parts[4];
                    String resourceType = parts[5];
                    LocalDateTime start = LocalDateTime.parse(parts[6]);
                    LocalDateTime end = LocalDateTime.parse(parts[7]);
                    String purpose = parts[8];
                    
                    // Create a resource with complete information
                    Resource resource;
                    switch (resourceType) 
                    {
                        case "Classroom":
                            resource = new Classroom(resourceId, resourceName, resourceLocation, resourceType);
                            break;
                        case "Lab":
                            resource = new Lab(resourceId, resourceName, resourceLocation, resourceType);
                            break;
                        case "Seminar Room":
                            resource = new SeminarRoom(resourceId, resourceName, resourceLocation, resourceType);
                            break;
                        case "Committee Room":
                            resource = new CommitteeRoom(resourceId, resourceName, resourceLocation, resourceType);
                            break;
                        default:
                            resource = new Resource(resourceId, resourceName, resourceLocation, resourceType) {};
                    }
                    bookings.add(new Booking(bookingId, resource, userId, start, end, purpose));
                }
            }
        }
        return bookings;
    }
}
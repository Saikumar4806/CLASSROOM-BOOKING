import java.io.*;
import java.util.List;

public class BookingReader 
{
    public static void main(String[] args) 
    {
        String filename = "bookings.txt";    // file for booking data
        File file = new File(filename);      // file object to check existence
        // check if the bookings file exists
        if (!file.exists()) 
        {
            System.out.println("bookings.txt file not found!");
            return;
        }

        try 
        {
            // load all bookings from the file
            List<Booking> bookings = Booking.loadBookings(filename);
            // check if there are any other bookings
            if (bookings == null || bookings.isEmpty()) 
            { 
                System.out.println("No bookings found in the file.");
                return;
            }
            // display header 
            System.out.println("\n=== Current Bookings ===");
            System.out.println("Total bookings: " + bookings.size());
            System.out.println("\nDetailed Booking Information:");
            System.out.println("----------------------------");
            // display details 
            for (Booking booking : bookings) 
            {
                System.out.println("Booking ID: " + booking.getBookingId());
                System.out.println("User ID: " + booking.getUserId());
                System.out.println("Resource: " + booking.getResource().getName() + " (" + booking.getResource().getLocation() + ")");
                System.out.println("Start Time: " + booking.getstart());
                System.out.println("End Time: " + booking.getend());
                System.out.println("Purpose: " + booking.getPurpose());
                System.out.println("----------------------------");
            }
        }
        catch (IOException e)    
        {
            System.out.println("Error reading bookings.txt: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 
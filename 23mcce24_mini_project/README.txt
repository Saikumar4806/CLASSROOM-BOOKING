SCIS School Resource Booking System
=====================================

Project Overview :
------------------
This is a Java-based resource booking system designed for SCIS to manage and book various resources like classrooms, labs, seminar rooms, and committee rooms. The system allows users to check resource availability, make bookings, and view booking details.

Features :
----------
1. Resource Management
   - Multiple resource types: Classrooms, Labs, Seminar Rooms, Committee Rooms
   - Each resource has unique properties and facilities
   - Resources are categorized by type and location
2. Booking System
   - Create new bookings with user ID, time slot, and purpose
   - Check resource availability for specific time slots
   - View all available and booked resources
   - Automatic conflict detection for overlapping bookings
3. User Management
   - Support for different user types (Students and Teachers)
   - Student ID format: XXMCCEXX or XXMCMEXX
   - Teacher ID format: XXXX (4 characters)
4. Data Persistence
   - All bookings are stored in a text file (bookings.txt)
   - Data persists between program runs
   - Automatic saving of bookings

Technical Implementation :
--------------------------
1. Object-Oriented Design
   - Inheritance: Resource class hierarchy
   - Polymorphism: Different resource types
   - Encapsulation: Private fields with public getters
   - Abstraction: Abstract Resource class
2. File Handling
   - Text file-based storage (bookings.txt)
   - BufferedWriter for saving data
   - BufferedReader for loading data
   - CSV format for data storage

How to Use :
------------
1. Running the Program
   - Compile: javac *.java
   - Run: java Main
2. Main Menu Options
   a) View Resources
      - Shows all available resources by type
      - Displays resource details and facilities
   b) Booking
      - Select resource type
      - Enter user ID
      - Specify booking time (24-hour format)
      - Enter booking purpose
      - System checks for conflicts
      - Booking ID is generated
   c) Check Availability
      - Enter time slot to check
      - View available and booked resources
      - See detailed room status
   d) Exit
      - Saves all bookings
      - Gracefully exits the program
3. Making a Booking
   - Choose option 2 from main menu
   - Select resource type (1-4)
   - Enter valid user ID
   - Enter start time (hour and minute)
   - Enter duration in hours
   - Enter booking purpose
   - System confirms booking with ID
4. Checking Availability
   - Choose option 3 from main menu
   - Enter time slot to check
   - View available and booked resources
   - See which rooms are free

Resource Types and Facilities :
-------------------------------
1. Classrooms
   - Projector
   - Whiteboard
   - 60 seats capacit
2. Labs
   - Computers
   - Chairs
   - Air conditioning
   - Software: C, C++, Java, Python
   - 75 seats capacity
3. Seminar Rooms
   - Projector
   - Sound System
   - 100 seats capacity
4. Committee Rooms
   - Projector
   - Whiteboard
   - Conference Call facilities
   - 120 seats capacity

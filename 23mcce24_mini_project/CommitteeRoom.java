import java.io.Serializable;

public class CommitteeRoom extends Resource implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean Projector;           // for projector
    private boolean Whiteboard;          // for whiteboard
    private boolean ConferenceCall;      // for conference call facilities
    private int seatingCapacity;         // for number of seats

    // constructor
    public CommitteeRoom(String id, String name, String location, String type) 
    {
        super(id, name, location, type);
        this.Projector = true;          
        this.Whiteboard = true;         
        this.ConferenceCall = true;     
        this.seatingCapacity = 120;     
    }

    // getters for committee room properties
    public boolean Projector() { return Projector; }
    public boolean Whiteboard() { return Whiteboard; }
    public boolean ConferenceCall() { return ConferenceCall; }
    public int getSeatingCapacity() { return seatingCapacity; }

    // information about the committee room
    public void display() 
    {
        System.out.println("Committee Room: " + getName() + " (" + getLocation() + ")");
    }

    // string representation of the committee room
    @Override
    public String toString() 
    {
        return getName() + " (" + getLocation() + ")";
    }
} 
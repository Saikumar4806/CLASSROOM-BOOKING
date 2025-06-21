import java.io.Serializable;

public class SeminarRoom extends Resource implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean Projector;           // for projector
    private boolean SoundSystem;         // for sound system
    private int seatingCapacity;         // for number of seats

    // constructor
    public SeminarRoom(String id, String name, String location, String type) 
    {
        super(id, name, location, type);
        this.Projector = true;          
        this.SoundSystem = true;        
        this.seatingCapacity = 100;     
    }

    // getters for seminar room properties
    public boolean Projector() { return Projector; }
    public boolean SoundSystem() { return SoundSystem; }
    public int getSeatingCapacity() { return seatingCapacity; }

    // information about the seminar room
    public void display() 
    {
        System.out.println("Seminar Room: " + getName() + " (" + getLocation() + ")");
    }

    // string representation of the seminar room
    @Override
    public String toString() 
    {
        return getName() + " (" + getLocation() + ")";
    }
} 
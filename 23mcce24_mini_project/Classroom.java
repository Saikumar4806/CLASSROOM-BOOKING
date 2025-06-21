import java.io.Serializable;

public class Classroom extends Resource implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean projector;           // for projector 
    private boolean board;               // for whiteboard
    private int seatingCapacity;         // for number of seats

    // constructor
    public Classroom(String id, String name, String location, String type) 
    {
        super(id, name, location, type);
        this.projector = true;          
        this.board = true;              
        this.seatingCapacity = 60;      
    }

    // getters for classroom properties
    public boolean Projector() { return projector; }
    public boolean Board() { return board; }
    public int getSeatingCapacity() { return seatingCapacity; }

    // information about the classroom
    public void display() 
    {
        System.out.println("Classroom: " + getName() + " (" + getLocation() + ")");
    }
    
    // string representation of the classroom
    @Override
    public String toString() 
    {
        return getName()+" (" +getLocation()+")";
    }
}
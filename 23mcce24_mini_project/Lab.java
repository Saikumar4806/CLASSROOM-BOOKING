import java.io.Serializable;

public class Lab extends Resource implements Serializable
{
    private static final long serialVersionUID = 1L;

    private boolean Computers;          // for lab computers
    private boolean chairs;             // for lab chairs
    private boolean ac;                 // for lab air conditioning
    private String[] software;          // for list of available software
    private int capacity;               // for maximum capacity of the lab

    // constructor
    public Lab(String id, String name, String location, String type) 
    {
        super(id, name, location, type);
        this.Computers = true;          
        this.chairs = true;            
        this.ac = true;   
        this.software = new String[]{"C", "C++", "Java", "Python"};  
        this.capacity = 75;               
    }

    // getters for lab properties
    public boolean Computers() { return Computers; }
    public boolean chairs() { return chairs; }
    public boolean ac() { return ac; }
    public String[] getSoftware() { return software; }
    public int getCapacity() { return capacity; }

    // information about the lab
    public void display() 
    {
        System.out.println("Lab: " + getName() + " (" + getLocation() + ")");
    }

    // string representation of the lab
    @Override
    public String toString() 
    {
        return getName() + " (" + getLocation() + ")";
    }
}
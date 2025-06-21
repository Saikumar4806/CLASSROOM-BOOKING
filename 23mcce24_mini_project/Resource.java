import java.io.Serializable;
public abstract class Resource implements Serializable
{
    private String id;          // id for the resource
    private String name;        // name of the resource
    private String location;    // location of the resource
    private String type;        // type of resource (Classroom,Lab,Seminar Room,Committee Room)

    // constructor 
    public Resource(String id, String name, String location, String type) 
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }
    // getters for resource properties
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getType() { return type; }

    // string representation of the resource
    @Override
    public String toString() 
    {
        return name+"("+location+")";
    }

    // check if two resources are equal
    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return id.equals(resource.id);
    }

    // hash code of the resource
    @Override
    public int hashCode() 
    {
        return id.hashCode();
    }
}
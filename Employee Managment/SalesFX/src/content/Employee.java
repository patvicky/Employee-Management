package content;


public class Employee {

    private int id;
    private String name;
    private String city;
    private String position;

    public Employee(int id, String name, String city, String position) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.position = position;
    }

    public Employee(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + city + ", " + position;
    }

    public String toStringData() {
        return id + "  " + name + "  " + city + "  " + position;
    }
}

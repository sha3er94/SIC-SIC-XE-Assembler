package system_project;
public class Literals
{
    String name;
    String value;
    int length;
    int address;

    public Literals(String name, String value, int length) 
    {
        this.name = name;
        this.value = value;
        this.length = length;
    }
    public Literals() 
    {
    }

    public Literals(String name, String value, int length, int address) {
        this.name = name;
        this.value = value;
        this.length = length;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    
    
}

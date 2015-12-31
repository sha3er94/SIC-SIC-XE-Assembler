package system_project;
public class Blocks 
{
 String name;
 int number;
 int StartAdress;
 int LOC;

    public Blocks(String Name, int number, int StartAdress, int LOC)
    {
        this.name = Name;
        this.number = number;
        this.StartAdress = StartAdress;
        this.LOC = LOC;
    }

    public Blocks() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStartAdress() {
        return StartAdress;
    }

    public void setStartAdress(int StartAdress) {
        this.StartAdress = StartAdress;
    }

    public int getLOC() {
        return LOC;
    }

    public void setLOC(int LOC) {
        this.LOC = LOC;
    }
    
 
}

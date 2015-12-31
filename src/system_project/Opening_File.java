package system_project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
public class Opening_File 
{
     Scanner file;
     
     boolean openfile()
     {
         //String path = System_Project.browse();
         boolean x = false;
       try 
        {
          file = new Scanner(new File(System_Project.path));
          return true;
        } 
        catch(Exception e)
        { 
            System.out.println("File Not Found : "+System_Project.path);
            return false;
            //System.exit(0);
        }
       
     }
      //reading the file into an arraylist
    ArrayList<String> readfile () throws FileNotFoundException
    {   
        
        openfile();
        if (openfile()==true){
        ArrayList<String> thefile = new ArrayList<>();
        while(file.hasNextLine())
        {  
            thefile.add(file.nextLine());
        }
        return thefile;
        }
        else
       return null;
    }
     public void closeFile()
    {
        file.close();
    } 
     //generating the listfile for the errors
         public void Outputfile(String fileName, ArrayList<Assembly> lines) throws FileNotFoundException {
        PrintWriter output = new PrintWriter(new File("ListFile"));
        for (int i = 0; i < lines.size(); i++) 
        {
            output.println(String.format("%10s%10s%10s%10s%10s\n%20s",
                    Integer.toHexString(lines.get(i).getPc()), lines.get(i).getLabel(),
                    lines.get(i).getOpCode(), lines.get(i).getOperand(), lines.get(i).getObjCode(),
                    lines.get(i).getError() ));      }
        output.close();

    }
}
   


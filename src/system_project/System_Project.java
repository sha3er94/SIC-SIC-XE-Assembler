package system_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.script.ScriptException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class System_Project
{
    public static String path;
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
            GUI.main(args);
//          Opening_File x=new Opening_File();
//           ArrayList<String> a=x.readfile();
//            Pass_One pass1 = new Pass_One();
//            pass1.run(a);
//            Pass_Two pass2 = new Pass_Two();
//            pass2.run(pass1.symtab,pass1.lines,pass1.table);
//            x.Outputfile("LISTFILE", pass1.lines);
//            Object_Program s = new Object_Program();
//            s.run("OBJECTFILE" , pass1.progName, pass1.lines);
    }  
    
    public static boolean runGUI(String path2) throws FileNotFoundException, IOException, ScriptException{
        if (path2.equals(""))
            return false;
        else{
            Opening_File x=new Opening_File();
           ArrayList<String> a=x.readfile();
            Pass_One pass1 = new Pass_One();
            pass1.run(a);
            Pass_Two pass2 = new Pass_Two();
            pass2.run(pass1.symtab,pass1.lines,pass1.table);
            x.Outputfile("LISTFILE", pass1.lines);
            Object_Program s = new Object_Program();
            s.run("OBJECTFILE" , pass1.progName, pass1.lines);
        }
        return true;
    }
    static void runLisFile(String path2) throws IOException{
        String path3 = path2.replaceAll("\\SRCFILE1","");
        System.out.println("path 3 "+path3);
Runtime rt=Runtime.getRuntime();

String file=path3+"\\"+"ListFile";

Process p=rt.exec("notepad "+file);

}
    static void runObjfile(String path2) throws IOException{
        String path3 = path2.replaceAll("\\SRCFILE1","");
        System.out.println("obj file path "+path3);
Runtime rt=Runtime.getRuntime();

String file=path3+"\\"+"OBJECTFILE";

Process p=rt.exec("notepad "+file);

}
static void runSRCFILE(String path2) throws IOException{

String path3 = path2.replaceAll("\\SRCFILE1","");
Runtime rt=Runtime.getRuntime();

String file=path3+"\\"+"SRCFILE1";;

Process p=rt.exec("notepad "+file);

}
public static String  browse()  {
		JFrame  f = new JFrame () ;
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(f);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().getAbsolutePath();
		   return   chooser.getSelectedFile().getAbsolutePath();
                }
                
		return "";
	}
   
}

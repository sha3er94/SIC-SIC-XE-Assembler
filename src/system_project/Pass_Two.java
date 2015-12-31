package system_project;

import java.util.ArrayList;
import java.util.Hashtable;

public class Pass_Two
{
     
    public void run(Hashtable<String, Integer> symtab, ArrayList<Assembly> lines, ArrayList<Literals> l) 
    {
        HashTable optab = new HashTable();
        String instCode;
        String opCode;
        for (int i = 0; i < lines.size(); i++) {

            opCode = "";
            instCode = "";

            if (lines.get(i).Flag == false) 
            {
                continue;
            }
            //Word Condition
            if (lines.get(i).getOpCode().equalsIgnoreCase("Word")) {
                // Handling error if operand is not numbers
                try {
                    Integer.parseInt(lines.get(i).operand);
                    opCode = Integer.toHexString(Integer.parseInt(lines.get(i).operand));
                    lines.get(i).objCode = formatZeros(opCode, 6);
                } catch (Exception e) 
                {
                    lines.get(i).setError("**** undefined symbol in operand");
                }
            }
           /* else if(!(lines.get(i).getOpCode().isEmpty()) && !((lines.get(i).getOpCode().equalsIgnoreCase("start")) || (lines.get(i).getOpCode().equalsIgnoreCase("end")) || (lines.get(i).getOpCode().equalsIgnoreCase("resw")) || (lines.get(i).getOpCode().equalsIgnoreCase("resb")) || (lines.get(i).getOpCode().equalsIgnoreCase("ltorg"))  || (lines.get(i).getOpCode().equalsIgnoreCase("equ"))  || (lines.get(i).getOpCode().equalsIgnoreCase("org"))  || (lines.get(i).getOpCode().equalsIgnoreCase("word")) || (lines.get(i).getOpCode().equalsIgnoreCase("byte")) || (lines.get(i).getOpCode().equalsIgnoreCase("jsub")) || (lines.get(i).getOpCode().equalsIgnoreCase("rsub")) || (lines.get(i).getOpCode().startsWith("=")) || (lines.get(i).operand.startsWith("="))))
            {
             if(!(lines.get(i).operand.matches(".*\\d+.*")))
              {     
                if(lines.get(i).operand.contains("+") && lines.get(i).operand.startsWith("-"))
             {    
                String h = lines.get(i).operand.substring(1, lines.get(i).operand.indexOf("+"));
                String h1 = lines.get(i).operand.substring(lines.get(i).operand.indexOf("+")+1);
                int v = 0;
                int v1 = 0;
                if (symtab.containsKey(h1) && symtab.contains(h))
                { 
                    v = symtab.get(h);
                   v1 = symtab.get(h1);
                  int y = v1 - v;
                y = Math.abs(y);
                opCode += Integer.toHexString(y);
                if (optab.optab.containsKey(lines.get(i).OpCode.toUpperCase()))
                {
                instCode = optab.optab.get(lines.get(i).OpCode.toUpperCase());
                } 
                else 
                {
                    lines.get(i).setError("**** unrecognized operation code");
                }
                    
                    while (opCode.length() < 4) {
                        opCode = "0" + opCode;
                    }
                     lines.get(i).objCode = (instCode+opCode).toUpperCase();
                     }
                else
                {
                    lines.get(i).setError("   ****Operand not defined");
                }
                }
               else if(lines.get(i).operand.contains("-") && !(lines.get(i).operand.startsWith("-")))
                {    
                  String h = lines.get(i).operand.substring(0, lines.get(i).operand.indexOf("-"));
                  String h1 = lines.get(i).operand.substring(lines.get(i).operand.indexOf("-")+1);
                  int v = 0;
                  int v1 = 0;
                if (symtab.containsKey(h) && symtab.contains(h1))
              { 
                v = symtab.get(h);
                v1 = symtab.get(h1);
                int y = v - v1;
                y = Math.abs(y);
                opCode += Integer.toHexString(y);
                 if (optab.optab.containsKey(lines.get(i).OpCode.toUpperCase()))
                {
                 instCode = optab.optab.get(lines.get(i).OpCode.toUpperCase());
                }
             else 
                 {
                    lines.get(i).setError("**** unrecognized operation code");
                }
                      while (opCode.length() < 4) {
                        opCode = "0" + opCode;
                    }
                      lines.get(i).objCode = (instCode+opCode).toUpperCase();
                }
                  else
                      lines.get(i).setError("   *****Operand Not Found");
                }
               else if(!(lines.get(i).operand.contains("+") && lines.get(i).operand.contains("-"))){
                    if (symtab.containsKey(lines.get(i).operand)) 
                        opCode += Integer.toHexString(symtab.get(lines.get(i).operand));
                    else 
                        lines.get(i).setError("   *****Operand Not Found");
                    if (optab.optab.containsKey(lines.get(i).OpCode.toUpperCase())) {
                    instCode = optab.optab.get(lines.get(i).OpCode.toUpperCase());
                } else {
                    lines.get(i).setError("**** unrecognized operation code");
                }
                      while (opCode.length() < 4) {
                        opCode = "0" + opCode;
                    }
                  lines.get(i).objCode = (instCode+opCode).toUpperCase();
                    
                }
                }
            else if (lines.get(i).operand.startsWith("=")) {
                    for (int j = 0; j < l.size(); j++) {
                        if (l.get(j).name.equalsIgnoreCase(lines.get(i).operand)) {
                            opCode = Integer.toHexString(l.get(j).address);
                        }
                    }
                }        
                else if(lines.get(i).getOpCode().matches(".*\\d+.*"))
                {
                    String h = lines.get(i).operand;
                    int z= Integer.parseInt(h);
                    opCode+=Integer.toHexString(z);
                  if (optab.optab.containsKey(lines.get(i).OpCode.toUpperCase())) {
                    instCode = optab.optab.get(lines.get(i).OpCode.toUpperCase());
                } else {
                    lines.get(i).setError("**** unrecognized operation code");
                }
                        lines.get(i).objCode = (instCode+opCode).toUpperCase();
                    
                }
            } */
      /// Byte Condition
            else if (lines.get(i).getOpCode().equalsIgnoreCase("Byte")) {
                if (lines.get(i).operand.startsWith("C'") || lines.get(i).operand.startsWith("c'")) {
                    String byteVal = lines.get(i).operand.substring(2, lines.get(i).operand.length() - 1);
                    for (int j = 0; j < byteVal.length(); j++) {
                        opCode += Integer.toHexString((int) (byteVal.charAt(j)));
                    }
                    while (opCode.length() < 6) {
                        opCode = "0" + opCode;
                    }
                }
                if (lines.get(i).operand.startsWith("X'") || lines.get(i).operand.startsWith("x'")) {
                    opCode = lines.get(i).operand.substring(2, lines.get(i).operand.length() - 1);

                }
                lines.get(i).objCode = opCode.toUpperCase();

            } 
            else if (lines.get(i).getOpCode().startsWith("=")) 
            {
                if (lines.get(i).getOpCode().contains("c'") || lines.get(i).getOpCode().contains("C'")) {
                    String byteVal = lines.get(i).getOpCode().substring(3, lines.get(i).getOpCode().length() - 1);
                    for (int j = 0; j < byteVal.length(); j++) {
                        opCode += Integer.toHexString((int) (byteVal.charAt(j)));
                    }
                    while (opCode.length() < 6) {
                        opCode = "0" + opCode;
                    }
                }
             else if (lines.get(i).getOpCode().contains("x'") || lines.get(i).getOpCode().contains("X'")) {
                    opCode = lines.get(i).getOpCode().substring(3, lines.get(i).getOpCode().length() - 1);
                }
                else if(lines.get(i).getOpCode().contains("*"))
                {
                    String byteVal = lines.get(i).getOpCode().substring(1,lines.get(i).getOpCode().length());
                    for(int j=0 ; j < byteVal.length() ; j++)
                    {
                      opCode += Integer.toHexString((int) (byteVal.charAt(j)));
                    }
                       while (opCode.length() < 6) {
                        opCode = "0" + opCode;
                    }
                }
                lines.get(i).objCode = opCode.toUpperCase();
            } 
            else
            {
                // Error handling
                if (optab.optab.containsKey(lines.get(i).OpCode.toUpperCase())) {
                    instCode = optab.optab.get(lines.get(i).OpCode.toUpperCase());
                }
                else 
                {
                    lines.get(i).setError("**** unrecognized operation code");
                }

                if (lines.get(i).operand.startsWith("=")) {
                    for (int j = 0; j < l.size(); j++) {
                        if (l.get(j).name.equalsIgnoreCase(lines.get(i).operand)) {
                            opCode = Integer.toHexString(l.get(j).address);
                        }
                    }
                } else if (lines.get(i).operand.endsWith(",x") || lines.get(i).operand.endsWith(",X")) {

                    if (symtab.containsKey(lines.get(i).operand.substring(0, lines.get(i).operand.length() - 2))) 
                    {
                        opCode = Integer.toHexString(symtab.get(lines.get(i).operand.substring(0, lines.get(i).operand.length() - 2)) + 32768);
                    } else {

                        lines.get(i).setError("**** undefined symbol in operand");
                    }
                } else if (lines.get(i).OpCode.compareToIgnoreCase("Rsub") == 0) {
                    opCode = "0000";
                } else
                {
                    if (symtab.containsKey(lines.get(i).operand)) {
                        opCode = Integer.toHexString(symtab.get(lines.get(i).operand));
                    }
                    
                    else {
                        lines.get(i).setError("**** undefined symbol in operand");
                    }
                }
                if ((lines.get(i).error.equals("**** unrecognized operation code"))) {
                    opCode = "";
                    instCode = "";
                    lines.get(i).objCode = (instCode + opCode);
                    lines.get(i).Flag = false;
                } else if ((lines.get(i).error == ("**** undefined symbol in operand"))) {
                    opCode = "";
                    instCode = "";
                    lines.get(i).objCode = (instCode + opCode);
                    lines.get(i).Flag = false;
                } else {
                    opCode = formatZeros(opCode, 4);
                    lines.get(i).objCode = (instCode + opCode).toUpperCase();
                }
            }
        }
    }

    /**
     * Format zeros String function is try to add "0" to each string to format
     * it as file format
     */
    String formatZeros(String string, int reqDigits) {
        while (string.length() < reqDigits) {
            string = "0" + string;
        }
        return string.toUpperCase();
    }
}

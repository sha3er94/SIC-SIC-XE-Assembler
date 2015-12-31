package system_project;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import javax.print.DocFlavor;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import sun.security.krb5.internal.KDCOptions;

public class Pass_One {

    String progName = "";
    Hashtable<String, Integer> symtab;
    ArrayList<Assembly> lines;
    ArrayList<Literals> LITTAB;
    ArrayList<Literals> table;
    Scanner x;
    private Object engine;

    public void run(ArrayList<String> codeLines) throws ScriptException {
        int pc = 0;
        int old_pc = 0;
        int fl = 0;
        int u = 0;
        String label;
        String inst;
        String operand;
        String name;
        String value;
        int q = 0;
        int length = 0;
        progName = "";
        symtab = new Hashtable<String, Integer>();
        lines = new ArrayList<Assembly>();
        LITTAB = new ArrayList<Literals>();
        Assembly currentLine;
        table = new ArrayList<Literals>();
        Literals l;
        Literals lit;
        for (int i = 0; i < codeLines.size(); i++) {
            label = "";
            inst = "";
            operand = "";
            name = "";
            value = "";
            currentLine = new Assembly();
            String[] splited = (codeLines.get(i).trim()).split(" +");
            if (splited[0].charAt(0) == '.') {
                continue;
            }
            /* if (codeLines.get(i).length() >= 8) 
             {
             label = codeLines.get(i).substring(0,9);   
             }
             if (codeLines.get(i).length() >= 15) 
             {
             inst = codeLines.get(i).substring(9,15);
             }
             else
             {
             inst = codeLines.get(i).substring(9);
             }  
             if (codeLines.get(i).length() >= 35) 
             {
             operand = codeLines.get(i).substring(17,35);
             }
             else
             operand = codeLines.get(i).substring(17); */

            if (splited.length == 1) {
                inst = splited[0];
            } else if (splited.length == 2) {
                inst = splited[0];
                operand = splited[1];

            } else if (splited.length == 3) {
                label = splited[0];
                inst = splited[1];
                operand = splited[2];
            } else if (splited.length == 4) {
                label = splited[0];
                inst = splited[1];
                operand = splited[2];
            }
            if (inst.compareToIgnoreCase("start") == 0) {
                if (operand.matches(".*\\d+.*")) {
                    pc = Integer.parseInt(operand, 16);
                    progName = label;
                    currentLine = new Assembly(label, inst, operand, pc, false);
                } else {
                    currentLine.setError("***Duplicated start");
                }
            } else if (inst.compareToIgnoreCase("equ") == 0) {
                for (int s = 0; s < lines.size(); s++) {
                    currentLine = lines.get(lines.size() - 1);
                    u = currentLine.pc;
                }
                currentLine = new Assembly(label, inst, operand, u, false);
                if (operand.matches(".*\\d+.*")) 
                {
                    ScriptEngineManager mgr = new ScriptEngineManager();
                    ScriptEngine engine = mgr.getEngineByName("JavaScript");
                    String foo = operand;
                    String z = engine.eval(foo).toString();
                    int y = Math.abs(Integer.parseInt(z));
                    if (!label.isEmpty()) {
                        symtab.put(label, y);
                    } else {
                        currentLine.setError("***Label is empty, recheck your SRCFILE!!");
                    }
                } else if (!(operand.matches(".*\\d+.*"))) {

                    if (operand.contains("-") && !operand.startsWith("-")) {
                        int o = 0;
                        int o1 = 0;
                        String op = operand.substring(0, operand.indexOf("-"));
                        String op1 = operand.substring(operand.indexOf("-") + 1);
                        if (symtab.containsKey(op) && symtab.containsKey(op1)) {
                            o = symtab.get(op);
                            o1 = symtab.get(op1);
                            int y = Math.abs(o - o1);
                            symtab.put(label, y);
                        } else {
                            currentLine.setError("*** undefined operand");
                        }
                    } else if (operand.contains("+") && operand.startsWith("-")) {
                        int w = 0;
                        int w1 = 0;
                        String up = operand.substring(1, operand.indexOf("+"));
                        String up1 = operand.substring(operand.indexOf("+") + 1);
                        if (symtab.containsKey(up1) && symtab.containsKey(up)) {
                            w = symtab.get(up);
                            w1 = symtab.get(up1);
                            int f = Math.abs(w1 - w);
                            symtab.put(label, f);
                        } else {
                            currentLine.setError("*** undefined operand");
                        }
                    } else if (!(operand.contains("-") && operand.contains("+"))) {
                        int w = 0;
                        if (symtab.containsKey(operand)) {
                            w = symtab.get(operand);
                            symtab.put(label, w);
                        } else {
                            currentLine.setError("*** undefined operand");
                        }
                    } else {
                        currentLine.setError("SIC assembler doesn't support relative expressions!!");
                    }
                }
                if (!(label.isEmpty())) {
                    if (!operand.matches(".*\\d+.*") && !(operand.contains("+") || operand.contains("-"))) {
                        int p = symtab.get(operand);
                        symtab.put(label, p);
                    }
                } else {
                    currentLine.setError("Label is not found, recheck your SRCFILE!!");
                }
            } else if (inst.compareToIgnoreCase("LTORG") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, false);
                lines.add(currentLine);
                for (int j = 0; j < LITTAB.size(); j++) {
                    inst = LITTAB.get(j).name;
                    label = "*";
                    currentLine = new Assembly(label, inst, operand, pc, true);
                    lines.add(currentLine);
                    if (inst.contains("C'") || inst.contains("c'")) {
                        String val = inst.substring(3, inst.length() - 1);
                        for (int k = 0; k < val.length(); k++) {
                            value += Integer.toHexString((int) (val.charAt(k)));
                        }
                        l = new Literals(inst, value, length, pc);
                        pc = pc + inst.length() - 4;
                    } else if (inst.matches("=.*\\d+.*") && !(inst.contains("X'") || inst.contains("x'"))) {

                        value += inst.substring(1, inst.length());
                        l = new Literals(inst, value, length, pc);
                        pc = pc + 3;
                    } else if (inst.contains("*")) {
                        length = 1;
                        value += Integer.toHexString(pc);
                        l = new Literals(inst, value, length, pc);
                        pc = pc + 3;

                    }
                    else 
                    {
                        value += inst.substring(3, inst.length() - 1);
                        l = new Literals(inst, value, length, pc);
                        pc = pc + ((inst.length() - 4) / 2);

                    }
                    table.add(l);
                }
                LITTAB.clear();
            } else if (inst.compareToIgnoreCase("org") == 0) {
                if (!operand.isEmpty()) {
                    if (operand.matches(".*\\d+.*")) {
                        ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine engine = mgr.getEngineByName("JavaScript");
                        String foo = operand;
                        String z = engine.eval(foo).toString();
                        //  int y = Math.abs(Integer.parseInt(z));
                        fl = 1;
                        old_pc = pc;
                        pc = Math.abs(Integer.parseInt(z));
                        currentLine = new Assembly(label, inst, operand, pc, false);
                    } else {
                        /* fl=1;
                         old_pc=pc;
                         pc = symtab.get(operand);
                         currentLine = new Assembly(label, inst, operand, pc, false);*/

                        if (operand.contains("-") && !(operand.startsWith("-"))) {
                            fl = 1;
                            String y = operand.substring(0, operand.indexOf("-"));
                            String y1 = operand.substring(operand.indexOf("-") + 1);
                            if (symtab.containsKey(y) && symtab.containsKey(y1)) {
                                old_pc = pc;
                                pc = symtab.get(y) - symtab.get(y1);
                                pc = Math.abs(pc);
                                currentLine = new Assembly(label, inst, operand, pc, false);
                            } else {
                                currentLine.setError("*** Used Operands are not previously defined");
                            }
                        } else if (operand.startsWith("-") && operand.contains("+")) {
                            fl = 1;
                            String y = operand.substring(1, operand.indexOf("+"));
                            String y1 = operand.substring(operand.indexOf("+") + 1);
                            if (symtab.containsKey(y) && symtab.containsKey(y1)) {
                                old_pc = pc;
                                pc = symtab.get(y1) - symtab.get(y);
                                pc = Math.abs(pc);
                                currentLine = new Assembly(label, inst, operand, pc, false);
                            } else {
                                currentLine.setError("*** Used Operands are not previously defined!!");
                            }
                        } else if (!(operand.contains("-")) && !(operand.contains("+"))) {
                            fl = 1;
                            old_pc = pc;
                            pc = symtab.get(operand);
                            currentLine = new Assembly(label, inst, operand, pc, false);
                        } else {
                            currentLine = new Assembly(label, inst, operand, pc, false);
                            currentLine.setError("***SIC assembler doesn't support relative expressions!!");
                        }
                    }
                } else if (operand.isEmpty()) {
                    if (fl == 0) {
                        currentLine = new Assembly(label, inst, operand, pc, false);
                        currentLine.setError("No previous ORG is found, Please recheck your SRFILE!!!");
                    } else {
                        currentLine = new Assembly(label, inst, operand, old_pc, false);;
                        pc = old_pc;
                    }
                }
            } else if (inst.compareToIgnoreCase("end") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, false);
                lines.add(currentLine);
                for (int j = 0; j < LITTAB.size(); j++) {
                    inst = LITTAB.get(j).name;
                    label = "*";
                    operand = "";
                    currentLine = new Assembly(label, inst, operand, pc, true);
                    lines.add(currentLine);
                    //  l=new Literals(inst, value, length, pc);
                    // table.add(l);
                    if (inst.contains("C'") || inst.contains("c'")) {
                        String val = inst.substring(3, inst.length() - 1);
                        for (int k = 0; k < val.length(); k++) {
                            value += Integer.toHexString((int) (val.charAt(k)));
                        }
                        l = new Literals(inst, value, length, pc);
                        pc = pc + inst.length() - 4;
                    } else if (inst.matches("=.*\\d+.*") && !(inst.contains("X'") || inst.contains("x'"))) {
                        value += inst.substring(1, inst.length());
                        l = new Literals(inst, value, length, pc);
                        pc = pc + 3;
                    } else if (inst.contains("*")) {
                        length = 1;
                        value += Integer.toHexString(pc);
                        l = new Literals(inst, value, length, pc);
                        pc = pc + 3;
                    } else {
                        value += inst.substring(3, inst.length() - 1);
                        l = new Literals(inst, value, length, pc);
                        pc = pc + ((inst.length() - 4) / 2);
                    }
                    table.add(l);
                }
            } else if (inst.compareToIgnoreCase("resw") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, false);
                if (!label.isEmpty()) {
                    symtab.put(label, pc);
                } else 
                {
                    currentLine.setError("Label is not found , must be existed");
                }
                //check if mn zero l 9
                if ((!operand.isEmpty()) && operand.matches(".*\\d+.*")) {
                     ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine engine = mgr.getEngineByName("JavaScript");
                        String foo = operand;
                        String z = engine.eval(foo).toString();
                        //  int y = Math.abs(Integer.parseInt(z));
                       // fl = 1;
                     //   old_pc = pc;
                        pc =pc+3* Math.abs(Integer.parseInt(z));
                   // pc = pc + 3 * Integer.parseInt(operand);
                } else if (!(operand.isEmpty()) && !(operand.matches(".*\\d+.*"))) {
                    if (operand.startsWith("-") && operand.contains("+")) {
                        int v = 0;
                        int v1 = 0;
                        String e = operand.substring(1, operand.indexOf("+"));
                        String e1 = operand.substring(operand.indexOf("+") + 1);
                        if (symtab.containsKey(e) && symtab.containsKey(e1)) {
                            v = symtab.get(e);
                            v1 = symtab.get(e1);
                            int b = v1 - v;
                            if (b < 0) {
                                b = Math.abs(b);
                                pc = pc + 3 * b;
                                symtab.put(label, pc);
                            } else {
                                pc = pc + 3 * b;
                                symtab.put(label, pc);
                            }
                        } else {
                            currentLine.setError("*** Operand isn't found");
                        }
                    } else if (operand.contains("-") && !(operand.startsWith("-"))) {
                        int v = 0;
                        int v1 = 0;
                        String e = operand.substring(0, operand.indexOf("-"));
                        String e1 = operand.substring(operand.indexOf("-") + 1);
                        if (symtab.containsKey(e1) && symtab.containsKey(e)) {
                            v = symtab.get(e);
                            v1 = symtab.get(e1);
                            int b = v - v1;
                            if (b < 0) {
                                b = Math.abs(b);
                                pc = pc + 3 * b;
                                symtab.put(label, pc);
                            } else {
                                pc = pc + 3 * b;
                                symtab.put(label, pc);
                            }
                        }
                        else if (!(operand.contains("-") && operand.contains("+"))) {
                        int m = 0;
                        if (symtab.containsKey(operand)) {
                            m = symtab.get(operand);
                            m = Math.abs(m);
                            pc = pc + 3 * m;
                            symtab.put(label, m);
                        } else {
                            currentLine.setError("*** Operand isn't found");

                        }
                    }
                    }

                } else {
                    currentLine.setError("Operand is not found or invalid format");
                }
            } else if (inst.compareToIgnoreCase("resb") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, false);
                if (!label.isEmpty()) {
                    symtab.put(label, pc);
                } else {
                    currentLine.setError("Label is not found , must be existed");
                }
                //
                if ((!operand.isEmpty()) && operand.matches(".*\\d+.*")) 
                {
                     ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine engine = mgr.getEngineByName("JavaScript");
                        String foo = operand;
                        String z = engine.eval(foo).toString();
                        //  int y = Math.abs(Integer.parseInt(z));
                       // fl = 1;
                     //   old_pc = pc;
                        pc =pc+Math.abs(Integer.parseInt(z));
                   // pc = pc + Integer.parseInt(operand);
                } else if (!operand.isEmpty() && !operand.matches(".*\\d+.*")) {
                    if (operand.startsWith("-") && operand.contains("+")) {
                        int v = 0;
                        int v1 = 0;
                        String e = operand.substring(1, operand.indexOf("+"));
                        String e1 = operand.substring(operand.indexOf("+") + 1);
                        if (symtab.containsKey(e) && symtab.containsKey(e1)) {
                            v = symtab.get(e);
                            v1 = symtab.get(e1);
                            int b = v1 - v;
                            if (b < 0) {
                                b = Math.abs(b);
                                pc = pc + b;
                                symtab.put(label, pc);
                            } else {
                                pc = pc + b;
                                symtab.put(label, pc);
                            }
                        } else {
                            currentLine.setError("*** Operand isn't found");
                        }
                    } else if (operand.contains("-") && !(operand.startsWith("-"))) {
                        int v = 0;
                        int v1 = 0;
                        String e = operand.substring(0, operand.indexOf("-"));
                        String e1 = operand.substring(operand.indexOf("-") + 1);
                        if (symtab.containsKey(e1) && symtab.containsKey(e)) {
                            v = symtab.get(e);
                            v1 = symtab.get(e1);
                            int b = v - v1;
                            if (b < 0) {
                                b = Math.abs(b);
                                pc = pc + b;
                                symtab.put(label, pc);
                            } else {
                                pc = pc + b;
                                symtab.put(label, pc);
                            }
                        } else {
                            currentLine.setError("*** Operand isn't found");
                        }
                    } else if (!(operand.contains("-") && operand.contains("+"))) {
                        int v = 0;
                        if (symtab.containsKey(operand)) {
                            v = symtab.get(operand);
                            v = Math.abs(v);
                            pc = pc + v;
                            symtab.put(label, v);
                        } else {
                            currentLine.setError("*** Operand isn't found");

                        }
                    }

                } else {
                    currentLine.setError("Operand is not found or invalid format");
                }

            } else if (inst.compareToIgnoreCase("word") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, true);
                symtab.put(label, pc);
                pc = pc + 3;
            } else if (inst.compareToIgnoreCase("byte") == 0) {
                currentLine = new Assembly(label, inst, operand, pc, true);
                symtab.put(label, pc);
                if (operand.contains("C'") || operand.contains("c'")) {
                    pc = pc + operand.length() - 3;
                } else {
                    pc = pc + 1;
                }
            } else if (operand.startsWith("=")) {
                currentLine = new Assembly(label, inst, operand, pc, true);
                if (!label.equalsIgnoreCase("")) {
                    symtab.put(label, pc);
                }
                if (operand.contains("C'") || operand.contains("c'")) {
                    q = 0;
                    pc = pc + 3;
                    length = operand.length() - 4;
                    name = operand;
                    String byteVal = operand.substring(3, operand.length() - 1);
                    for (int j = 0; j < byteVal.length(); j++) {
                        value += Integer.toHexString((int) (byteVal.charAt(j)));
                    }
                    for (int k = 0; k < LITTAB.size(); k++) {
                        if (LITTAB.get(k).name.matches(name)) {
                            q = 1;
                        }
                    }
                    if (q == 0) {
                        lit = new Literals(name, value, length);
                        LITTAB.add(lit);
                    }
                } else if (operand.contains("x'") || operand.contains("X'")) {
                    q = 0;
                    pc = pc + 3;
                    name = operand;
                    length = operand.length() - 4;
                    String byteVal = operand.substring(3, operand.length() - 1);
                    value = byteVal;
                    for (int s = 0; s < LITTAB.size(); s++) {
                        if (LITTAB.get(s).name.matches(name)) {
                            q = 1;
                        }
                    }
                    if (q == 0) {
                        lit = new Literals(name, value, length);
                        LITTAB.add(lit);
                    }
                } else if (operand.matches("=.*\\d+.*") && (!operand.contains("x'") && !operand.contains("X'"))) {
                    q = 0;
                    pc = pc + 3;
                    name = operand;
                    length = operand.length() - 1;
                    value = operand.substring(1, operand.length());
                    for (int s = 0; s < LITTAB.size(); s++) {
                        if (LITTAB.get(s).name.matches(name)) {
                            q = 1;
                        }
                    }
                    if (q == 0) {
                        lit = new Literals(name, value, length);
                        LITTAB.add(lit);
                    }
                } else if (operand.contains("*")) {
                    int g = 0;
                    name = operand;
                    pc = pc + 3;
                    length = 1;
                    String val = operand.substring(1, operand.length());
                    for (int j = 0; j < val.length(); j++) {
                        value += Integer.toHexString((int) (val.charAt(j)));
                    }

                    for (int k = 0; k < LITTAB.size(); k++) {
                        if (LITTAB.get(k).name.matches(name)) {
                            g = 1;
                        }
                        continue;
                    }
                    if (g == 0) {
                        lit = new Literals(name, value, length);
                        LITTAB.add(lit);
                    }
                }   
            }
           
     /* else if(!(inst.isEmpty()) && !((inst.equalsIgnoreCase("resw")) || (inst.equalsIgnoreCase("resb")) || (inst.equalsIgnoreCase("rsub")) || (inst.equalsIgnoreCase("jsub")) || (inst.equalsIgnoreCase("ltorg")) || (inst.equalsIgnoreCase("equ")) || (inst.equalsIgnoreCase("org")) || (inst.equalsIgnoreCase("word")) || (inst.equalsIgnoreCase("byte")) || (inst.equalsIgnoreCase("end")) || (inst.equalsIgnoreCase("start")) || (inst.startsWith("="))))       
            {
                currentLine = new Assembly(label, inst, operand, pc, true);
                 if(!(label.isEmpty())){
                        symtab.put(label, pc);
                }
                if(operand.isEmpty())
                {
                   currentLine.setError("*** Operand isn't found");
                }
                else
                {
                    if(!(operand.matches(".*\\d+.*")))
                    {     
                    if(operand.contains("+") && operand.startsWith("-"))
                    {
                        pc = pc + 3;
                        String up = operand.substring(1, operand.indexOf("+"));
                        String up1 = operand.substring(operand.indexOf("+")+1);
                        if(symtab.containsKey(up1) && symtab.containsKey(up))
                        {
                            System.out.println("ok");
                        }   
                        
                    }
                    else if (operand.contains("-") && !operand.startsWith("-"))
                    {
                         pc = pc + 3;
                        String up = operand.substring(0, operand.indexOf("-"));
                        String up1 = operand.substring(operand.indexOf("-")+1);
                        if(symtab.containsKey(up1) && symtab.containsKey(up))
                        {
                            System.out.println("ok");
                           
                        }
                    }
                 else
                    {
                        pc = pc + 3;
                       /* if (symtab.containsKey(operand))
                        {
                            System.out.println("ok");   
                        }
                        else
                            currentLine.setError("****Operand not found");
                    }
                    } }
                    else if (operand.matches(".*\\d+.*")) 
                    {
                       pc=pc+3;
                    }
                }
            } */
            else if (symtab.containsKey(label)) 
            {
                {
                    currentLine = new Assembly(label, inst, operand, pc, false);
                    currentLine.setError("Error DUPLICATE LABEL!!!!");
                }
            } else {
                if (!label.equalsIgnoreCase("")) {
                    symtab.put(label, pc);
                }
                currentLine = new Assembly(label, inst, operand, pc, true);
                pc = pc + 3;
            }
            if (label.compareToIgnoreCase("*") != 0) {
                lines.add(currentLine);
            }

        }
    }

}

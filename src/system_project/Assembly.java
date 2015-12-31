package system_project;
import java.util.ArrayList;

public class Assembly 
{
     String operand,OpCode,objCode,label,error;
     int pc; 
     boolean Flag;
    public Assembly() 
    {
        
    }
    public Assembly(String inst, String operand, int pc) {
        this.pc = pc;
        this.operand = operand;
        this.OpCode = inst;
    }

    public Assembly(String label, String inst, String operand, int pc, boolean TrnsltedFlg) {
        this.operand = operand;
        this.OpCode = inst;
        this.pc = pc;
        this.label = label;
        this.Flag = TrnsltedFlg;
        this.objCode = "";
        this.error = "";

    }
    public String getError() {
        return error;
    }
    public void setError(String error) 
    {
        this.error= error;
    }
    public String getOpCode() {
        return OpCode;
    }
    public void setOpCode(String OpCode) {
        this.OpCode = OpCode;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getObjCode() {
        return objCode;
    }
    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }
    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) 
    {
        this.operand = operand;
    }
    public int getPc() 
    {
        return pc;
    }
    public void setFlag(boolean Flag)
    {
        this.Flag = Flag;
    }
    public boolean getFlag()
    {
        return Flag;
    }

    
}

package src.PatientenGUI;

public class NichtErlaubException extends Exception {
    public NichtErlaubException(){
        super();
    }
    public NichtErlaubException(String mess){
        super(mess);
   }
}




package src.Fachkonzept;

public class Patient{
    private String name ;
    private String vorname;

    public Patient(String name, String vorname){
        this.name = name;
        this.vorname = vorname;
    }
    public void setName(String name){
            this.name = name;
    }
    public String getName(){
            return this.name;
    }



}
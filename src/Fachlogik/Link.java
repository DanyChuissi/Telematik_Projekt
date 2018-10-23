/**
 *
 * @author Dany Chuissi
 */

package src.Fachlogik;

public class Link {
    private String other;
    private String type;

    public Link(String other, String type){
        this.other = other;
        this.type = type;
    }

    public String getOther(){
        return this.other;
    }
    public String getType(){
        return this.type;
    }
    public void setOther(String other){
        this.other = other;
    }
    public void setType(String type){
        this.type = type;
    }
}

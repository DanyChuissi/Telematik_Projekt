/**
 *
 * @author Dany Chuissi
 */

package src.Fachlogik;

public class Communication {
    private String language;
    private Boolean prefered;

    public Communication(String language){
        this.language = language;
    }

    public Boolean getPrefered() {
        return prefered;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPrefered(Boolean prefered) {
        this.prefered = prefered;
    }
}

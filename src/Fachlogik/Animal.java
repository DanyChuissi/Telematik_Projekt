/**
 *
 * @author Dany Chuissi
 */

package src.Fachlogik;

public class Animal {
    private String specie;
    private String breed;
    private String genderStatus;

    public Animal( String specie){
        this.specie = specie;
    }

    public String getAnimalSpecie(){
        return this.specie;
    }

    public String getAnimalBreed(){
        return this.breed;
    }
    public String getAnimalGenderStatut(){
        return this.genderStatus;
    }
    public void setAnimalSpecie(String specie){
        this.specie = specie;
    }
    public void setAnimalBreed(String breed){
        this.breed = breed;
    }
    public void setAnimalGenderStatus(String genderStatus){
        this.genderStatus = genderStatus;
    }
}

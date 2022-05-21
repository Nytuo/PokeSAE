package pokemon;

public class Types implements interfaces.IType {

    private String nom;
    public Types(String nom) {
        this.nom = nom;
    }

    @Override
    public String getNom() {
        return this.nom;
    }
}

package pokemon;

public class Categorie implements interfaces.ICategorie {
    private String nom;
    private boolean special;
    public Categorie(String nom, boolean special) {
        this.nom = nom;
        this.special = special;
    }
    @Override
    public boolean isSpecial() {
        return special;
    }


    @Override
    public String getNom() {
        return nom;
    }
}

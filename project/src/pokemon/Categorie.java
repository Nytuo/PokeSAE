package pokemon;

public class Categorie implements interfaces.ICategorie {
    private final String nom;
    private final boolean special;
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

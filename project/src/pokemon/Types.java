package pokemon;

/**
 * @author Arnaud BEUX
 **/

/**
 * Une classe qui permet de créer un type pour un Pokemon ou une capacité.
 * Exemple : Plante, Feu, Glace, etc...
 */
public final class Types implements interfaces.IType {

    /**
     * Le nom du type.
     */
    private final String nom;

    /**
     * Constructeur de la classe Types.
     *
     * @param nom String (Le nom du type).
     */

    public Types(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le nom du type.
     *
     * @return String nom. (Le nom du type associé).
     */
    @Override
    public String getNom() {
        return this.nom;
    }
}

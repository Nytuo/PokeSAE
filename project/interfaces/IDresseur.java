/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date IDresseur.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface IDresseur {
  public void enseigne(interfaces.IPokemon pok, interfaces.ICapacite[] caps);

  public void soigneRanch();

  public interfaces.IPokemon choisitCombattant();

  public interfaces.IPokemon choisitCombattantContre(interfaces.IPokemon pok);

  public interfaces.IAttaque choisitAttaque(
      interfaces.IPokemon attaquant, interfaces.IPokemon defenseur);

  public int getNiveau();

  public String getNom();

  public interfaces.IPokemon getPokemon(int i);
}

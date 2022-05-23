/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date ICombat.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface ICombat {
  public void commence();

  public interfaces.IDresseur getDresseur1();

  public interfaces.IDresseur getDresseur2();

  public interfaces.ITour nouveauTour(
      interfaces.IPokemon pok1,
      interfaces.IAttaque atk1,
      interfaces.IPokemon pok2,
      interfaces.IAttaque atk2);

  public void termine();
}

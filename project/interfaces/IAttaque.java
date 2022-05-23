/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date IAttaque.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface IAttaque {
  int calculeDommage(interfaces.IPokemon lanceur, interfaces.IPokemon receveur);

  void utilise();
}

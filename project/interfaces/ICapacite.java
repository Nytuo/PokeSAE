/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date ICapacite.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface ICapacite extends interfaces.IAttaque {
  String getNom();

  double getPrecision();

  int getPuissance();

  int getPP();

  void resetPP();

  interfaces.ICategorie getCategorie();

  interfaces.IType getType();

  int calculeDommage(interfaces.IPokemon lanceur, interfaces.IPokemon receveur);

  void utilise();
}

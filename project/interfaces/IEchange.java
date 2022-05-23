/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date IEchange.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface IEchange extends interfaces.IAttaque {
  public void setPokemon(interfaces.IPokemon pok);

  public interfaces.IPokemon echangeCombattant();
}

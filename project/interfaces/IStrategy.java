/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IStrategy.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IStrategy {
	public interfaces.IPokemon choisitCombattant();
	public interfaces.IPokemon choisitCombattantContre(interfaces.IPokemon pok);
	public interfaces.IAttaque choisitAttaque(interfaces.IPokemon attaquant, interfaces.IPokemon defenseur);
}

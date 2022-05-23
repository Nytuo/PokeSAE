/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IPokedex.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IPokedex {
	public interfaces.IPokemon[] engendreRanch();
	public interfaces.IEspece getInfo(String nomEspece);
	public Double getEfficacite(interfaces.IType attaque, interfaces.IType defense);
	public interfaces.ICapacite getCapacite(String nom);
	public interfaces.ICapacite getCapacite(int n);
}

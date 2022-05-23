/**
 * Université Côte d'Azur
 * IUT Côte d'Azur
 * Département Informatique
 * @date
 * IEspece.java
 */
package interfaces;

/**
 * @author Leo Donati
 *
 */
public interface IEspece {
	public interfaces.IStat getBaseStat();
	public String getNom();
	public int getNiveauDepart();
	public int getBaseExp();
	public interfaces.IStat getGainsStat();
	public interfaces.ICapacite[] getCapSet();
	public IEspece getEvolution(int niveau);  //renvoie null si aucune evolution possible
	public interfaces.IType[] getTypes();
}

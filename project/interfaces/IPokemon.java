/**
 * Université Côte d'Azur IUT Côte d'Azur Département Informatique
 *
 * @date IPokemon.java
 */
package interfaces;

/**
 * @author Leo Donati
 */
public interface IPokemon {
  public interfaces.IStat getStat();

  public double getExperience();

  public int getNiveau();

  public int getId();

  public String getNom();

  public double getPourcentagePV();

  public interfaces.IEspece getEspece();

  public void vaMuterEn(interfaces.IEspece esp);

  public interfaces.ICapacite[] getCapacitesApprises();

  public void apprendCapacites(interfaces.ICapacite[] caps);

  public void remplaceCapacite(int i, interfaces.ICapacite cap) throws Exception;

  public void gagneExperienceDe(IPokemon pok);

  public void subitAttaqueDe(IPokemon pok, interfaces.IAttaque atk);

  public boolean estEvanoui();

  public boolean aChangeNiveau();

  public boolean peutMuter();

  public void soigne();
}

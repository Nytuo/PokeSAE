package game;

import dresseur.AIcomplexe;
import dresseur.Dresseur;
import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Capacite;
import pokemon.Pokemon;
/** La classe Combat gère les combats. Elle implémente l'interface ICombat. */
public class Combat implements ICombat {

  /** Le joueur 1 */
  IDresseur dresseur1;

  /** Le joueur 2 */
  IDresseur dresseur2;

  /** Pokémon du joueur 1 */
  static IPokemon pok1;

  /** Pokémon du joueur 2 */
  static IPokemon pok2;

  /** L'attaque du pokémon du joueur 1 */
  public static IAttaque atk1;

  /** L'attaque du pokémon du joueur 2 */
  public static IAttaque atk2;

  /** Le nombre de tours éffectué lors du combat. */
  public static int nbTour = 0;

  /** Le nom du gagnant */
  String gagnant;

  /**
   * Le constructeur de la classe Combat.
   *
   * @param dresseur1 Dresseur 1 (Joueur 1)
   * @param dresseur2 Dresseur 2 (Joueur 2 (principalement l'IA))
   */
  public Combat(IDresseur dresseur1, IDresseur dresseur2) {
    this.dresseur1 = dresseur1;
    this.dresseur2 = dresseur2;
  }

  /**
   * Méthode permettant de commencer le combat et de créé des tours tant que l'un des deux dresseurs
   * ne peut plus se battre
   */
  @Override
  public void commence() {
	System.out.println("\n\n<--------------------------[ Turn "+(nbTour + 1)+" ]--------------------------->\n");
    System.out.println(
            dresseur1.getNom()
            + " VS. "
            + dresseur2.getNom());
    
    //Lors du 1er tour, chaque dresseur choisit dans son ranch, quel Pokemon va se battre en premier.
    Combat.pok1 = dresseur1.choisitCombattant();
    Combat.pok2 = dresseur2.choisitCombattant();

    System.out.println(Combat.pok1.getNom() + " VS. " + Combat.pok2.getNom() + "\n");

    while ((((Dresseur) dresseur1).pokeEnVie > 0) && (((Dresseur) dresseur2).pokeEnVie > 0)  ) {
    	//&& (((AIcomplexe) dresseur1).pokeEnVie > 0) && (((AIcomplexe) dresseur2).pokeEnVie > 0) // problème de cast
      if (nbTour>0) { System.out.println("\n\n<--------------------------[ Turn "+(nbTour + 1)+" ]--------------------------->\n");}
      
      Combat.atk1 = dresseur1.choisitAttaque(Combat.pok1, Combat.pok2);
      //System.out.println( dresseur1.getNom()+" "+Combat.atk1.calculeDommage(pok1, pok2) );
      
      if (Combat.atk1.getClass() == Echange.class) {
        System.out.println(dresseur1.getNom() + " used swap");
        Combat.pok1 = ((Echange) Combat.atk1).echangeCombattant();
        System.out.println(dresseur1.getNom() + " sent " + Combat.pok1.getNom());
      }
      
      
      Combat.atk2 = dresseur2.choisitAttaque(Combat.pok2, Combat.pok1);
      //System.out.println( dresseur2.getNom() +" "+Combat.atk2.calculeDommage(pok2, pok1) );
      
      if (Combat.atk2.getClass() == Echange.class) {
        System.out.println(dresseur2.getNom() + " used swap");
        Combat.pok2 = ((Echange) Combat.atk2).echangeCombattant();
        System.out.println(dresseur2.getNom() + " sent " + Combat.pok2.getNom());
      }

      nouveauTour(Combat.pok1, Combat.atk1, Combat.pok2, Combat.atk2).commence();
    }

    setGagnant();
    nbTour=0;

    this.termine();
  }

  /** Methode permettant de determiner le gagnant du combat. */
  private void setGagnant() {
	  if (dresseur2.getClass() == Dresseur.class) { // si dresseur2 est humain
		  //System.out.println("OH humain");
		  if (((Dresseur) dresseur2).pokeEnVie <= 0) {
		      gagnant = dresseur1.getNom();
		    } else  {
		      gagnant = dresseur2.getNom();
		    }
	  }
	  else if (dresseur2.getClass() == AIcomplexe.class) {// si dresseur2 est une IA
		  //System.out.println("!!!!pokeEnvie de"+dresseur2.getNom()+":"+((AIcomplexe) dresseur2).pokeEnVie);
		  if (((AIcomplexe) dresseur2).pokeEnVie == 0 || !((AIcomplexe)dresseur2).isSwapPossible()) {
		      gagnant = dresseur1.getNom();
		    } else {
		      gagnant = dresseur2.getNom();
		    }
	  }
	  /*
	   *  if (((Dresseur) dresseur2).pokeEnVie <= 0) {
		      gagnant = dresseur1.getNom();
		    } else  {
		      gagnant = dresseur2.getNom();
		    }
	   */
    
  }

  /**
   * Obtenir le dresseur 1
   *
   * @return IDresseur dresseur1
   */
  @Override
  public IDresseur getDresseur1() {

    return dresseur1;
  }

  /**
   * Obtenir le dresseur 2
   *
   * @return IDresseur dresseur2
   */
  @Override
  public IDresseur getDresseur2() {

    return dresseur2;
  }

  /**
   * Créé un nouveau tour et incrémente le compte tour.
   *
   * @param pok1 Le pokémon du joueur 1
   * @param atk1 L'attaque du pokémon du joueur 1
   * @param pok2 Le pokémon du joueur 2
   * @param atk2 L'attaque du pokémon du joueur 2
   * @return Un nouveau tour
   */
  @Override
  public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
    nbTour++;
    return new Tour(pok1, atk1, pok2, atk2, dresseur1, dresseur2);
  }

  /** Affiche la fin du combat et le gagnant et remet à zéro les pokémons et le dresseur. */
  @Override
  public void termine() {

    Dresseur dresseur1 = (Dresseur) getDresseur1();
    System.out.println("————————————————————————————————————————————————————\n"
    		+gagnant + " won the fight! Congratulations!"
    		+"\n————————————————————————————————————————————————————"
    		);

    resetAllandEvolve(dresseur1);
  }

  /**
   * Méthode permettant de réinitialiser les pokémon et d'évoluer les pokémon.
   *
   * @param dresseur Le dresseur
   */
  public void resetAllandEvolve(Dresseur dresseur) {

    dresseur.echangeRestant = 5;
    dresseur.soigneRanch();

    for (Pokemon pok : dresseur.pokemons) {

      for (Capacite cap : pok.capacities) {
        cap.resetPP();
      }
      if (pok.peutMuter()) {
        System.out.println("What ?!\n" + pok.getNom() + " evolve ?!");
        pok.vaMuterEn(pok.getEvolution(pok.getNiveau()));
        System.out.println("It became a " + pok.getNom());
      }
    }
  }
}

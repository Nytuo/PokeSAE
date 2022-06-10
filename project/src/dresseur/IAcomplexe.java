package dresseur;

import java.util.Random;

import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.IStrategy;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;

public class IAcomplexe extends Dresseur implements IStrategy {
	/**
	   * Constructeur de la classe AIcomplexe qui se charge de remplir le constructeur de Dresseur
	   *
	   * @param name Nom du dresseur (IA)
	   * @param pokemons Liste des pokemons du dresseur (IA)
	   */
	public IAcomplexe(String name, Pokemon[] pokemons) {
		super(name, pokemons);
		
	}
	
	/**
	   * MÃ©thode qui permet de choisir le premier pokemon du dresseur (IA) lors 1er tour de combat
	   *
	   * @return Le premier pokemon de la liste des pokemons du dresseur (IA)
	   */
	  
	  @Override
	  public IPokemon choisitCombattant() {
		 //TODO Choix strategique Ã  faire
		  // A voir
	   return getPokemon(0);
	  }
	  
	  
	  
	  
	  /**
	   * MÃ©thode qui permet de choisir un pokemon au hasard dans la liste des pokemons du dresseur (IA)
	   * (pendant le tour)
	   *
	   * @param pok Pokemon Adverse
	   * @return Un pokemon au hasard dans la liste des pokemons du dresseur (IA) (non Ã©vanouie)
	   */
	  @Override
	  public IPokemon choisitCombattantContre(IPokemon pok) {
	    // TODO Choix strategique Ã  faire
	    Random r = new Random();

	    int numPok = 6;
	    while (numPok == 6) {

	      numPok = r.nextInt((5));

	      if (getPokemon(numPok).estEvanoui()) {
	        numPok = 6;
	      }
	    }

	    return getPokemon(numPok);
	  }
	  
	  
	  
	  
	  /**
	   * MÃ©thode qui permet de choisir une attaque au hasard dans la liste des attaques du pokemon (IA).
	   * La capacitÃ© choisie doit avoir des PPs restants.
	   * Si le nombre de PP de toutes les capacitÃ©s est Ã©gal Ã  0, la capacitÃ© choisie est "Lutte".
	   *
	   * @param attaquant Pokemon du dresseur (IA)
	   * @param defenseur Pokemon Adverse
	   * @return Une attaque au hasard dans la liste des attaques du pokemon (IA), sous conditions
	   */
	  @Override
	  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		  //TODO Choix strategique Ã  faire
	    Capacite[] capList =
	        (Capacite[])
	            attaquant.getCapacitesApprises(); // RÃ©cupÃ¨re toutes les capacitÃ©s de l'attaquant
	    Random r = new Random();
	    int numAttaque = -1;
	    int nbCap = 3 - Boolean.compare(false, echangeRestant > 0);

	    while (numAttaque < 0 || numAttaque > nbCap) {
	      numAttaque = r.nextInt((nbCap));
	      if (capList[numAttaque].getPP() <= 0) {
	        numAttaque = -1;
	        if (capList[0].getPP() <= 0
	            && capList[1].getPP() <= 0
	            && capList[2].getPP() <= 0
	            && capList[3].getPP() <= 0) {
	          return new Pokedex().getCapacite("Lutte");
	        }
	      }
	      System.out.println();
	    }

	    return capList[numAttaque];
	  }
	  
	  public void minmax() {
		  //TODO retourne l'action choisie par TourMax()
	  }
	  
	  public void tourMax(int nb) {
		  // si nb correspond à une fin de partie, alors retourner utilite(nb)
		  //u= (un nombre très petit(negatif)), a=null
		  //pour chaque paire(a',nb) donnée par transition(n)
		  // 	si l'utilité de tourMin(nb') > u alors a=a' , u= utilite() de tourMin
		  //retourne l'utilite u et l'action a // l'action ne sert qu'à la fin de la racine.
	  }
	  
	  public void tourMin(int nb) {
		// si nb correspond à une fin de partie, alors retourner utilite(nb)
		  //u= (un nombre très petit(negatif)), a=null
		  //pour chaque paire(a',nb) donnée par transition(n)
		  // 	si l'utilité de tourMax(nb') < u alors a=a' , u= utilite() de tourMax(nb')
		  //retourne l'utilite u et l'action a
	  }
}

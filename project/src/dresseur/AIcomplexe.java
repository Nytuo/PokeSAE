package dresseur;

import java.util.Random;

import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.IStrategy;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;

public class AIcomplexe extends Dresseur implements IStrategy {
	/**
	   * Constructeur de la classe AIcomplexe qui se charge de remplir le constructeur de Dresseur
	   *
	   * @param name Nom du dresseur (IA)
	   * @param pokemons Liste des pokemons du dresseur (IA)
	   */
	public int degré ;
	public AIcomplexe(String name, Pokemon[] pokemons,int d) {
		super(name, pokemons);
		this.degré = d;
		
	}
	
	public AIcomplexe(String name, Pokemon[] pokemons) {
		super(name, pokemons);
		this.degré = 1;
		
	}
	
	/**
	   * Méthode qui permet de choisir le premier pokemon du dresseur (IA) lors 1er tour de combat
	   *
	   * @return Le premier pokemon de la liste des pokemons du dresseur (IA)
	   */
	  
	  @Override
	  public IPokemon choisitCombattant() {
		 //TODO Choix strategique à faire
		  // A voir
		  int numPok = 0;
		  if (degré ==1) {
			  numPok=choisitCombattantContreIAd1() ;// A completer
		  }
		  // 2 Choisit le pokemon avec le plus gros PV
		  // 3 Choisit le pokémon qui la meilleur ratio PV/Dégat (Fait la moyene pondérée avec les PP des capacités)
		  // 4
		  else {
			  System.out.println("[WARNING]: IA level "+degré+" is not implemented. first Pokémon is selected.");
		  }
	   return getPokemon(numPok);
	  }
	  
	  
	  
	  
	  /**
	   * Méthode qui permet de délégué le choix d'un pokemon dans la liste des pokemons du dresseur (IA)
	   * (pendant le tour)
	   *
	   * @param pok Pokemon Adverse
	   * @return Un pokemon au hasard dans la liste des pokemons du dresseur (IA) (non évanouie)
	   */
	  @Override
	  public IPokemon choisitCombattantContre(IPokemon pok) {
	    // TODO Choix strategique à faire
		  int numPok = -1;
		  if (degré==1) {
			  numPok=choisitCombattantContreIAd1();
		  }
		  // Choisit le pokemon avec le plus gros PV
		  
		  
		  else {
			  System.out.println("[WARNING]: IA level "+degré+" is not implemented. random Pokémon is selected.");
			  
		  }
		  if (numPok ==-1) {
			  numPok=choisitCombattantContreIAd1();
		  }
		  return getPokemon(numPok);
	  }
	  
	  /**
	   * Méthode qui permet de choisir un pokemon au hasard dans la liste des pokemons du dresseur (IA)
	   * (pendant le tour)
	   *
	   * @param pok Pokemon Adverse
	   * @return Un pokemon au hasard dans la liste des pokemons du dresseur (IA) (non évanouie)
	   */
	  public int choisitCombattantContreIAd1() {
		  Random r = new Random();

		    int numPok = 6;
		    while (numPok == 6) {

		      numPok = r.nextInt((5));

		      if (getPokemon(numPok).estEvanoui()) {
		        numPok = 6;
		      }
		    }

		    return numPok;
	  }
	  
	  /**
	   * Méthode qui permet de délégué le choix d'une attaque dans la liste des attaques du pokemon (IA).
	   * La capacité choisie doit avoir des PPs restants.
	   * Si le nombre de PP de toutes les capacités est égal à 0, la capacité choisie est "Lutte".
	   *
	   * @param attaquant Pokemon du dresseur (IA)
	   * @param defenseur Pokemon Adverse
	   * @return Une attaque au hasard dans la liste des attaques du pokemon (IA), sous conditions
	   */
	  @Override
	  public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		  Capacite[] capListAttaquant =
			        (Capacite[])
			            attaquant.getCapacitesApprises(); // Récupère toutes les capacités de l'attaquant
		  
		  int numAttaque=0 ;
		  if ( degré == 1 ) { //Degrés 1 : Aléatoire
			 numAttaque=choixAttaqueIAd1(attaquant,defenseur,capListAttaquant);
		  }
		  else if( degré ==2 ) {
			 
			  numAttaque=choixAttaqueIAd2(attaquant,defenseur,capListAttaquant);
			  //System.out.println("numAttaque"+numAttaque);
		  }
		  else {
			  System.out.println("[WARNING]: IA level "+degré+" is not implemented. Attack n°1 is selected.");
		  }
		  
		  if (numAttaque==-1) {// Si aucune attaque n'est disponible numAttaque = -1
			  return new Pokedex().getCapacite("Lutte");
		  }
		  else {
			  return capListAttaquant[numAttaque];
		  }
		 
	  }
	  
	 

	/**
	   * Méthode qui permet de choisir une attaque au hasard dans la liste des attaques du pokemon (IA).
	   * La capacité choisie doit avoir des PPs restants.
	   * Si le nombre de PP de toutes les capacités est égal à 0, la capacité choisie est "Lutte".
	   *
	   * @param attaquant Pokemon du dresseur (IA)
	   * @param defenseur Pokemon Adverse
	   * @return Une attaque au hasard dans la liste des attaques du pokemon (IA), sous conditions
	   */
	  public int choixAttaqueIAd1(IPokemon attaquant, IPokemon defenseur,Capacite[] capList){
		   //IA de degré 1 -> Aléatoire 
		  
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
		          return -1;
		        }
		      }
		      System.out.println();
		    }
		    return numAttaque;
	  }
	  
	  private int choixAttaqueIAd2(IPokemon attaquant, IPokemon defenseur, Capacite[] capListAttaquant) {
			//retourne la meilleure l'indice de l'attaque qui à le plus de force.
			  
			return getBestDmgCapaciteIndex(capListAttaquant);
		}
	  
	  public void minmax() {
		  //TODO retourne l'action choisie par TourMax()
	  }
	  
	  public void tourMax(int nb) {
		  // si nb correspond � une fin de partie, alors retourner utilite(nb)
		  //u= (un nombre tr�s petit(negatif)), a=null
		  //pour chaque paire(a',nb) donn�e par transition(n)
		  // 	si l'utilit� de tourMin(nb') > u alors a=a' , u= utilite() de tourMin
		  //retourne l'utilite u et l'action a // l'action ne sert qu'� la fin de la racine.
	  }
	  
	  public void tourMin(int nb) {
		// si nb correspond � une fin de partie, alors retourner utilite(nb)
		  //u= (un nombre tr�s petit(negatif)), a=null
		  //pour chaque paire(a',nb) donn�e par transition(n)
		  // 	si l'utilit� de tourMax(nb') < u alors a=a' , u= utilite() de tourMax(nb')
		  //retourne l'utilite u et l'action a
	  }
	  
	  
	  public void getTypeFromPokemonName() {
		  //retourne le type d'un pokemon à partir de son nom.
	  }
	  
	  public void SortByDmg(Pokemon poke) {
		  //retourne la liste des attaques d'un pokemon triées par dégat.
		  int maxCapPuiss = 0;
		  Capacite[] capList ;
		  for (Capacite cap:poke.capacities) {
			  int capPuiss = cap.getPuissance();
			  if (capPuiss > maxCapPuiss) {
				  
			  }
		  }
	  }
	  public int getBestDmgCapaciteIndex(Capacite[] capList) {
		  //retourne le nom de la capacité qui fait le plus de dégat (si ses PP sont >0)
		  int maxCapPuiss = 0;
		  int i = 0; // peut etre source de problème
		  int bestCapIndex = -1;
		  for (Capacite cap:capList) {
			  int capPuiss = cap.getPuissance();
			  if (capPuiss > maxCapPuiss && cap.getPP()>0 ) {
				  maxCapPuiss = capPuiss;
				  bestCapIndex= i;
			  }
			  i++;
		  }
		  return bestCapIndex;
	  }
	  
	  public void duno() {
		  // classe les pokémons du plus tanké au plus agréssif 
		  // grâce à PV/ moyPond(capacités)
	  }
}

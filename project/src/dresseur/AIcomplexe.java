package dresseur;

import java.util.Random;

import game.Echange;
import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IPokemon;
import interfaces.IStrategy;
import interfaces.IType;
import pokedex.Pokedex;
import pokemon.Capacite;
import pokemon.Pokemon;


public class AIcomplexe extends Dresseur implements IStrategy {
    public int pokeEnVie = 6;//Le nombre de pokémons du Dresseur encore en vie
    public int degré ;// Définit le "niveau" de compléxité de l'IA
    Pokedex pokedex = new Pokedex();// Il est générer ici pour que l'execution soit plus rapide.
    int[] pokemonsEchanges = {5,5,5,5,5,5};// nombre d'échange restant par pokémon.
    
    
	
	/**
	   * Constructeur de la classe AIcomplexe qui se charge de remplir le constructeur de Dresseur
	   *
	   * @param name Nom du dresseur (IA)
	   * @param pokemons Liste des pokemons du dresseur (IA)
	   */
		
	
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
		  int numPok = 0;
		  
		  
		  if (degré ==1) {
			  // 1 Random
			  numPok=choisitCombattantContreIAd1() ;
		  }
		  else if (degré==2) {
			  // 2 Choisit le pokemon avec le plus gros PV
			  numPok=getMaxPVPokeIndex();
		  }
		  
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
	   
		  int numPok = -1;
		  
		  showInfo();
		  
		  
		  //1 Random
		  if (degré==1) {
			  numPok=choisitCombattantContreIAd1();
		  }
		  // 2 Choisit le pokemon avec le plus gros PV
		  else if (degré==2) {
			  
			  numPok=getMaxPVPokeIndex();
		  }
		  
		  // 3 Choisit un pokemon qui à un avantage de type contre pok
		  else if (degré==3) {
			  numPok=getBestPokeIndexAgainst(pok);
		  }
		 
		  
		  else {// Pas implémenté
			  System.out.println("[WARNING]: IA level "+degré+" is not implemented. random Pokémon is selected.");
			  
		  }
		  if (numPok ==-1) {
			  numPok=choisitCombattantContreIAd1();
		  }
		  return getPokemon(numPok);
	  }
	  
	  
	  //TODO le pb peut peut être venir de TOUR. avec nb Poke vivant par ex
	  
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
		  if ( degré == 1 ) { //Degré 1 : Aléatoire
			 numAttaque=choixAttaqueIAd1(attaquant,defenseur,capListAttaquant);
			 System.out.println("numAttaque:"+numAttaque);
		  }
		  else if( degré ==2 ) {//Degré 2: 
			 
			  numAttaque=choixAttaqueIAd2(attaquant,defenseur,capListAttaquant);
			  //System.out.println("numAttaque"+numAttaque);
		  }
		  else if (degré==3) { //Degré 3: 
			  if (isOpposedType(attaquant,defenseur)) {
				  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!OPPOSED TYPE");
				  return new Echange(this, attaquant, defenseur);
					  
			 }
			  else {
				  numAttaque=getBestAttackAgainst(attaquant,defenseur);
			  }
		  }
		  
		  else {
			  System.out.println("[WARNING]: IA level "+degré+" is not implemented. Attack n°1 is selected.");
		  }
		  System.out.println(this.name+" swap is possible:"+isSwapPossible()+" "+attaquant.getNom()+": totalPP: "+totalPP(attaquant) );
		  if (numAttaque==-1) {// Si aucune attaque n'est disponible numAttaque = -1
			  //return new Pokedex().getCapacite("Lutte");
			  if (isSwapPossible()) {
				  int switchIndex = getBestSwitchablePokeIndex(attaquant,defenseur);
				  System.out.println("changement:"+switchIndex);
				  pokemonsEchanges[switchIndex]--; 
				  return new Echange(this, attaquant, pokemons[switchIndex]); // peut être source de problème
			  }
			  else {
				  System.out.println("SWAP IMPOSSIBLE");
				  numAttaque=0;
			  }
		  }
		  return capListAttaquant[numAttaque];
		 
		
		 
	  }
	  
	  public int[] getSwapableList() {
		  //retourne l'index des pokémons swapable
	  }
	  
	  public boolean isSwapPossible() {
		  //retourne vrai si il existe au moins un pokémon en vie qui peut etre appellé pour un échange.
		  for (int i=0; i<pokemons.length;i++) {
			  if (!pokemons[i].estEvanoui() &&  pokemonsEchanges[i]>0) {
				  return true;
			  }
		  }
		  return false;
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
		    int nbTests=0;
		    while (numPok == 6) {
		    	nbTests++;
		      numPok = r.nextInt((6));
		     System.out.println();
		      if (getPokemon(numPok).estEvanoui())  {
		        numPok = 6;
		      }
		    }

		    return numPok;
	  }
	  
	  public int totalPP(IPokemon pok) {
		  // Retourne le total des PP d'un pokémon 
		  int total=0;
		  for (ICapacite cap: pok.getCapacitesApprises()) {
			  total+=cap.getPP();
		  }
		  return total;
	  }
	  
	  
	  private void afficherPokeKO() {
		  int i=0;
		  	String spaceCarac=" ";
		  	int maxPokeNameLength=0;
	        for (IPokemon poke : this.pokemons) {
	            int pokeNameLength = poke.getNom().length();
	            if (pokeNameLength > maxPokeNameLength) {
	                maxPokeNameLength = pokeNameLength;
	            }
	        }
	        
	       
	        System.out.println(" Index  | NOM       | KO | Swap | totalPP");
	        for (IPokemon poke : this.pokemons) {
	            String down = "      ";
	            if (poke.estEvanoui()) {
	                down = " [KO] ";
	            }
	            System.out.println("    " + i + " - " + poke.getNom()
	                    + spaceCarac.repeat(maxPokeNameLength - poke.getNom().length())
	                    + down
	                    +pokemonsEchanges[i]
	                    +" "+
	                    +totalPP(pokemons[i]));
	            	
	            i++;
	        }
	    }
	  
	  public int randomCombatantManual() {
		  Random r = new Random();

		    int numPok = 6;
		    int nbTests=0;
		    while (numPok == 6 && nbTests<12) {
		    	nbTests++;
		      numPok = r.nextInt((6));
		      System.out.println("INFINIT:"+getPokemon(numPok).estEvanoui()+" "+numPok+"  "+pokemonsEchanges[numPok]);
		      if (getPokemon(numPok).estEvanoui() || pokemonsEchanges[numPok]==0) {
		        numPok = 6;
		      }
		    }

		    return numPok;
	  }
	  
	  
	  private void showInfo() {
		  //Montre les infos d'un dresseur.
		  System.out.println(this.getNom()+"'s info:");
		  System.out.println("pokeEnVie:"+pokeEnVie);
		  
		  
		  afficherPokeKO();
		 
		  
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
		    int nbCap = 4;//3 - Boolean.compare(false, echangeRestant > 0);
		    	
		    if (totalPP(attaquant)==0){
		    	System.out.println("BESOIN DE SWAP");
		    	return -1;//Le code pour le changement de pokemon
		    }
		    while (numAttaque < 0 || numAttaque > nbCap) {
		      numAttaque = r.nextInt((nbCap));
		      if (capList[numAttaque].getPP() <= 0) {
		        numAttaque = -1;
		       
		      }
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
	  
	  public int getMaxPVPokeIndex() {
		  int maxPV=0;
		  int maxPVindex=-1;
		  int i=0;
		  for (Pokemon poke:pokemons) {
			  int pokePV=poke.getStat().getPV();
			  if (pokePV>maxPV && !poke.estEvanoui()) {
				  maxPV=pokePV;
				  maxPVindex=i;
			  }
			  i++;
		  }
		  return maxPVindex;
	  }
	  
	  public float getTypeScore(IPokemon atta,IPokemon def) {
		  //retourne le score d'un pokémon 
		  float total=0;
		  for (IType typePok1:atta.getEspece().getTypes()) {
				 for (IType typePok2:atta.getEspece().getTypes()) {
					 if (typePok1.getNom()!="" && typePok2.getNom()!="") {
						 total+=pokedex.getEfficacite(typePok1, typePok2);
					 }
				 }
				
		  }
		  return total;//retourner si le total est avantageux.
	  }
	  
	  public int getBestAttackAgainst(IPokemon atta, IPokemon def) {
		 int[] maxIndex= {0,0};
		  double maxEfficacite=0;
		  IType[] typesDef= def.getEspece().getTypes();
		  ICapacite[] caps = atta.getCapacitesApprises();
		  for (int j=0; j<typesDef.length;j++ ) {
			  for (int i=0; i<caps.length;i++) {
				  IType capType = caps[i].getType();
				  if (caps[i].getPP()>0) {
					  if (capType.getNom()!="" && typesDef[j].getNom()!="") {
						  double efficacite =pokedex.getEfficacite(caps[i].getType(), typesDef[j] );
						  if(efficacite>maxEfficacite) {
							  maxEfficacite=efficacite;
							  maxIndex[j]=i;
					  }
					 
					  }
					 
				  }
				
			  }
		  }
		  
		  //Prend la plus précise 
		  if ( caps[maxIndex[0]].getPrecision()>caps[maxIndex[1]].getPrecision() ) {
			  return maxIndex[0];
		  }
		  return maxIndex[1];
		
		
	  }
	  
	  public int getBestAttack(IPokemon atta, IPokemon def) {
		  int maxDmg=0;
		  int i=0;
		  int maxIndex=0;
		  for (ICapacite cap: atta.getCapacitesApprises()) {
			  int dmg=cap.calculeDommage(atta, def);
			  if (dmg>maxDmg && cap.getPP()>0) {
				  maxDmg=dmg;
				  maxIndex=i;
			  }
			  i++;
		  }
		  return maxIndex;
		 
	  }
	  
	  
	  public int getBestPokeIndexAgainst(IPokemon pok) {
		  float maxScore = 0;
		  int bestPokeIndex=-2; // peut être source de problème
		  for (int i=0; i<pokemons.length;i++) {// Pour chaque poke de mon ranch
			  float score = getTypeScore(pokemons[i],pok);
			  if (score>maxScore && pokemons[i].getPourcentagePV()>0)
				 maxScore=score;
			  	bestPokeIndex=i;
			  // je compte combien il en a, s'il en a plus que le max rencontré, je garde son nom et son index
		  }
		  return bestPokeIndex;
	  }
	 
	  public int getBestSwitchablePokeIndex(IPokemon currentPok, IPokemon pok) {
		  //Retourne le meilleur pokémon possible (sauf celui actuel) contre un opposant. 
		  float maxScore = 0;
		  int bestPokeIndex=-2; // peut être source de problème
		  for (int i=0; i<pokemons.length;i++) {// Pour chaque poke de mon ranch
			  float score = getTypeScore(pokemons[i],pok);
			  if (score>maxScore 
					  && !pokemons[i].estEvanoui() // Regarde si il n'est pas évanouhi
					  && !(pokemons[i].getNom() ==currentPok.getNom())  // Passe le pokémon actuel
					  && pokemonsEchanges[i]>0 // Si il reste des échanges au poké
					  && totalPP(pok)>0) {// Si il lui reste des PP
				  
				  	maxScore=score;
				  	bestPokeIndex=i;
			  }
				
			  // je compte combien il en a, s'il en a plus que le max rencontré, je garde son nom et son index
		  }
		  if (bestPokeIndex==-2){//Aucun ne correspond
			  return choisitCombattantContreIAd1();// Retourne un random
		  }
		  return bestPokeIndex;
	  }
	  public void duno() {
		  // classe les pokémons du plus tanké au plus agréssif 
		  // grâce à PV/ moyPond(capacités)
	  }
	  
	 public boolean isOpposedType(IPokemon atta,IPokemon def) {
		 // retourne vrai si deux pokémons sont de type contraires.
		 for (IType typePok1:atta.getEspece().getTypes()) {
			 for (IType typePok2:atta.getEspece().getTypes()) {
				 if (typePok1.getNom()!="" && typePok2.getNom()!="" ) {
					 double value =pokedex.getEfficacite(typePok1, typePok2);
					//System.out.println(typePok1.getNom()+" et "+typePok2.getNom()+"->valeur :"+value);
					 if (pokedex.getEfficacite(typePok1, typePok2) == 0.0) {
						 //System.out.println("TRUEEEEEEEEEEEEEEEE");
						 return true;
					 }
				 }
				 
			 }
		 }
		 return false;
	 }
	  public Pokemon[] getRanchCopy() {
	        Pokemon[] pokecopy = new Pokemon[6];
	        System.arraycopy(this.pokemons, 0, pokecopy, 0, this.pokemons.length);
	        return pokecopy;
	    }

	 public int getPokeEnVie() {
		 return this.pokeEnVie;
	 }
}

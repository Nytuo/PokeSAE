package game;

import java.util.Random;

import dresseur.AIcomplexe;
import dresseur.AIsimple;
import dresseur.Dresseur;
import interfaces.IAttaque;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;
import pokemon.Capacite;

/** La classe Tour gère les tours du combat. Elle implémente l'interface ITour. */
public class Tour implements ITour {

  /** Pokémon du joueur 1 */
  IPokemon pok1;

  /** Attaque du pokémon du joueur 1 */
  IAttaque atk1;

  /** Pokémon du joueur 2 */
  IPokemon pok2;

  /** Attaque du pokémon du joueur 2 */
  IAttaque atk2;

  /** Dresseur 1 (Joueur 1) */
  IDresseur dresseur1;

  /** Dresseur 2 (Joueur 2 (principalement l'IA)) */
  IDresseur dresseur2;

  /** nombre de pokémon en vie du joueur 1 (deprecated) */
  int pokes1EnVie;

  /** nombre de pokémon en vie pour le joueur 2 (deprecated) */
  int pokes2EnVie;

  /**
   * Constructeur de la classe Tour
   *
   * @param pok1 Pokémon du joueur 1
   * @param atk1 Attaque du pokémon du joueur 1
   * @param pok2 Pokémon du joueur 2
   * @param atk2 Attaque du pokémon du joueur 2
   * @param d1 Dresseur 1 (Joueur 1)
   * @param d2 Dresseur 2 (Joueur 2 (principalement l'IA))
   */
  public Tour(
      IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2, IDresseur d1, IDresseur d2) {

    this.pok1 = pok1;
    this.atk1 = atk1;
    this.pok2 = pok2;
    this.atk2 = atk2;
    this.dresseur1 = d1;
    this.dresseur2 = d2;
  }

  /**
   * Méthode permettant de commencer le tour. Le premier pokémon à attaqué est celui dont la vitesse
   * est suéprieur à celle de l'autre
   */
  @Override
  public void commence() {
	  System.out.println("poke en vie "+dresseur1.getNom()+" :"+((AIcomplexe) dresseur1).getPokeEnVie());
	  System.out.println("poke en vie "+dresseur2.getNom()+" :"+((AIcomplexe) dresseur2).getPokeEnVie());
	  
    if (pok1.getStat().getVitesse() < pok2.getStat().getVitesse()) {
    
      gereAttaque(dresseur2, pok2, atk2, dresseur1, pok1, atk1);
    }
    else if (pok1.getStat().getVitesse() > pok2.getStat().getVitesse()) {
    
      gereAttaque(dresseur1, pok1, atk1, dresseur2, pok2, atk2);
    }
    else {
    	Random r = new Random();

	    int randDresseur = r.nextInt((2));
	    
	    if(randDresseur == 0) {
	    	
          gereAttaque(dresseur2, pok2, atk2, dresseur1, pok1, atk1);
        }
	    else {
	    
          gereAttaque(dresseur1, pok1, atk1, dresseur2, pok2, atk2);
        }
	    
	    
	      
     
    }
  }

  private void gereAttaque(IDresseur dressAttack, IPokemon pokAttack, IAttaque atkPokAttack, IDresseur dressDef, IPokemon pokDef, IAttaque atkPokDef) {

      if (dressAttack == dresseur1){
          System.out.println("◀ gagnant "+ dressAttack.getNom() + "▶ -> " + pokAttack.getNom());
          attaque(dressAttack, pokAttack, atkPokAttack, pokDef, atkPokDef);
          if (pokAttack == Combat.pok1) {
              System.out.println("voila!");
              System.out.println("◀"+ dressDef.getNom() + "▶ -> " + pokDef.getNom());
              attaque(dressAttack, pokDef, atkPokDef, pokAttack, atkPokAttack);
          }
      }else{
          System.out.println("◀ gagnant "+ dressAttack.getNom() + "▶ -> " + pokAttack.getNom());
          attaque(dressAttack, pokAttack, atkPokAttack, pokDef, atkPokDef);
          if (pokAttack == Combat.pok2) {
              System.out.println("voila!");
              System.out.println("◀" + dressDef.getNom() + "▶ -> " + pokDef.getNom());
              attaque(dressAttack, pokDef, atkPokDef, pokAttack, atkPokAttack);
          }
      }
  }

  
  /**
   * Méthode qui permet d'attaquer un pokémon ennemi. On gère ici la défaite d'un Pokémon sur ce
   * tour et l'attaque utilisée contre le pokémon ennemi
   *
   * @param dresseur1 Le dresseur qui attaque
   * @param pok1 Le pokémon attaquant
   * @param atk1 L'attaque utilisée
   * @param pok2 Le pokémon attaqué
   * @param atk2 L'attaque utilisée
   */
  public void attaque(
      IDresseur dresseur1, IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {

    atk1.utilise();

    
  
  //Gestion des echanges
    if (atk1.getClass() == Echange.class) {// Si il y a échange
        pok1.subitAttaqueDe(pok2, atk2);
       
        System.out.println(
      		  "    "
      		  +pok2.getNom()
      		  +" : "
      		  +((Capacite) atk2).getNom()
      		  +" --> "
      		  +pok1.getNom()
      		  +" "+String.format("%.2f",pok1.getPourcentagePV())+"% PV\n"
      		  );
        

      
      } 
      else  if (atk2.getClass() != Echange.class){// Si il y a échange

        pok1.subitAttaqueDe(pok2, atk2);
        System.out.println(
      		  "    "
      		  +pok2.getNom()
      		  +" : "
      		  +((Capacite) atk2).getNom()
      		  +" --> "
      		  +atk2.calculeDommage(pok2, pok1)
      		  +" DMG to "
      		  +pok1.getNom()
      		  +" "+String.format("%.2f",pok1.getPourcentagePV())+"% PV\n"
      		  );
      		
    

      }
    //Gestion des KO
    if (this.pok1.estEvanoui()) {
    	this.pok2.gagneExperienceDe(this.pok1);
    	System.out.println(this.pok2.getNom() 
    			+" slain " 
    			+ this.pok1.getNom() 
    			+"  +"+ String.format("%.2f",this.pok2.getExperience() ) 
    			+ " xp\n");
      
      
    
	    	((Dresseur) this.dresseur1).pokeEnVie--;
	      if (((Dresseur) this.dresseur1).pokeEnVie > 0) {
	
	        Combat.pok1 = new Echange((Dresseur) this.dresseur1, this.pok1, this.pok2).echangeCombattant();
	
	        System.out.println(this.dresseur1.getNom() + " sent " + Combat.pok1.getNom());
	      }
	      
    }
    else if(this.pok2.estEvanoui()) {
	    	this.pok1.gagneExperienceDe(this.pok2);
		      System.out.println(this.pok1.getNom() 
		    		  +" slain " 
		    		  +this.pok2.getNom()
		    		  +"  +"
		    		  +String.format("%.2f",this.pok1.getExperience()) 
		    		  + " xp\n");
	
		
	      	((Dresseur) this.dresseur2).pokeEnVie--;
	      if (((Dresseur) this.dresseur2).pokeEnVie > 0) {
	        Combat.pok2 = new Echange((Dresseur) this.dresseur2, this.pok2, this.pok1).echangeCombattant();
	        System.out.println(this.dresseur2.getNom() + " sent " + Combat.pok2.getNom()+"\n");
	      }
	      
    }
   
    
    
  }
  
 
  /**
   * Méthode qui permet de savoir si le dresseur passe son tour
   *
   * @param dresseur1 le dresseur qui passe son tour
   */
  void passTour(Dresseur dresseur1) {
    System.out.println("Pass his turn");
  }
  
 
  
}

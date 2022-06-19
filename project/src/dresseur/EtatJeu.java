package dresseur;

import java.util.ArrayList;

import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import pokemon.Capacite;

public class EtatJeu {
	IDresseur dresseur1;//  IA
	IDresseur dresseur2;//  Adversaire
	IPokemon currentPok1;// Pokémon actuel de l'IA
	IPokemon currentPok2;// Pokémon actuel de L'adversaire
	
	
	
	public EtatJeu(IDresseur dresseur1, IDresseur dresseur2) {
		this.dresseur1 = dresseur1;
		this.dresseur2 = dresseur2;
	}
	public EtatJeu() {
		this.dresseur1 = null;
		this.dresseur2 = null;
	}
	
	public void miseAJourEtatJeu(IDresseur dresseur1, IDresseur dresseur2,IPokemon currentPok1,IPokemon currentPok2) {
		this.dresseur1 = dresseur1;
		this.dresseur2 = dresseur2;
		this.currentPok1= currentPok1;
		this.currentPok2= currentPok2;
	}
	
	public boolean allPokeDown(IDresseur dresseur) {
		int nbEvanoui = 0;
		for (IPokemon poke: dresseur.getRanchCopy()) {
			if (poke.estEvanoui()) {
				nbEvanoui++;
			}
		}
		return nbEvanoui==6;
	}
	
	public ICapacite[] getCoupPossible(IDresseur dresseur,IPokemon pok) { // on ne compte pas les swap
		ArrayList<ICapacite> caps = new ArrayList<ICapacite>();
		for (ICapacite cap: pok.getCapacitesApprises() ) {
			if (cap.getPP()>0) {
				caps.add(cap);
			}
		}
		return (ICapacite[]) caps.toArray(); // Pas sur
	}
	
	
	/**Permet de savoit si une partie est terminale
	 * 
	 */
	public boolean isTerminal() {
		for (IPokemon poke: dresseur1.getRanchCopy()) {
			if (!poke.estEvanoui()) {
				return false;
			}
		}
		for (IPokemon poke: dresseur2.getRanchCopy()) {
			if (!poke.estEvanoui()) {
				return false;
			}
		}
		return false;
	}
	
	
	public ArrayList<Object> P(EtatJeu X) { // probabilité de victoire pour l'IA dans l'état X
		if (X.isTerminal()) {
			if (allPokeDown(dresseur1)) {
				ArrayList<Object> objList = new ArrayList<Object>();
				objList.add( 1);
				objList.add(null);
				return objList;
			}
			else if (allPokeDown(X.dresseur2)) {
				ArrayList<Object> objList = new ArrayList<Object>();
				objList.add( 0);
				objList.add(null);
				return objList;
			}
		}
		else {
			ICapacite[] C1=getCoupPossible(X.dresseur1,X.currentPok1);// pas sur
			ICapacite[] C2=getCoupPossible(X.dresseur2,X.currentPok2);// pas sur
			float max=0;
			ICapacite cmax=(ICapacite) C1[0];
			for (int i=0; i<C1.length;i++) { // pas sur dresseur2
				float min=1;
				for (int j=0; j<C2.length;j++) {
					//val=somme_i P_i P(X_i)[0] // A FAIRE
					min = Math.min(min,val);
				}
				if (min>max){
					max=min;
					cmax=C1[1];
				}
			}
			ArrayList<Object> objList = new ArrayList<Object>();
			objList.add(max);
			objList.add(cmax);
			return objList;
		}
	}
	

	
	
	public ArrayList<Object> H(EtatJeu X,int n) {
		float max=0;
		ICapacite[] C1=getCoupPossible(X.dresseur1,X.currentPok1);// pas sur
		ICapacite cmax=(ICapacite) C1[0];
		if (X.isTerminal() || n==0) { // PAS SUR DE X.isTerminal
			int sPVdresseur1=sommePV(X.dresseur1);
			float val = sPVdresseur1/(sPVdresseur1+sommePV(X.dresseur2));
		}
		else {
			ICapacite[] C2=getCoupPossible(X.dresseur2,X.currentPok2);// pas sur
			
			for (int i=0; i<C1.length;i++) {
				float min=1;
				for (int j=0; j<C2.length;j++) {
					
					//float val = Float.sum(P, j) //val=somme_i P_i P(X_i)[0]; // A FAIRE
					min = Math.min(min,val);
				}
				if (min>max) {
					max=min;
					cmax=C1[1];
				}
			}
		}
		ArrayList<Object> objList = new ArrayList<Object>();
		objList.add(max);
		objList.add(cmax);
		return objList;
	}
	
	public int sommePV(IDresseur dresseur) {
		int total=0;
		for (int i=0; i<6;i++) {
			total+=dresseur1.getPokemon(i).getStat().getPV();
		}
		return total;
	}
	



	
}

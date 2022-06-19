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
	
	public int nbCoupPossible(IDresseur dresseur) {
		//Switch
		//Attaque
		return 5;
		
	}
	
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
	
	
	public void P(EtatJeu X) { // probabilité de victoire pour l'IA dans l'état X
		if (X.isTerminal()) {
			if (allPokeDown(dresseur1)) {
				return (1,null);
			}
			else if (allPokeDown(dresseur2)) {
				return(0,null);
			}
		}
		else {
			ICapacite[] C1=getCoupPossible(dresseur1,currentPok1);// pas sur
			ICapacite[] C2=getCoupPossible(dresseur2,currentPok2);// pas sur
			float max=0;
			ICapacite cmax=(ICapacite) C1[0];
			for (int i=0; i<C1.length;i++) { // pas sur dresseur2
				float min=1;
				for (int j=0; j<C2.length;j++) {
					//val=somme_i P_i P(X_i)[0] // A FAIRE
					min = min(min,val);
				}
				if (min>max){
					max=min;
					cmax=c[1];
				}
			}
			return (max,cmax);
		}
	}
	

	
	
	public void H(EtatJeu X,int n) {
		if (X.isTerminal() || n==0) { // PAS SUR DE X.isTerminal
			int sPVdresseur1=sommePV(dresseur1);
			float val = sPVdresseur1/(sPVdresseur1+sommePV(dresseur2));
		}
		else {
			ICapacite[] C1=getCoupPossible(dresseur1,currentPok1);// pas sur
			ICapacite[] C2=getCoupPossible(dresseur2,currentPok2);// pas sur
			float max=0;
			ICapacite cmax=(ICapacite) C1[0];
			for (int i=0; i<C1.length;i++) {
				float min=1;
				for (int j=0; j<C2.length;j++) {
					//val=somme_i P_i P(X_i)[0]; // A FAIRE
					min = min(min,val);
				}
				if (min>max) {
					max=min;
					cmax=c_1;
				}
			}
		}
		return (max,cmax);
	}
	
	public int sommePV(IDresseur dresseur) {
		int total=0;
		for (int i=0; i<6;i++) {
			total+=dresseur1.getPokemon(i).getStat().getPV();
		}
		return total;
	}
	



	
}

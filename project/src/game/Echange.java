package game;

import interfaces.IDresseur;
import interfaces.IEchange;
import interfaces.IPokemon;

public class Echange implements IEchange {
	
	IDresseur dresseur;

	IPokemon oldPok;
	IPokemon newPok;
	IPokemon defenseur;

	public Echange(IDresseur dresseur, IPokemon oldPok, IPokemon defenseur) {

		this.dresseur = dresseur;
		this.oldPok = oldPok;
		this.defenseur = defenseur;
	}

	@Override
	public int calculeDommage(IPokemon lanceur, IPokemon receveur) {

		return 0;
	}

	@Override
	public void utilise() {
	}

	@Override
	public void setPokemon(IPokemon pok) {

		this.newPok = pok;
	}

	@Override
	public IPokemon echangeCombattant() {

		this.setPokemon(this.dresseur.choisitCombattantContre(defenseur));
		
		return this.newPok;
	}

}

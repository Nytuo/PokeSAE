package game;

import interfaces.IAttaque;
import interfaces.ICombat;
import interfaces.IDresseur;
import interfaces.IPokemon;
import interfaces.ITour;

public class Combat implements ICombat{
	
	IDresseur dresseur1;
	IDresseur dresseur2;
	ITour tour;

	public Combat(IDresseur dresseur1, IDresseur dresseur2) {
		this.dresseur1 = dresseur1;
		this.dresseur2 = dresseur2;
	}

	@Override
	public void commence() {
		
		IPokemon pok1 = dresseur1.choisitCombattant();
		IPokemon pok2 = dresseur2.choisitCombattant();
		
		IAttaque atk1 = dresseur1.choisitAttaque(pok1, pok2);
		IAttaque atk2 = dresseur2.choisitAttaque(pok1, pok2);
		
		tour = nouveauTour(pok1, atk1, pok2, atk2);
		
	}

	@Override
	public IDresseur getDresseur1() {

		return dresseur1;
	}

	@Override
	public IDresseur getDresseur2() {

		return dresseur2;
	}

	@Override
	public ITour nouveauTour(IPokemon pok1, IAttaque atk1, IPokemon pok2, IAttaque atk2) {
		
		tour.commence();
		
		if (atk1.getClass() == Echange.class) {
			pok1 = ((Echange) atk1).echangeCombattant();
		}
		else {
			pok1.subitAttaqueDe(pok2, atk2);
		}
		
		
		if (atk2.getClass() == Echange.class) {
			pok2 = ((Echange) atk2).echangeCombattant();
		}
		else {
			pok2.subitAttaqueDe(pok1, atk1);
		}
		
		
		return this.nouveauTour(pok1, atk1, pok2, atk2);
	}

	@Override
	public void termine() {
		// TODO Auto-generated method stub
		
	}}

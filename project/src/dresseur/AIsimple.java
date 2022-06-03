package dresseur;

import interfaces.IAttaque;
import interfaces.IPokemon;
import interfaces.IStrategy;
import java.util.ArrayList;
import java.util.Random;
import pokemon.Capacite;
import pokemon.Pokemon;

/**
 * Une stratégie est utilisée par les dresseurs non humains (IA) pour prendre
 * les décisions Un DresseurIA possède une référence sur une IStrategy à qui il
 * délègue la prise de décision Un dresseur humain n'utilise pas IStrategy
 *
 * <p>
 * Chaque méthode de IStrategy correspond à la méthode homonyme de IDresseur
 */
public class AIsimple extends Dresseur implements IStrategy {
	public AIsimple(String name, int level, ArrayList<Pokemon> pokemons) {
		super(name, level, pokemons);
	}

	@Override
	public IPokemon choisitCombattant() {
		// TODO Auto-generated method stub
		return pokemons.get(0);
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {
		// TODO Auto-generated method stub
		Random r = new Random();
		int numPok = r.nextInt((5));

		return pokemons.get(numPok);
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
		Capacite[] capList = (Capacite[]) attaquant.getCapacitesApprises(); // Récupère toutes les capacités de
																			// l'attaquant
		Random r = new Random();
		int numAttaque = r.nextInt((3)); // Choisit un nombre aléatoire entre 0 et 3
		return capList[numAttaque];
	}
}
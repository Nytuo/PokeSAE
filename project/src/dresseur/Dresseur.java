package dresseur;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import pokemon.Capacite;
import pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Scanner;

import game.Echange;

public class Dresseur implements IDresseur {
	public String name;
	public int level = 0;
	public ArrayList<Pokemon> pokemons;

	public Dresseur(String name, int level, ArrayList<Pokemon> pokemons) {

		this.name = name;
		this.pokemons = pokemons;

		for (Pokemon pok : pokemons) {
			this.level += pok.getNiveau();
		}
	}

	@Override
	public void enseigne(IPokemon pok, ICapacite[] caps) {

		pok.apprendCapacites(caps);

	}

	@Override
	public void soigneRanch() {
		for (Pokemon pok : pokemons) {
			pok.soigne();
		}
	}

	@Override
	public IPokemon choisitCombattant() {

		return getPokemon(0);
	}

	@Override
	public IPokemon choisitCombattantContre(IPokemon pok) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Which pokemon will you send against \n" + pok + "\n" + this.pokemons);
		String numPok = scanner.nextLine();
		System.out.println();

		scanner.close();

		return pokemons.get(Integer.valueOf(numPok));
	}

	@Override
	public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {

		Capacite[] capList = (Capacite[]) attaquant.getCapacitesApprises(); // R�cup�re toutes les capacit�s de
																			// l'attaquant

		Scanner scanner = new Scanner(System.in);

		System.out.print("Choose the move to use\n" + attaquant.getCapacitesApprises() + "4 : change pokemon");
		String numAttaque = scanner.nextLine();
		System.out.println();

		scanner.close();

		if (Integer.valueOf(numAttaque) == 4) {

			return new Echange(this, attaquant, defenseur);
		}
		return capList[Integer.valueOf(numAttaque)];
	}

	@Override
	public int getNiveau() {

		return this.level;
	}

	@Override
	public String getNom() {

		return this.name;
	}

	@Override
	public IPokemon getPokemon(int i) {

		return pokemons.get(i);
	}
}

package dresseur;

import java.util.ArrayList;
import java.util.Scanner;

import interfaces.IAttaque;
import interfaces.ICapacite;
import interfaces.IDresseur;
import interfaces.IPokemon;
import pokemon.Capacite;
import pokemon.Pokemon;

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
	// TODO Auto-generated method stub
	
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
	
	return pok;
}

@Override
public IAttaque choisitAttaque(IPokemon attaquant, IPokemon defenseur) {
	
	Capacite[] capList =  (Capacite[]) attaquant.getCapacitesApprises(); // Récupère toutes les capacités de l'attaquant
	
	Scanner scanner = new Scanner(System.in);

	System.out.print("Choose the move to use\n" + attaquant.getCapacitesApprises());
	String numAttaque = scanner.nextLine();
	System.out.println();

	scanner.close();
	
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

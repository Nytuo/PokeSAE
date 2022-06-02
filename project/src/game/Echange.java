package game;

import interfaces.IEchange;
import interfaces.IPokemon;
import java.util.Scanner;
import pokemon.Capacite;

public class Echange implements IEchange {

  @Override
  public int calculeDommage(IPokemon lanceur, IPokemon receveur) {

    return 0;
  }

  @Override
  public void utilise() {}

  @Override
  public void setPokemon(IPokemon pok) {}

  @Override
  public IPokemon echangeCombattant() {

    Capacite[] equipe =
        (Capacite[])
            attaquant.getCapacitesApprises(); // R�cup�re toutes les capacit�s de l'attaquant

    Scanner scanner = new Scanner(System.in);

    System.out.print("Choose your new pokemon\n" + equipe);
    String numAttaque = scanner.nextLine();
    System.out.println();

    scanner.close();

    return (IPokemon) this;
  }
}

package pokemon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import interfaces.ICategorie;
import interfaces.IType;


public class Capacite implements interfaces.ICapacite, interfaces.ICategorie, interfaces.IType {

    public enum EnumCapacite {

        Combat(1),Dragon(2),Eau(3),Electrique(4),Feu(5),Glace(6),Insecte(7),Normal(8),Plante(9),Poison(10),Psy(11),Roche(12),Sol(13),Spectre(14),Vol(15);

        private int id;

        EnumCapacite(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }


    String nom;
    interfaces.IType type;
    interfaces.ICategorie categorie;
    int puissance;
    double precision;
    int PP;
    int PPmax;

    public Capacite(String nom, /*interfaces.IType type, interfaces.ICategorie categorie,*/ int puissance, double precision, int PP) {
        this.nom = nom;
        this.type = type;
        this.categorie = categorie;
        this.puissance = puissance;
        this.precision = precision;
        this.PP = PP;
        this.PPmax = PP;

    }


    @Override
    public boolean isSpecial() {

        return categorie.toString().equals("Special");
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public double getPrecision() {
        return precision;
    }

    @Override
    public int getPuissance() {
        return puissance;
    }

    @Override
    public int getPP() {
        return PP;
    }

    @Override
    public void resetPP() {
        this.PP = this.PPmax;
    }

    @Override
    public ICategorie getCategorie() {
        return categorie;
    }

    @Override
    public IType getType() {
        return type;
    }

    @Override
    public int calculeDommage(/*IPokemon lanceur, IPokemon receveur*/) {
/*        IType[] typeLanceur = lanceur.getEspece().getTypes();
        IType[] typeReceiveur = receveur.getEspece().getTypes();*/
        int dommage = 0;
        HashMap<String, String[]> csv = initializeFromCSV();
        System.out.println(csv.get("Combat")[EnumCapacite.Combat.getId()]);

        return dommage;
    }

    @Override
    public void utilise() {
        this.PP--;
    }

    public HashMap<String,String[]> initializeFromCSV() {
        HashMap<String,String[]> lines = new HashMap<>();
        try {

            String file = String.valueOf(new File("external/efficacites.csv"));
            Scanner sc = new Scanner(new File(file));
            sc.useDelimiter(";");
            sc.nextLine();
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split(";");
                lines.put(line[0],line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return lines;
    }
}

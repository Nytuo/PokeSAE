package pokemon;

public class Stats implements interfaces.IStat {
    private int PV;
    private int force;
    private int defense;
    private int special;
    private int vitesse;

    public Stats(int PV, int force, int defense, int special, int vitesse) {
        this.PV = PV;
        this.force = force;
        this.defense = defense;
        this.special = special;
        this.vitesse = vitesse;

    }


    @Override
    public int getPV() {
        return PV;
    }

    @Override
    public int getForce() {
        return force;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSpecial() {
        return special;
    }

    @Override
    public int getVitesse() {
        return vitesse;
    }

    @Override
    public void setPV(int i) {
        this.PV = i;
    }

    @Override
    public void setForce(int i) {
        this.force = i;
    }

    @Override
    public void setDefense(int i) {
        this.defense = i;
    }

    @Override
    public void setVitesse(int i) {
        this.vitesse = i;
    }

    @Override
    public void setSpecial(int i) {
        this.special = i;
    }
}

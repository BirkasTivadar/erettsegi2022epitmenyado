package epitmenyado;


public class Telek {

    private String adoszam;

    private String utca;

    private String hazszam;

    private String adoSav;

    private int alapTerulet;

    private int ado;

    public Telek(String adoszam, String utca, String hazszam, String adoSav, int alapTerulet) {
        this.adoszam = adoszam;
        this.utca = utca;
        this.hazszam = hazszam;
        this.adoSav = adoSav;
        this.alapTerulet = alapTerulet;
    }

    public String getAdoszam() {
        return adoszam;
    }

    public String getUtca() {
        return utca;
    }

    public String getHazszam() {
        return hazszam;
    }

    public String getAdoSav() {
        return adoSav;
    }

    public int getAlapTerulet() {
        return alapTerulet;
    }

    public int getAdo() {
        return ado;
    }

    public void setAdo(int ado) {
        this.ado = ado;
    }
}

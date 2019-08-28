package models;

public class Piloto {
    private String codePiloto;
    private String namePiloto;

    public Piloto(String codePiloto, String namePiloto) {
        this.codePiloto = codePiloto;
        this.namePiloto = namePiloto;
    }

    /**
     * Code of Pilot
     * @return String
     */
    public String getCodePiloto() {
        return codePiloto;
    }

    /***
     * Name of Pilot
     * @return String
     */
    public String getNamePiloto() {
        return namePiloto;
    }
}

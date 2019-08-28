package models;

import java.util.Comparator;
import java.util.List;

public class Corrida implements Comparator<ValuesAround> {
    // Pilot what are racing
    private Piloto _piloto;
    //Values that obtains for every found
    private List<ValuesAround> _valuesAround;

    public Corrida() {
    }

    public Corrida(Piloto piloto, List<ValuesAround> lstValues){
        this._piloto = piloto;
        this._valuesAround = lstValues;
    };

    public Piloto get_piloto() {
        return _piloto;
    }

    public List<ValuesAround> get_valuesAround() {
        return _valuesAround;
    }

    @Override
    public int compare(ValuesAround o1, ValuesAround o2) {
        return o1.getTimeAround().compareTo(o2.getTimeAround());
    }
}

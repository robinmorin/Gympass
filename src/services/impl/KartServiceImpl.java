package services.impl;

import models.Corrida;
import models.Piloto;
import models.ValuesAround;
import readers.ReadFile;
import readers.impl.ReadFileImpl;
import services.KartService;

import java.io.EOFException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class KartServiceImpl implements KartService {

    public static final KartService INSTANCE = new KartServiceImpl();
    private ReadFile _reader = new ReadFileImpl();
    private Piloto _pilotoWinner;

    private Hashtable<String, Corrida> _mapCorridas = new Hashtable<>();

    private KartServiceImpl(){};

    public Hashtable<String, Corrida> loadArquivo(String nomeArquivo, boolean temCabecera) throws Exception {
        try {
            boolean isOpen = _reader.openFile(nomeArquivo);
            _reader.isTemCabecera(temCabecera);
            if (isOpen) {
                _mapCorridas = new Hashtable<String,Corrida>();
                Corrida corrida = new Corrida();
                while ((corrida = _reader.readRegistro(corrida)) != null) {
                    // pilotoWinner precisei pra referencia no momento de calcular o tempo depois do Vencedor
                    if(_pilotoWinner == null && corrida.get_valuesAround().get(0).getNumberAround().equals(4)) _pilotoWinner = corrida.get_piloto();
                    Corrida existCorrida = _mapCorridas.get(corrida.get_piloto().getCodePiloto());
                    if (existCorrida == null) _mapCorridas.put(corrida.get_piloto().getCodePiloto(), corrida);
                    else {
                        existCorrida.get_valuesAround().addAll(corrida.get_valuesAround());
                        _mapCorridas.put(corrida.get_piloto().getCodePiloto(), existCorrida);
                    }
                }
            }
        } catch(EOFException ex){
        } finally {
            _reader.closeFile();
        }
        return _mapCorridas;
    }

    public HashMap<Piloto, Integer>  melhorVoltaByPiloto() {
        HashMap<Piloto, Integer> retornoResultados = new HashMap<>();
        if(_mapCorridas.size() > 0){
            _mapCorridas.forEach((key, values) -> {
                values.get_valuesAround().sort(Comparator.comparing(ValuesAround::getTimeAround));
                retornoResultados.put(values.get_piloto(),values.get_valuesAround().get(0).getNumberAround());
            });
        }
        return retornoResultados;
    }

    public Corrida melhorVoltaDaCarreira() {
        Corrida retornoCorrida;
        List<Corrida> lstCorrida = new ArrayList<>();
        if(_mapCorridas.size() > 0){
            _mapCorridas.forEach((key, values) -> {
                values.get_valuesAround().sort(Comparator.comparing(ValuesAround::getTimeAround));
                lstCorrida.add(values);
            });
            lstCorrida.sort((Corrida c1, Corrida c2) -> c1.get_valuesAround().get(0).getTimeAround().compareTo(c2.get_valuesAround().get(0).getTimeAround()));
        }
        retornoCorrida = lstCorrida.size() > 0 ? lstCorrida.get(0) : null;
        return retornoCorrida;
    }

    public HashMap<Piloto, Double> velocidadeMedia() {
        HashMap<Piloto, Double> retornoResultados = new HashMap<>();
        if(_mapCorridas.size() > 0){
            _mapCorridas.forEach((key, values) -> {
                    // Para resolver o tema do lambda onde as variaveis tem que ser efetivamente Final,
                    // se cria um array para que ao salir do bucle lambda possa pegar o valor por referencia no array.
                    Double[] promedio = new Double[1];
                    int[] qtaVoltas = new int[1];
                    promedio[0] = 0d;
                    qtaVoltas[0] = 0;
                    values.get_valuesAround().stream().forEach((itemValue)-> {
                        qtaVoltas[0]++;
                        promedio[0] = promedio[0] + itemValue.getAverageAround();
                    });
                    retornoResultados.put(values.get_piloto(),promedio[0] / qtaVoltas[0]);
            });
        }
        return retornoResultados;
    }

    public HashMap<Piloto, String>  lstPilotAfterWinner() {
        HashMap<String, LocalTime> mapCalculos = new HashMap<>();
        HashMap<Piloto, String> retornoResultados = new HashMap<>();
        if(_mapCorridas.size() > 0){
            _mapCorridas.forEach((key, values) -> {
                values.get_valuesAround().sort(Comparator.comparing(ValuesAround::getNumberAround).reversed());
                mapCalculos.put(values.get_piloto().getCodePiloto().concat(",").concat(values.get_piloto().getNamePiloto()),values.get_valuesAround().get(0).getHourAround());
            });
            LocalTime tempoWinner = mapCalculos.get(_pilotoWinner.getCodePiloto().concat(",").concat(_pilotoWinner.getNamePiloto()));
            mapCalculos.forEach((key,value) -> {
                if(!key.split(",")[0].equalsIgnoreCase(_pilotoWinner.getCodePiloto())){
                    Duration diferencia = Duration.between(tempoWinner,value);
                    retornoResultados.put(new Piloto(key.split(",")[0],key.split(",")[1]),String.valueOf(diferencia.getSeconds()).concat("s.").concat(String.valueOf(diferencia.getNano()).substring(0,3)));
                };
            });
        }
        return retornoResultados;
    }
}

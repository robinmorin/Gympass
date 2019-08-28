package services;

import models.Corrida;
import models.Piloto;

import java.util.HashMap;
import java.util.Hashtable;

public interface KartService {

    Hashtable<String, Corrida> loadArquivo(String nomeArquivo, boolean temCabecera) throws Exception ;
    HashMap<Piloto, Integer > melhorVoltaByPiloto();
    Corrida melhorVoltaDaCarreira();
    HashMap<Piloto, Double> velocidadeMedia();
    HashMap<Piloto, String> lstPilotAfterWinner();

}

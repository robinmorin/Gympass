package app;

import models.Corrida;
import models.Piloto;
import services.KartService;
import services.impl.KartServiceImpl;

import javax.swing.text.NumberFormatter;
import java.io.EOFException;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Hashtable;

public class KartApp {

    public static void main(String[] arg){
        KartApp application = new KartApp();
        application.processar();
    }

    public KartApp() {
    }

    public void processar(){
        Hashtable<String, Corrida> retorno = new Hashtable<String, Corrida>();

        HashMap<Piloto, Integer> mapMelhorVoltaByPiloto = new HashMap<Piloto, Integer>();
        Corrida melhorCorrida;
        HashMap<Piloto, Double> mapPromedioVelocidade = new HashMap<>();
        HashMap<Piloto, String> mapAfterPilotWinner = new HashMap<>();

        try {
            KartService service = KartServiceImpl.INSTANCE;
            retorno = service.loadArquivo(".\\log-kart.log", true);
            mapMelhorVoltaByPiloto = service.melhorVoltaByPiloto();
            melhorCorrida = service.melhorVoltaDaCarreira();
            mapPromedioVelocidade = service.velocidadeMedia();
            mapAfterPilotWinner = service.lstPilotAfterWinner();

            System.out.println("Melhores voltas por Piloto");
            mapMelhorVoltaByPiloto.forEach((key,value) -> {
                System.out.println("   ".concat(key.getCodePiloto()).concat(" - ").concat(key.getNamePiloto().concat(" -> Vuelta : ").concat(String.valueOf(value))));
            });
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Melhor volta da Corrida");
            System.out.println("   ".concat(melhorCorrida.get_piloto().getCodePiloto()).concat(" - ").concat(melhorCorrida.get_piloto().getNamePiloto()
                                    .concat(" / Vuelta : ").concat(String.valueOf(melhorCorrida.get_valuesAround().get(0).getNumberAround())))
                                    .concat(" / Tempo : ").concat(String.valueOf(melhorCorrida.get_valuesAround().get(0).getTimeAround())));
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Velocidade média de cada piloto durante toda corrida");
            mapPromedioVelocidade.forEach((key,value) -> {
                System.out.println("   ".concat(key.getCodePiloto()).concat(" - ").concat(key.getNamePiloto().concat(" -> Velocidade Média: ").concat(new DecimalFormat("#0.000").format(value).replace(".",","))));
            });
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println("Tempo a mais depois da chegada do Vencedor");
            mapAfterPilotWinner.forEach((key,value) -> System.out.println("   ".concat(key.getCodePiloto()).concat(" - ").concat(key.getNamePiloto()).concat(" -> Diferença: ").concat(value)));
        } catch (EOFException ex){
            System.out.println(ex.getMessage() + " - Finalizou o processo");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}


package readers.impl;

import models.Corrida;
import models.Piloto;
import models.ValuesAround;
import readers.ReadFile;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe para lêr arquivos, crie de modo generico para que possa ser re-utilizavél
 * só adicionando o tratamento especifico das colunas desse arquivo, traducindo ao
 * tipo de objeto que se precisa.
 */
public class ReadFileImpl implements ReadFile {
    private BufferedReader _reader = null;
    private boolean _temCabecera = false;
    private int _linha=0;

    /**
     * Metodo para abrir arquivo
     * @param arquivo_log Nome do arquivo incluindo Path.
     * @return Retorna um boolean pra disser se foi aberto corretamente o arquivo
     * @throws Exception
     */
    public boolean openFile(String arquivo_log) throws  Exception{
        boolean isOpen = true;
        try{
            _reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(new File(arquivo_log)), "Cp1252"));
        }catch (FileNotFoundException ex){
            throw new Exception("Arquivo não existe.");
        }catch (Exception ex){
            throw new Exception("Aconteceu o seguinte erro ao tentar abrir o arquivo: \n" + ex.getMessage());
        }
        return isOpen;
    }

    /**
     * Close arquivo que tinha aberto
     * @throws Exception
     */
    public void closeFile() throws Exception{
        if(_reader != null){
            try {
                _reader.close();
            }catch (IOException ex){
                throw new Exception("Arquivo está bloqueado ou já foi fechado");
            }
        }
    }

    /**
     * Lê uma linha as vez, e traduz pra um objeto especifico.
     * @param typeObject
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T readRegistro(T typeObject) throws Exception {
        T linhaReadObject = null;
        try {
            String registro;
            if ((registro = _reader.readLine()) != null) {
            } else {
                throw new EOFException("Chegou no final do arquivo");
            }
            _linha++;
            if(_linha == 1 && _temCabecera){
                registro = _reader.readLine();
                if(registro == null) throw new Exception("Arquivo esta vazio");
            }

            //No caso de lêr outros arquivos que trouxem outro tipo de objeto, deverá adicionar o tratamento dele.
            if(typeObject instanceof Corrida){
                List<ValuesAround> lstValues = new ArrayList<>();
                LocalTime hourAround = LocalTime.parse(registro.substring(0,12));
                Piloto pilot = new Piloto(registro.substring(13,22).trim(),registro.substring(24,56).trim());
                LocalTime timeAround = LocalTime.parse("00:0"+ registro.substring(62,72).trim());
                Double averageAround = Double.valueOf(registro.substring(75).replace(",",".").trim());
                Integer numberAround = Integer.valueOf(registro.substring(56,60).trim());
                ValuesAround valuesAround = new ValuesAround.Builder()
                                                .setHourAround(hourAround)
                                                .setTimeAround(timeAround)
                                                .setAverageAround(averageAround)
                                                .setNumberAround(numberAround).build();
                lstValues.add(valuesAround);
                Corrida objectCons = new Corrida(pilot,lstValues);
                linhaReadObject = (T) objectCons;
            } else {
                throw new Exception("Tipo de Objeto desconhecido");
            }
        }catch (EOFException ex) {
            throw ex;
        }catch (Exception ex) {
            throw new Exception("Aconteceu o seguinte erro ao tentar lê o arquivo: \n" + ex.getMessage());
        }
        return linhaReadObject;
    }

    public void isTemCabecera(boolean value) throws Exception {
        _temCabecera = value;
    }

}

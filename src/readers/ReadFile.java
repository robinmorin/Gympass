package readers;

public interface ReadFile {

    boolean openFile(String arquivo_log) throws  Exception;

    void closeFile() throws Exception;

    <T> T readRegistro(T typeObject) throws Exception;

    void isTemCabecera(boolean value) throws Exception;
}

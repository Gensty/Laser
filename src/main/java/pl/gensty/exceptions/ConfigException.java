package pl.gensty.exceptions;

public class ConfigException  extends RuntimeException{
    public ConfigException(String cellName) {
        super("Błąd w komórce \"" + cellName + "\" w konfiguratorze Excel.");
    }
}

package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;

public interface ConfigBuilderStrategy {
    String ORDER = "Nr zlecenia";
    String TYPE = "Typ urządzenia";
    String SIZE = "Rozmiar";
    String MATERIAL = "Powłoka";
    String DEVICE_QUANTITY = "Ilość sztuk";

    //Other
    String DRIVE_TYPE = "Typ napędu";

    //NPK
    String FEET_TYPE = "Typ stopy";
    String FILLING = "Rodzaj zasypu";
    String CLUTCH = "Sprzęgło";
    String VENTING_SEGMENT = "Segment odpowietrzający";
    String MAINTENANCE_PLATFORM = "Podest obsługowy";

    //SPR
    String CHAIN_SUPPORT = "Prowadzenie łańcucha";


    AbstractConfig buildConfig(String excelPath);
}

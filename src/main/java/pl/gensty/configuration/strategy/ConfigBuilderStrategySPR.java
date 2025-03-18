package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigSPR;
import pl.gensty.utils.ExcelReader;

public class ConfigBuilderStrategySPR implements ConfigBuilderStrategy {
    private final ExcelReader excelReader;

    public ConfigBuilderStrategySPR(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigSPR.builder()
                .order(excelReader.getDeviceConfig(ORDER))
                .type(excelReader.getDeviceConfig(TYPE))
                .size(excelReader.getDeviceConfig(SIZE))
                .material(excelReader.getDeviceConfig(MATERIAL))
                .deviceQuantity(Integer.parseInt(excelReader.getDeviceConfig(DEVICE_QUANTITY)))
                .chainSupport(excelReader.getDeviceConfig(CHAIN_SUPPORT))
                .build();
    }
}

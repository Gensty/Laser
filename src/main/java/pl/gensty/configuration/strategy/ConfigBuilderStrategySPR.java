package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigSPR;

import static pl.gensty.utils.ExcelReader.readDeviceConfig;

public class ConfigBuilderStrategySPR implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigSPR.builder()
                .order(readDeviceConfig(excelPath, ORDER))
                .type(readDeviceConfig(excelPath, TYPE))
                .size(readDeviceConfig(excelPath, SIZE))
                .material(readDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(readDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .chainSupport(readDeviceConfig(excelPath, CHAIN_SUPPORT))
                .build();
    }
}

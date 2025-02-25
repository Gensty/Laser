package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigSPR;

import static pl.gensty.utils.ExcelReader.getDeviceConfig;

public class ConfigBuilderStrategySPR implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigSPR.builder()
                .order(getDeviceConfig(excelPath, ORDER))
                .type(getDeviceConfig(excelPath, TYPE))
                .size(getDeviceConfig(excelPath, SIZE))
                .material(getDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(getDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .chainSupport(getDeviceConfig(excelPath, CHAIN_SUPPORT))
                .build();
    }
}

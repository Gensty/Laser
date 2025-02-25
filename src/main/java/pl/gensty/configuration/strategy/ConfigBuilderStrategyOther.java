package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;

import static pl.gensty.utils.ExcelReader.getDeviceConfig;

public class ConfigBuilderStrategyOther implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigOther.builder()
                .order(getDeviceConfig(excelPath, ORDER))
                .type(getDeviceConfig(excelPath, TYPE))
                .size(getDeviceConfig(excelPath, SIZE))
                .material(getDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(getDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .driveType(getDeviceConfig(excelPath, DRIVE_TYPE))
                .build();
    }
}

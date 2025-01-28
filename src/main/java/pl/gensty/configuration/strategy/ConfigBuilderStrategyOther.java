package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;

import static pl.gensty.utils.ExcelReader.readDeviceConfig;

public class ConfigBuilderStrategyOther implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigOther.builder()
                .order(readDeviceConfig(excelPath, ORDER))
                .type(readDeviceConfig(excelPath, TYPE))
                .size(readDeviceConfig(excelPath, SIZE))
                .material(readDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(readDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .driveType(readDeviceConfig(excelPath, DRIVE_TYPE))
                .build();
    }
}

package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigOther;
import pl.gensty.utils.ExcelReader;

public class ConfigBuilderStrategyOther implements ConfigBuilderStrategy {
    private final ExcelReader excelReader;

    public ConfigBuilderStrategyOther(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigOther.builder()
                .order(excelReader.getDeviceConfig(ORDER))
                .type(excelReader.getDeviceConfig(TYPE))
                .size(excelReader.getDeviceConfig(SIZE))
                .material(excelReader.getDeviceConfig(MATERIAL))
                .deviceQuantity(Integer.parseInt(excelReader.getDeviceConfig(DEVICE_QUANTITY)))
                .driveType(excelReader.getDeviceConfig(DRIVE_TYPE))
                .build();
    }
}

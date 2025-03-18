package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigNPK;
import pl.gensty.utils.ExcelReader;

import static pl.gensty.utils.Utils.convertStringToBoolean;

public class ConfigBuilderStrategyNPK implements ConfigBuilderStrategy {
    private final ExcelReader excelReader;

    public ConfigBuilderStrategyNPK(ExcelReader excelReader) {
        this.excelReader = excelReader;
    }

    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigNPK.builder()
                .order(excelReader.getDeviceConfig(ORDER))
                .type(excelReader.getDeviceConfig(TYPE))
                .size(excelReader.getDeviceConfig(SIZE))
                .material(excelReader.getDeviceConfig(MATERIAL))
                .deviceQuantity(Integer.parseInt(excelReader.getDeviceConfig(DEVICE_QUANTITY)))
                .feetType(excelReader.getDeviceConfig(FEET_TYPE))
                .filling(excelReader.getDeviceConfig(FILLING))
                .isClutch(convertStringToBoolean(excelReader.getDeviceConfig(CLUTCH)))
                .isVentingSegment(convertStringToBoolean(excelReader.getDeviceConfig(VENTING_SEGMENT)))
                .isMaintenancePlatform(convertStringToBoolean(excelReader.getDeviceConfig(MAINTENANCE_PLATFORM)))
                .build();
    }
}

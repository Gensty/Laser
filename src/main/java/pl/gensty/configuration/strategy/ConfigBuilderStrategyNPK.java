package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigNPK;

import static pl.gensty.utils.ExcelReader.*;

public class ConfigBuilderStrategyNPK implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigNPK.builder()
                .order(readDeviceConfig(excelPath, ORDER))
                .type(readDeviceConfig(excelPath, TYPE))
                .size(readDeviceConfig(excelPath, SIZE))
                .material(readDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(readDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .feetType(readDeviceConfig(excelPath, FEET_TYPE))
                .filling(readDeviceConfig(excelPath, FILLING))
                .isClutch(convertStringToBoolean(readDeviceConfig(excelPath, CLUTCH)))
                .isVentingSegment(convertStringToBoolean(readDeviceConfig(excelPath, VENTING_SEGMENT)))
                .isMaintenancePlatform(convertStringToBoolean(readDeviceConfig(excelPath, MAINTENANCE_PLATFORM)))
                .build();
    }
}

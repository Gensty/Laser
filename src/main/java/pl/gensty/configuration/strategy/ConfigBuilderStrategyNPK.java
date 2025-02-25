package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.configuration.ConfigNPK;

import static pl.gensty.utils.ExcelReader.*;

public class ConfigBuilderStrategyNPK implements ConfigBuilderStrategy {
    @Override
    public AbstractConfig buildConfig(String excelPath) {
        return ConfigNPK.builder()
                .order(getDeviceConfig(excelPath, ORDER))
                .type(getDeviceConfig(excelPath, TYPE))
                .size(getDeviceConfig(excelPath, SIZE))
                .material(getDeviceConfig(excelPath, MATERIAL))
                .deviceQuantity(Integer.parseInt(getDeviceConfig(excelPath, DEVICE_QUANTITY)))
                .feetType(getDeviceConfig(excelPath, FEET_TYPE))
                .filling(getDeviceConfig(excelPath, FILLING))
                .isClutch(convertStringToBoolean(getDeviceConfig(excelPath, CLUTCH)))
                .isVentingSegment(convertStringToBoolean(getDeviceConfig(excelPath, VENTING_SEGMENT)))
                .isMaintenancePlatform(convertStringToBoolean(getDeviceConfig(excelPath, MAINTENANCE_PLATFORM)))
                .build();
    }
}

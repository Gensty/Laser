package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.enums.DeviceType;

public class FactoryConfig {
    public static AbstractConfig createConfig(DeviceType deviceType, String excelPath) {
        return ConfigBuilderFactory.getStrategy(deviceType).buildConfig(excelPath);
    }
}

package pl.gensty.configuration.strategy;

import pl.gensty.configuration.AbstractConfig;
import pl.gensty.enums.DeviceType;
import pl.gensty.utils.ExcelReader;

import java.io.IOException;

public class FactoryConfig {
    public static AbstractConfig createConfig(DeviceType deviceType, String excelPath) throws IOException {
        ConfigBuilderFactory factory = new ConfigBuilderFactory(new ExcelReader(excelPath));
        return factory.getStrategy(deviceType).buildConfig(excelPath);
    }
}

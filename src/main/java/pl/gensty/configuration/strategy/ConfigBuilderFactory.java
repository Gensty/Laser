package pl.gensty.configuration.strategy;

import pl.gensty.enums.DeviceType;
import pl.gensty.utils.ExcelReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigBuilderFactory {
    private final Map<DeviceType, ConfigBuilderStrategy> strategies = new HashMap<>();

    public ConfigBuilderFactory(ExcelReader excelReader) {
        strategies.put(DeviceType.NPK, new ConfigBuilderStrategyNPK(excelReader));
        strategies.put(DeviceType.SPR, new ConfigBuilderStrategySPR(excelReader));
        strategies.put(DeviceType.OTHER, new ConfigBuilderStrategyOther(excelReader));
    }

    public ConfigBuilderStrategy getStrategy(DeviceType deviceType) {
        return Optional.ofNullable(strategies.get(deviceType))
                .orElseThrow(() -> new IllegalArgumentException("Nieobsługiwany typ urządzenia"));
    }
}
    
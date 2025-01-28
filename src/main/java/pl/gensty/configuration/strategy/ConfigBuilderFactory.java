package pl.gensty.configuration.strategy;

import pl.gensty.enums.DeviceType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigBuilderFactory {
    private static final Map<DeviceType, ConfigBuilderStrategy> strategies = new HashMap<>();

    static {
        strategies.put(DeviceType.NPK, new ConfigBuilderStrategyNPK());
        strategies.put(DeviceType.SPR, new ConfigBuilderStrategySPR());
        strategies.put(DeviceType.OTHER, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.NZZ, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RDA, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RDS, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RTS, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RDAP, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RDSP, new ConfigBuilderStrategyOther());
//        strategies.put(DeviceType.RTSP, new ConfigBuilderStrategyOther());
    }
    public static ConfigBuilderStrategy getStrategy(DeviceType deviceType) {
        return Optional.ofNullable(strategies.get(deviceType))
                .orElseThrow(() -> new IllegalArgumentException("Nieobsługiwany typ urządzenia"));
    }


}
    
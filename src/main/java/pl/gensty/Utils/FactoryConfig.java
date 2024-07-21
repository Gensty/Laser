package pl.gensty.Utils;

import pl.gensty.Configuration.AbstractConfig;
import pl.gensty.Configuration.ConfigNPK;
import pl.gensty.Configuration.ConfigOther;
import pl.gensty.Configuration.ConfigSPR;
import pl.gensty.Enums.DeviceType;

import java.util.Map;

public class FactoryConfig {
    public static AbstractConfig createConfig(DeviceType deviceType, Map<String, Object> params) {
        switch (deviceType) {
            case SPR:
//                return createConfigSPR(params);
            case NPK:
                return createConfigNPK(params);
            default:
                return createConfigOther(params);
        }
    }

    private static ConfigSPR createConfigSPR(Map<String, Object> params) {
        return new ConfigSPR.Builder()
//                .order((String) params.get("order"))
//                .type((String) params.get("type"))
//                .size((String) params.get("size"))
//                .material((String) params.get("material"))
//                .deviceQuantity((Integer) params.get("deviceQuantity"))
//                .height((Double) params.get("height"))
//                .feetType((String) params.get("feetType"))
//                .isVentingSegment((Boolean) params.get("isVentingSegment"))
//                .isMaintenancePlatform((Boolean) params.get("isMaintenancePlatform"))
                .build();
    }

    private static ConfigNPK createConfigNPK(Map<String, Object> params) {
        return new ConfigNPK.Builder()
                .order((String) params.get("order"))
                .type((String) params.get("type"))
                .size((String) params.get("size"))
                .material((String) params.get("material"))
                .deviceQuantity((Integer) params.get("deviceQuantity"))
                .feetType((String) params.get("feetType"))
                .isVentingSegment((Boolean) params.get("isVentingSegment"))
                .isMaintenancePlatform((Boolean) params.get("isMaintenancePlatform"))
                .build();
    }

    private static ConfigOther createConfigOther(Map<String, Object> params) {
        return new ConfigOther.Builder()
                .order((String) params.get("order"))
                .type((String) params.get("type"))
                .size((String) params.get("size"))
                .material((String) params.get("material"))
                .deviceQuantity((Integer) params.get("deviceQuantity"))
                .driveType((String) params.get("driveType"))
                .build();
    }
}

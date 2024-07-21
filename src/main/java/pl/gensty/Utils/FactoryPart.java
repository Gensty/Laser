package pl.gensty.Utils;

import pl.gensty.DevicePart.AbstractPart;
import pl.gensty.Enums.DeviceType;

import java.util.Map;

public class FactoryPart {
    public static AbstractPart createInstance(DeviceType deviceType, Map<String, Object> params) {
        switch (deviceType) {
            case NPK:
                return createPartNPK(params);
            case SPR:
//                return createPartSPR(params);
            default:
                return createPartOther(params);
        }
    }

    private static ElevatorPart createPartNPK(Map<String, Object> params) {

    }
}

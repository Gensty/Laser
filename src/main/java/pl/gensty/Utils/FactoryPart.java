package pl.gensty.Utils;

import pl.gensty.DevicePart.*;
import pl.gensty.Enums.DeviceType;

import java.util.Map;

public class FactoryPart {
    public static AbstractPart createInstance(DeviceType deviceType, String module, Map<String, Object> params) {
        return switch (deviceType) {
            case SPR -> createPartSPR(params);
            case NPK -> switch (module) {
                case "STS", "STZ" -> createPartFeet(params);
                default -> createPart(params);
            };
            default -> createPartDriveType(params);
        };
    }

    private static Part createPart(Map<String, Object> params) {
        return new Part.Builder()
                .numberEDT((String) params.get("numberEDT"))
                .material((String) params.get("material"))
                .thickness((int) params.get("thickness"))
                .quantity((int) params.get("quantity"))
                .description((String) params.get("description"))
                .build();
    }

    private static PartDriveType createPartDriveType(Map<String, Object> params) {
        return new PartDriveType.Builder()
                .numberEDT((String) params.get("numberEDT"))
                .material((String) params.get("material"))
                .thickness((int) params.get("thickness"))
                .quantity((int) params.get("quantity"))
                .description((String) params.get("description"))
                .isElectric((Boolean) params.get("isElectric"))
                .isPneumatic((Boolean) params.get("isPneumatic"))
                .isManual((Boolean) params.get("isManual"))
                .build();
    }

    private static PartSPR createPartSPR(Map<String, Object> params) {
        return new PartSPR.Builder()
                .numberEDT((String) params.get("numberEDT"))
                .material((String) params.get("material"))
                .thickness((int) params.get("thickness"))
                .quantity((int) params.get("quantity"))
                .description((String) params.get("description"))
                .rolls((Boolean) params.get("rolls"))
                .upperDeck((Boolean) params.get("upperDeck"))
                .transportingUpperDeck((Boolean) params.get("transportingUpperDeck"))
                .guideBar((Boolean) params.get("guideBar"))
                .build();
    }

    private static PartFeet createPartFeet(Map<String, Object> params) {
        return new PartFeet.Builder()
                .numberEDT((String) params.get("numberEDT"))
                .material((String) params.get("material"))
                .thickness((int) params.get("thickness"))
                .quantity((int) params.get("quantity"))
                .description((String) params.get("description"))
                .filling1Way((Boolean) params.get("filling1Way"))
                .filling2Way((Boolean) params.get("filling2Way"))
                .fillingNoWay((Boolean) params.get("fillingNoWay"))
                .build();
    }
}

package pl.gensty.configuration;

import lombok.ToString;
import pl.gensty.enums.Module;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ConfigNPK extends AbstractConfig {
    public final String feetType;
    public final String filling;
    public final Boolean isClutch;
    public final Boolean isVentingSegment;
    public final Boolean isMaintenancePlatform;

    protected ConfigNPK(
            String order,
            String type,
            String size,
            String material,
            Integer deviceQuantity,
            String feetType,
            String filling,
            Boolean isClutch,
            Boolean isVentingSegment,
            Boolean isMaintenancePlatform
    ) {
        super(order, type, size, material, deviceQuantity);
        this.feetType = feetType;
        this.filling = filling;
        this.isClutch = isClutch;
        this.isVentingSegment = isVentingSegment;
        this.isMaintenancePlatform = isMaintenancePlatform;
    }

    @Override
    public List<Module> getModules() {
        List<Module> modules = new ArrayList<>();

        modules.add(Module.GL);
        modules.add(Module.RT);
//        modules.add(Module.RU);
        modules.add(Module.RO);

        if (Module.STS.toString().equals(feetType)) {
            modules.add(Module.STS);
        } else {
            modules.add(Module.STZ);
        }

//        if (!isClutch) {
//            modules.add(Module.RWM_S);
//        } else {
//            modules.add(Module.RWM_HKK);
//        }

        if (isVentingSegment) modules.add(Module.SO);
        if (isMaintenancePlatform) modules.add(Module.PO);

        return modules;
    }

    public static ConfigNPKBuilder builder() {
        return new ConfigNPKBuilder();
    }

    public static class ConfigNPKBuilder {
        private String order;
        private String type;
        private String size;
        private String material;
        private Integer deviceQuantity;
        private String feetType;
        private String filling;
        private Boolean isClutch;
        private Boolean isVentingSegment;
        private Boolean isMaintenancePlatform;

        public ConfigNPKBuilder order(String order) {
            this.order = order;
            return this;
        }

        public ConfigNPKBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ConfigNPKBuilder size(String size) {
            this.size = size;
            return this;
        }

        public ConfigNPKBuilder material(String material) {
            this.material = material;
            return this;
        }

        public ConfigNPKBuilder deviceQuantity(Integer deviceQuantity) {
            this.deviceQuantity = deviceQuantity;
            return this;
        }

        public ConfigNPKBuilder feetType(String feetType) {
            this.feetType = feetType;
            return this;
        }

        public ConfigNPKBuilder filling(String filling) {
            this.filling = filling;
            return this;
        }

        public ConfigNPKBuilder isClutch(Boolean isClutch) {
            this.isClutch = isClutch;
            return this;
        }

        public ConfigNPKBuilder isVentingSegment(Boolean isVentingSegment) {
            this.isVentingSegment = isVentingSegment;
            return this;
        }

        public ConfigNPKBuilder isMaintenancePlatform(Boolean isMaintenancePlatform) {
            this.isMaintenancePlatform = isMaintenancePlatform;
            return this;
        }

        public ConfigNPK build() {
            return new ConfigNPK(
                    order,
                    type,
                    size,
                    material,
                    deviceQuantity,
                    feetType,
                    filling,
                    isClutch,
                    isVentingSegment,
                    isMaintenancePlatform
            );
        }
    }
}

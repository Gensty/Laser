package pl.gensty.configuration;

import lombok.Builder;
import lombok.ToString;
import pl.gensty.enums.Module;

import java.util.List;

@Builder
@ToString
public class ConfigSPR extends AbstractConfig {
    public final String chainSupport;
    public final Boolean isClutch;

    protected ConfigSPR(
            String order,
            String type,
            String size,
            String material,
            Integer deviceQuantity,
            String chainSupport,
            Boolean isClutch
    ) {
        super(order, type, size, material, deviceQuantity);
        this.chainSupport = chainSupport;
        this.isClutch = isClutch;
    }

    @Override
    public List<Module> getModules() {
        //TODO: moduły SPR po Excelu
        return null;
    }

    public static ConfigSPRBuilder builder() {
        return new ConfigSPRBuilder();
    }

    public static class ConfigSPRBuilder {
        private String order;
        private String type;
        private String size;
        private String material;
        private Integer deviceQuantity;
        private String chainSupport;
        private Boolean isClutch;

        public ConfigSPRBuilder order(String order) {
            this.order = order;
            return this;
        }

        public ConfigSPRBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ConfigSPRBuilder size(String size) {
            this.size = size;
            return this;
        }

        public ConfigSPRBuilder material(String material) {
            this.material = material;
            return this;
        }

        public ConfigSPRBuilder deviceQuantity(Integer deviceQuantity) {
            this.deviceQuantity = deviceQuantity;
            return this;
        }

        public ConfigSPRBuilder chainSupport (String chainSupport) {
            this.chainSupport = chainSupport;
            return this;
        }

        public ConfigSPRBuilder isClutch (Boolean isClutch) {
            this.isClutch = isClutch;
            return this;
        }

        public ConfigSPR build() {
            return new ConfigSPR(
                    order,
                    type,
                    size,
                    material,
                    deviceQuantity,
                    chainSupport,
                    isClutch
            );
        }
    }
}

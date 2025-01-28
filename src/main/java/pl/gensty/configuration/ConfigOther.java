package pl.gensty.configuration;

import lombok.Builder;
import lombok.ToString;
import pl.gensty.enums.Module;

import java.util.ArrayList;
import java.util.List;

@Builder
@ToString
public class ConfigOther extends AbstractConfig {
    public final String driveType;

    protected ConfigOther(String order,
                       String type,
                       String size,
                       String material,
                       Integer deviceQuantity,
                       String driveType) {
        super(order, type, size, material, deviceQuantity);
        this.driveType = driveType;
    }

    @Override
    public List<Module> getModules() {
        List<Module> modules = new ArrayList<>();
        modules.add(Module.valueOf(getType()));
        return modules;
    }

    public static ConfigOtherBuilder builder() {
        return new ConfigOtherBuilder();
    }

    public static class ConfigOtherBuilder {
        private String order;
        private String type;
        private String size;
        private String material;
        private Integer deviceQuantity;
        private String driveType;

        public ConfigOtherBuilder order(String order) {
            this.order = order;
            return this;
        }

        public ConfigOtherBuilder type(String type) {
            this.type = type;
            return this;
        }

        public ConfigOtherBuilder size(String size) {
            this.size = size;
            return this;
        }

        public ConfigOtherBuilder material(String material) {
            this.material = material;
            return this;
        }

        public ConfigOtherBuilder deviceQuantity(Integer deviceQuantity) {
            this.deviceQuantity = deviceQuantity;
            return this;
        }

        public ConfigOtherBuilder driveType (String driveType) {
            this.driveType = driveType;
            return this;
        }

        public ConfigOther build() {
            return new ConfigOther(
                    order,
                    type,
                    size,
                    material,
                    deviceQuantity,
                    driveType
            );
        }
    }
}

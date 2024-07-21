package pl.gensty.Configuration;

import pl.gensty.Enums.MaterialType;

import static pl.gensty.Utils.NumberUtils.isSingleCharNumber;

public abstract class AbstractConfig {
    private final String order;
    private final String type;
    private final String size;
    private final String material;
    private final Integer deviceQuantity;

    protected AbstractConfig(Builder<?> builder) {
        this.order = builder.order;
        this.type = builder.type;
        this.size = builder.size;
        this.material = builder.material;
        this.deviceQuantity = builder.deviceQuantity;
    }

    public String getOrder() {
        return order;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getMaterial() {
        return material;
    }

    public Integer getDeviceQuantity() {
        return deviceQuantity;
    }

    public String setFolderName() {
        return "ZL_" + order + " - " + size  + "module" + "_" + material + " (x" + isSingleCharNumber(deviceQuantity) + ")";
    }

    public String setFolderName(MaterialType materialType) {
        return "ZL_" + order + " - " + size + "module" + "_" + materialType.toString() + " (x" + isSingleCharNumber(deviceQuantity) + ")";
    }

    public static abstract class Builder<T extends Builder<T>> {
        private String order;
        private String type;
        private String size;
        private String material;
        private Integer deviceQuantity;

        public T order(String order) {
            this.order = order;
            return self();
        }

        public T type(String type) {
            this.type = type;
            return self();
        }

        public T size(String size) {
            this.size = size;
            return self();
        }

        public T material(String material) {
            this.material = material;
            return self();
        }

        public T deviceQuantity(Integer deviceQuantity) {
            this.deviceQuantity = deviceQuantity;
            return self();
        }

        protected abstract T self();

        public abstract AbstractConfig build();

        protected void validate() {
            if (order == null) {
                throw new NullPointerException("Nr zlecenia nie może być pusty! Sprawdź konfigurator Excel.");
            }
            if (type == null) {
                throw new NullPointerException("Typ urządzenia nie może być pusty! Sprawdź konfigurator Excel.");
            }
            if (size == null) {
                throw new NullPointerException("Rozmiar nie może być pusty! Sprawdź konfigurator Excel.");
            }
            if (material == null) {
                throw new NullPointerException("Powłoka nie może być pusta! Sprawdź konfigurator Excel.");
            }
            if (deviceQuantity == null) {
                throw new NullPointerException("Ilość sztuk nie może być pusta! Sprawdź konfigurator Excel.");
            }
        }
    }
}

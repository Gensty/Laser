package pl.gensty.DevicePart;

import static pl.gensty.Utils.NumberUtils.isSingleCharNumber;

public abstract class AbstractPart {
    public final String numberEDT;
    public final String material;
    public final Integer thickness;
    public final Integer quantity;
    public final String description;

    public AbstractPart(Builder<?> builder) {
        this.numberEDT = builder.numberEDT;
        this.material = builder.material;
        this.thickness = builder.thickness;
        this.quantity = builder.quantity;
        this.description = builder.description;
    }

    public String getNumberEDT() {
        return numberEDT;
    }

    public String getMaterial() {
        return material;
    }

    public Integer getThickness() {
        return thickness;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return numberEDT + '-' +
                material + "-t" +
                isSingleCharNumber(thickness) + '-' +
                isSingleCharNumber(quantity) + "_" +
                description + ".DWG";
    }

    public static abstract class Builder<T extends Builder<T>> {
        private String numberEDT;
        private String material;
        private Integer thickness;
        private Integer quantity;
        private String description;

        public T numberEDT(String numberEDT) {
            this.numberEDT = numberEDT;
            return self();
        }

        public T material(String material) {
            this.material = material;
            return self();
        }

        public T thickness(Integer thickness) {
            this.thickness = thickness;
            return self();
        }

        public T quantity(Integer quantity) {
            this.quantity = quantity;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        protected abstract T self();

        public abstract AbstractPart build();

        protected void validate() {
            if (numberEDT == null) {
                throw new NullPointerException("Nr elementu nie może być pusty! Sprawdź konfigurator Excel.");
            }
            if (material == null) {
                throw new NullPointerException("Powłoka nie może być pusta! Sprawdź konfigurator Excel.");
            }
            if (thickness == null) {
                throw new NullPointerException("Grubość nie może być pusta! Sprawdź konfigurator Excel.");
            }
            if (quantity == null) {
                throw new NullPointerException("Ilość elementów nie może być pusta! Sprawdź konfigurator Excel.");
            }
            if (description == null) {
                throw new NullPointerException("Opis elementu nie może być pusty! Sprawdź konfigurator Excel.");
            }
        }
    }
}

package pl.gensty.Configuration;

public class ConfigNPK extends AbstractConfig {
    private final String feetType;
    private final String filling;
    private final Boolean isVentingSegment;
    private final Boolean isMaintenancePlatform;

    public ConfigNPK(Builder builder) {
        super(builder);
        this.feetType = builder.feetType;
        this.filling = builder.filling;
        this.isVentingSegment = builder.isVentingSegment;
        this.isMaintenancePlatform = builder.isMaintenancePlatform;
    }

    public String getFeetType() {
        return feetType;
    }

    public String getFilling() {
        return filling;
    }

    public Boolean isVentingSegment() {
        return isVentingSegment;
    }

    public Boolean isMaintenancePlatform() {
        return isMaintenancePlatform;
    }

    public static class Builder extends AbstractConfig.Builder<Builder> {
        private String feetType;
        private String filling;
        private Boolean isVentingSegment;
        private Boolean isMaintenancePlatform;

        public Builder feetType(String feetType) {
            this.feetType = feetType;
            return  this;
        }

        public Builder filling(String filling) {
            this.filling = filling;
            return  this;
        }

        public Builder isVentingSegment(Boolean isVentingSegment) {
            this.isVentingSegment = isVentingSegment;
            return  this;
        }

        public Builder isMaintenancePlatform(Boolean isMaintenancePlatform) {
            this.isMaintenancePlatform = isMaintenancePlatform;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ConfigNPK build() {
            validate();

            if (feetType == null) {
                throw new NullPointerException("Typ stopy nie może być pusty! Sprawdź konfigurator Excel.");
            }

            if (filling == null) {
                throw new NullPointerException("Rodzaj zasypu nie może być pusty! Sprawdź konfigurator Excel.");
            }

            if (isVentingSegment == null) {
                throw new NullPointerException("Musisz podać dane nt Segmentu odpowietrzającego! Sprawdź konfigurator Excel.");
            }

            if (isMaintenancePlatform == null) {
                throw new NullPointerException("Musisz podać dane nt podestu obsługowego! Sprawdź konfigurator Excel.");
            }

            return new ConfigNPK(this);
        }
    }
}

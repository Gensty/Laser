package pl.gensty.Configuration;

public class ConfigSPR extends AbstractConfig {
    private final String feetType;
    private final boolean isVentingSegment;
    private final boolean isMaintenancePlatform;

    public ConfigSPR(Builder builder) {
        super(builder);
        this.feetType = builder.feetType;
        this.isVentingSegment = builder.isVentingSegment;
        this.isMaintenancePlatform = builder.isMaintenancePlatform;
    }

    public String getFeetType() {
        return feetType;
    }

    public boolean isVentingSegment() {
        return isVentingSegment;
    }

    public boolean isMaintenancePlatform() {
        return isMaintenancePlatform;
    }

    public static class Builder extends AbstractConfig.Builder<Builder> {
        private String feetType;
        private Boolean isVentingSegment;
        private Boolean isMaintenancePlatform;

        public Builder feetType(String feetType) {
            this.feetType = feetType;
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
        public ConfigSPR build() {
            validate();

            if (feetType == null) {
                throw new NullPointerException("Typ stopy nie może być pusty! Sprawdź konfigurator Excel.");
            }
            return new ConfigSPR(this);
        }
    }
}

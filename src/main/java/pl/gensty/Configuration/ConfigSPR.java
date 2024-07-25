package pl.gensty.Configuration;

public class ConfigSPR extends AbstractConfig {
    private final String chainSupport;

    public ConfigSPR(Builder builder) {
        super(builder);
        this.chainSupport = builder.chainSupport;
    }

    public String getChainSupport() {
        return chainSupport;
    }

    public static class Builder extends AbstractConfig.Builder<Builder> {
        private String chainSupport;

        public Builder chainSupport(String feetType) {
            this.chainSupport = feetType;
            return  this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ConfigSPR build() {
            validate();

            if (chainSupport == null) {
                throw new NullPointerException("Prowadzenie łańcucha nie może być puste! Sprawdź konfigurator Excel.");
            }
            return new ConfigSPR(this);
        }
    }
}

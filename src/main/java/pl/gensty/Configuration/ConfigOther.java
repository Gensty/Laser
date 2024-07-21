package pl.gensty.Configuration;

public class ConfigOther extends AbstractConfig {
    private final String driveType;

    public ConfigOther(Builder builder) {
        super(builder);
        this.driveType = builder.driveType;
    }

    public String getDriveType() {
        return driveType;
    }

    public static class Builder extends AbstractConfig.Builder<Builder> {
        private String driveType;

        public Builder driveType(String driveType) {
            this.driveType = driveType;
            return  this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ConfigOther build() {
            validate();
            if (driveType == null) {
                throw new NullPointerException("Typ napędu nie może być pusty! Sprawdź konfigurator Excel.");
            }
            return new ConfigOther(this);
        }
    }
}

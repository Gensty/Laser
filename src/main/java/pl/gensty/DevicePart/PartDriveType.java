package pl.gensty.DevicePart;

public class PartDriveType extends AbstractPart {
    private final Boolean isElectric;
    private final Boolean isPneumatic;
    private final Boolean isManual;

    public PartDriveType(Builder builder) {
        super(builder);
        this.isElectric = builder.isElectric;
        this.isPneumatic = builder.isPneumatic;
        this.isManual = builder.isManual;
    }

    public Boolean isElectric() {
        return isElectric;
    }

    public Boolean isPneumatic() {
        return isPneumatic;
    }

    public Boolean isManual() {
        return isManual;
    }

    public static class Builder extends AbstractPart.Builder<Builder> {
        private Boolean isElectric;
        private Boolean isPneumatic;
        private Boolean isManual;

        public Builder isElectric(Boolean isElectric) {
            this.isElectric = isElectric;
            return this;
        }

        public Builder isPneumatic(Boolean isPneumatic) {
            this.isPneumatic = isPneumatic;
            return this;
        }

        public Builder isManual(Boolean isManual) {
            this.isManual = isManual;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public PartDriveType build() {
            validate();
            if (isElectric == null) {
                throw new NullPointerException("Konfiguracja elektryczna nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (isPneumatic == null) {
                throw new NullPointerException("Konfiguracja pneumatyczna nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (isManual == null) {
                throw new NullPointerException("Konfiguracja manualna nie może być pusta. Sprawdź konfigurator Excel.");
            }
            return new PartDriveType(this);
        }
    }
}

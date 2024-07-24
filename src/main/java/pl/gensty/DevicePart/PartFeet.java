package pl.gensty.DevicePart;

public class PartFeet extends AbstractPart {
    private final Boolean filling1Way;
    private final Boolean filling2Way;
    private final Boolean fillingNoWay;

    public PartFeet(Builder builder) {
        super(builder);
        this.filling1Way = builder.filling1Way;
        this.filling2Way = builder.filling2Way;
        this.fillingNoWay = builder.fillingNoWay;
    }

    public Boolean filling1Way() {
        return filling1Way;
    }

    public Boolean filling2Way() {
        return filling2Way;
    }

    public Boolean fillingNoWay() {
        return fillingNoWay;
    }

    public static class Builder extends AbstractPart.Builder<Builder> {
        private Boolean filling1Way;
        private Boolean filling2Way;
        private Boolean fillingNoWay;

        public Builder filling1Way(Boolean filling1Way) {
            this.filling1Way = filling1Way;
            return this;
        }

        public Builder filling2Way(Boolean filling2Way) {
            this.filling2Way = filling2Way;
            return this;
        }

        public Builder fillingNoWay(Boolean fillingNoWay) {
            this.fillingNoWay = fillingNoWay;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public PartFeet build() {
            validate();
            if (filling1Way == null) {
                throw new NullPointerException("Konfiguracja zasypu jednostronnego nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (filling2Way == null) {
                throw new NullPointerException("Konfiguracja zasypu dwustronnego nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (fillingNoWay == null) {
                throw new NullPointerException("Konfiguracja z brakiem zasypu nie może być pusta. Sprawdź konfigurator Excel.");
            }
            return new PartFeet(this);
        }
    }
}

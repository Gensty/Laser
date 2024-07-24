package pl.gensty.DevicePart;

public class PartSPR extends AbstractPart {
    private final Boolean rolls;
    private final Boolean upperDeck;
    private final Boolean transportingUpperDeck;
    private final Boolean guideBar;

    public PartSPR(Builder builder) {
        super(builder);
        this.rolls = builder.rolls;
        this.upperDeck = builder.upperDeck;
        this.transportingUpperDeck = builder.transportingUpperDeck;
        this.guideBar = builder.guideBar;
    }

    public Boolean rolls() {
        return rolls;
    }

    public Boolean uppedDeck() {
        return upperDeck;
    }

    public Boolean transportingUpperDeck() {
        return transportingUpperDeck;
    }

    public Boolean guideBar() {
        return guideBar;
    }

    public static class Builder extends AbstractPart.Builder<Builder> {
        private Boolean rolls;
        private Boolean upperDeck;
        private Boolean transportingUpperDeck;
        private Boolean guideBar;

        public Builder rolls(Boolean rolls) {
            this.rolls = rolls;
            return this;
        }

        public Builder upperDeck(Boolean upperDeck) {
            this.upperDeck = upperDeck;
            return this;
        }

        public Builder transportingUpperDeck(Boolean transportingUpperDeck) {
            this.transportingUpperDeck = transportingUpperDeck;
            return this;
        }

        public Builder guideBar(Boolean guideBar) {
            this.guideBar = guideBar;
            return this;
        }

        @Override
        public Builder self() {
            return this;
        }

        @Override
        public PartSPR build() {
            validate();
            if (rolls == null) {
                throw new NullPointerException("Konfiguracja z rolkami nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (upperDeck == null) {
                throw new NullPointerException("Konfiguracja z górnym dnem nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (transportingUpperDeck == null) {
                throw new NullPointerException("Konfiguracja z górnym dnem transportowym nie może być pusta. Sprawdź konfigurator Excel.");
            }
            if (guideBar == null) {
                throw new NullPointerException("Konfiguracja z listwą prowadzącą nie może być pusta. Sprawdź konfigurator Excel.");
            }
            return new PartSPR(this);
        }
    }
}

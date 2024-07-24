package pl.gensty.DevicePart;

public class Part extends AbstractPart {
    public Part(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbstractPart.Builder<Builder> {
        @Override
        public Builder self() {
            return this;
        }

        @Override
        public Part build() {
            validate();
            return new Part(this);
        }
    }
}

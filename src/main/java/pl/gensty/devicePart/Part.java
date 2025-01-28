package pl.gensty.devicePart;

public class Part extends AbstractPart {
    protected Part(
            String numberEDT,
            String material,
            Integer thickness,
            Integer quantity,
            String description
    ) {
        super(numberEDT, material, thickness, quantity, description);
    }

    public static PartBuilder builder() {
        return new PartBuilder();
    }

    public static class PartBuilder {
        private String numberEDT;
        private String material;
        private Integer thickness;
        private Integer quantity;
        private String description;

        public PartBuilder numberEDT(String numberEDT) {
            this.numberEDT = numberEDT;
            return this;
        }

        public PartBuilder material(String material) {
            this.material = material;
            return this;
        }

        public PartBuilder thickness(Integer thickness) {
            this.thickness = thickness;
            return this;
        }

        public PartBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public PartBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Part build() {
            return new Part(
                    numberEDT,
                    material,
                    thickness,
                    quantity,
                    description
            );
        }
    }
}

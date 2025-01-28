package pl.gensty.devicePart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static pl.gensty.utils.ExcelReader.isSingleCharNumber;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class AbstractPart {
    private final String numberEDT;
    private final String material;
    private final Integer thickness;
    private final Integer quantity;
    private final String description;

    @Override
    public String toString() {
        String extension = "PU".equals(material) ? ".DXF" : ".DWG";

        return numberEDT + '-' +
                material + "-t" +
                isSingleCharNumber(thickness) + '-' +
                isSingleCharNumber(quantity) + "_" +
                description + extension;
    }
}

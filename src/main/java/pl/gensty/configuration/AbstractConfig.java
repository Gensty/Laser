package pl.gensty.configuration;

import lombok.*;
import pl.gensty.enums.MaterialType;
import pl.gensty.enums.Module;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractConfig {
    private final String order;
    private final String type;
    private final String size;
    private final String material;
    private final Integer deviceQuantity;

    public abstract List<Module> getModules();

    public String setFolderName() {
        String materialFolder;
        if (MaterialType.DX51D.toString().equals(material)) {
            materialFolder = "DX51D+S235";
        } else {
            materialFolder = material;
        }
        return "ZL_" + order + " - " + size + "module" + materialFolder + " (x" + "quantity" + ")";
    }

    public String setFolderName(MaterialType materialType) {
        return "ZL_" + order + " - " + size + "module" + materialType.toString() + " (x" + "quantity" + ")";
    }
}

package pl.gensty.configuration;

import lombok.*;
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
}

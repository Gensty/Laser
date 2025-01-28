package pl.gensty.devicePart.strategy;

import pl.gensty.devicePart.AbstractPart;
import pl.gensty.enums.Parameter;

import java.util.Map;

public class FactoryPart {
    public static AbstractPart createPart(Map<Parameter, Object> params) {
        return PartBuilderFactory.getStrategy("Part").buildPart(params);
    }
}

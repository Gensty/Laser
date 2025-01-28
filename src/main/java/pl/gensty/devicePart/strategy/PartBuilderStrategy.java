package pl.gensty.devicePart.strategy;

import pl.gensty.devicePart.AbstractPart;
import pl.gensty.enums.Parameter;

import java.util.Map;

public interface PartBuilderStrategy {
    AbstractPart buildPart(Map<Parameter, Object> params);
}

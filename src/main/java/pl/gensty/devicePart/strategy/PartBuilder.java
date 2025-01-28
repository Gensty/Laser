package pl.gensty.devicePart.strategy;

import pl.gensty.devicePart.AbstractPart;
import pl.gensty.devicePart.Part;
import pl.gensty.enums.Parameter;

import java.util.Map;

public class PartBuilder implements PartBuilderStrategy {
    @Override
    public AbstractPart buildPart(Map<Parameter, Object> params) {
        //TODO: zaimplementować Part po readPart
        return Part.builder()
                .numberEDT(params.get(Parameter.NUMBER_EDT).toString())
                .material(params.get(Parameter.MATERIAL).toString())
                .thickness(Integer.parseInt(params.get(Parameter.THICKNESS).toString()))
                .quantity(Integer.parseInt(params.get(Parameter.QUANTITY).toString()))
                .description(params.get(Parameter.DESCRIPTION).toString())
                .build();
    }
}

package pl.gensty.devicePart.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PartBuilderFactory {
    private static final Map<String, PartBuilderStrategy> strategies = new HashMap<>();

    static {
        strategies.put("Part", new PartBuilder());
    }
    public static PartBuilderStrategy getStrategy(String partType) {
        return Optional.ofNullable(strategies.get(partType))
                .orElseThrow(() -> new IllegalArgumentException("Nieobsługiwany typ części"));
    }


}
    
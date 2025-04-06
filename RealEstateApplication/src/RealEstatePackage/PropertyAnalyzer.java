package RealEstatePackage;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;

public class PropertyAnalyzer {
    private final PropertyManager manager;

    public PropertyAnalyzer(PropertyManager manager) {
        this.manager = manager;
    }

    public void logProperties() {
        Consumer<Property> propertyLogger = property ->
                System.out.println("LOG: [Address: " + property.getFullAddress() +
                        ", Price: $" + property.getPrice() +
                        ", Status: " + property.getStatus() + "]");

        System.out.println("\nLogging all properties with Consumer:");
        manager.getProperties().forEach(propertyLogger);
    }

    public Property getDefaultPropertyIfEmpty() {
        try {
            return manager.getProperties().getFirst();
        } catch (NoSuchElementException e) {
            return ((Supplier<Property>) () -> new ResidentialProperty("Default Address", 100000, 2)).get();
        }
    }

    public void printFormattedProperties() {
        Function<Property, String> propertyFormatter = property ->
                "Formatted: " + property.getFullAddress() + " - $" + property.getPrice();

        System.out.println("\nFormatted properties using Function:");
        manager.getProperties().stream()
                .map(propertyFormatter)
                .forEach(System.out::println);
    }

    public long countProperties() {
        return manager.getProperties().stream().count();
    }

    // Concept: Stream Terminal Operation (findAny) - Find any property
    public Optional<Property> findAnyProperty() {
        return manager.getProperties().stream().findAny();
    }

    // Concept: Stream Terminal Operation (findFirst) - Find the first property
    public Optional<Property> findFirstProperty() {
        return manager.getProperties().stream().findFirst();
    }
}
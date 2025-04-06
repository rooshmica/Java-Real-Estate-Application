package RealEstatePackage;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.stream.Collectors; // Import for Collectors

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

    public Optional<Property> findAnyProperty() {
        return manager.getProperties().stream().findAny();
    }

    public Optional<Property> findFirstProperty() {
        return manager.getProperties().stream().findFirst();
    }

    public boolean areAllPropertiesSold() {
        return manager.getProperties().stream()
                .allMatch(property -> property.getStatus() == PropertyStatus.SOLD);
    }

    public boolean isAnyPropertyAvailable() {
        return manager.getProperties().stream()
                .anyMatch(property -> property.getStatus() == PropertyStatus.AVAILABLE);
    }

    public boolean areNoPropertiesBelowPrice(double price) {
        return manager.getProperties().stream()
                .noneMatch(property -> property.getPrice() < price);
    }

    // Concept: Stream Terminal Operation (collect with Collectors.toMap) - Map properties by address
    public Map<String, Property> mapPropertiesByAddress() {
        return manager.getProperties().stream()
                .collect(Collectors.toMap(
                        Property::getFullAddress,
                        property -> property,
                        (p1, p2) -> p1 // Handle duplicate addresses by keeping the first
                ));
    }

    // Concept: Stream Terminal Operation (collect with Collectors.partitioningBy) - Partition by type
    public Map<Boolean, List<Property>> partitionPropertiesByType() {
        return manager.getProperties().stream()
                .collect(Collectors.partitioningBy(
                        property -> property instanceof ResidentialProperty
                ));
    }
}
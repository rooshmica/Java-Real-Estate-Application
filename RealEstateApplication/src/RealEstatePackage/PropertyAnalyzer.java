package RealEstatePackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
            return new ResidentialProperty("Default Address", 100000, 2);
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
        return manager.getProperties().size();
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

    public Map<String, Property> mapPropertiesByAddress() {
        return manager.getProperties().stream()
                .collect(Collectors.toMap(
                        Property::getFullAddress,
                        property -> property,
                        (p1, p2) -> p1
                ));
    }

    public Map<Boolean, List<Property>> partitionPropertiesByType() {
        return manager.getProperties().stream()
                .collect(Collectors.partitioningBy(
                        property -> property instanceof ResidentialProperty residential
                ));
    }

    public List<String> getLimitedDistinctAddressesByPrice(int limit) {
        return manager.getProperties().stream()
                .distinct()
                .sorted((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()))
                .limit(limit)
                .map(Property::getFullAddress)
                .collect(Collectors.toList());
    }

    public List<Property> filterPropertiesByCondition(double minPrice, PropertyStatus status) {
        Predicate<Property> condition = property ->
                property.getPrice() >= minPrice && property.getStatus() == status;
        return manager.getProperties().stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    public List<Property> sortPropertiesByPrice() {
        return manager.getProperties().stream()
                .sorted(Comparator.comparing(Property::getPrice))
                .collect(Collectors.toList());
    }

    public double calculateTotalPriceConcurrently() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            List<Callable<Double>> tasks = List.of(
                    () -> manager.getProperties().stream()
                            .filter(p -> p instanceof ResidentialProperty)
                            .mapToDouble(Property::getPrice)
                            .sum(),
                    () -> manager.getProperties().stream()
                            .filter(p -> p instanceof CommercialProperty)
                            .mapToDouble(Property::getPrice)
                            .sum()
            );
            List<Future<Double>> results = executor.invokeAll(tasks);
            return results.stream()
                    .mapToDouble(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sum();
        } finally {
            executor.shutdown();
        }
    }

    public void savePropertiesToFile(String filePath) throws IOException {
        List<String> propertyDetails = manager.getProperties().stream()
                .map(Property::getFullDetails)
                .toList();
        Files.write(Path.of(filePath), propertyDetails);
    }

    public void displayPropertiesInLocale(Locale locale) {
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        System.out.println("\n" + messages.getString("display.header") + " " + locale);
        manager.getProperties().forEach(property -> {
            System.out.println(messages.getString("display.address") + ": " + property.getFormattedAddress(locale) +
                    ", " + messages.getString("display.price") + ": " + property.getFormattedPrice(locale) +
                    ", " + messages.getString("display.status") + ": " + property.getLocalizedStatus(locale) +
                    ", " + messages.getString("display.added") + ": " + property.getFormattedAddedDate(locale));
        });
    }

    public Optional<Property> findCheapestProperty() {
        return manager.getProperties().stream()
                .min(Comparator.comparing(Property::getPrice));
    }

    public Optional<Property> findMostExpensiveProperty() {
        return manager.getProperties().stream()
                .max(Comparator.comparing(Property::getPrice));
    }

    public Map<PropertyStatus, List<Property>> groupPropertiesByStatus() {
        return manager.getProperties().stream()
                .collect(Collectors.groupingBy(Property::getStatus));
    }

    // Java 22 Unnamed Variable: Use _ when the variable is not referenced
    public long countPropertiesWithUnnamedVariable() {
        return manager.getProperties().stream()
                .filter(_ -> true)
                .count();
    }

    // Java 22 Unnamed Variable in a loop
    public void printFormattedPropertiesWithUnnamedVariable() {
        System.out.println("\nPrinting formatted properties using unnamed variable in loop:");
        for (Property _ : manager.getProperties()) {
            System.out.println("Processing a property...");
        }
    }

    // Java 22 Unnamed Pattern in instanceof
    public void logPropertyType(Property property) {
        System.out.println("\nLogging property type using unnamed pattern in instanceof:");
        if (property instanceof ResidentialProperty _) {
            System.out.println("This is a residential property.");
        } else if (property instanceof CommercialProperty _) {
            System.out.println("This is a commercial property.");
        } else {
            System.out.println("Unknown property type.");
        }
    }

    // Java 22 Unnamed Pattern in switch
    public String describePropertyType(Property property) {
        return switch (property) {
            case ResidentialProperty _ -> "Residential Property";
            case CommercialProperty _ -> "Commercial Property";
            default -> "Unknown Property Type";
        };
    }

    // Supplier: Lazily provide a default property
    public Property getDefaultPropertyFromSupplier(Supplier<Property> defaultPropertySupplier) {
        return manager.getProperties().isEmpty() ? defaultPropertySupplier.get() : manager.getProperties().getFirst();
    }
}
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;
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
                        property -> property instanceof ResidentialProperty
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

    // Concept: NIO2 - Save property details to a file
    public void savePropertiesToFile(String filePath) throws IOException {
        List<String> propertyDetails = manager.getProperties().stream()
                .map(Property::getFullDetails)
                .toList();
        Files.write(Path.of(filePath), propertyDetails);
    }

    // Concept: Localization - Display properties in a specific locale, including formatted date
    public void displayPropertiesInLocale(Locale locale) {
        System.out.println("\nDisplaying properties in locale: " + locale);
        manager.getProperties().forEach(property -> {
            String formattedPrice = property.getFormattedPrice(locale);
            String formattedDate = property.getFormattedAddedDate(locale);
            System.out.println("Address: " + property.getFullAddress() +
                    ", Price: " + formattedPrice +
                    ", Status: " + property.getStatus() +
                    ", Added: " + formattedDate);
        });
    }
}
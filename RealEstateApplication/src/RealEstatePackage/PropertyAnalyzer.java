package RealEstatePackage;

import java.util.NoSuchElementException; // Import for NoSuchElementException
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PropertyAnalyzer {
    private final PropertyManager manager;

    public PropertyAnalyzer(PropertyManager manager) {
        this.manager = manager;
    }

    // Concept: Lambda (Consumer) - Use Consumer to log property details in a custom format
    public void logProperties() {
        Consumer<Property> propertyLogger = property ->
                System.out.println("LOG: [Address: " + property.getFullAddress() +
                        ", Price: $" + property.getPrice() +
                        ", Status: " + property.getStatus() + "]");

        System.out.println("\nLogging all properties with Consumer:");
        manager.getProperties().forEach(propertyLogger);
    }

    // Concept: Lambda (Supplier) - Generate a default property if none match criteria
    public Property getDefaultPropertyIfEmpty() {
        try {
            return manager.getProperties().getFirst();
        } catch (NoSuchElementException e) {
            return new ResidentialProperty("Default Address", 100000, 2);
        }
    }
}
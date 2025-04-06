package RealEstatePackage;

import java.util.function.Consumer;

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
}
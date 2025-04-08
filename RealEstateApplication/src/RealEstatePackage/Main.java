package RealEstatePackage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        PropertyManagement.printWelcomeMessage();

        PropertyManager manager = new PropertyManager();

        ResidentialProperty res1 = new ResidentialProperty("123 Main St", 250000, 3);
        CommercialProperty com1 = new CommercialProperty("456 Market Rd", 500000, "Retail");
        ResidentialProperty res2 = new ResidentialProperty("789 Pine St", 350000, 4);

        Runnable addProperties = () -> {
            System.out.println("\nAdded New properties:");
            manager.addProperty(res1);
            manager.addProperty(com1);
            manager.addProperty(res2);
        };

        Runnable listProperties = () -> {
            System.out.println("\nListed all properties:");
            manager.listAllProperties();
        };

        Runnable searchByAddress = () -> {
            System.out.println("\nSearch property by address '123 Main St':");
            Property foundProperty = manager.searchProperty("123 Main St");
            if (foundProperty != null) {
                System.out.println("Found property: " + foundProperty.getFullDetails());
            } else {
                System.out.println("Property not found.");
            }
        };

        Runnable searchByPrice = () -> {
            System.out.println("\nSearch properties by price range (200000, 400000):");
            List<Property> propertiesByPrice = manager.searchProperty(200000, 400000);
            propertiesByPrice.forEach(property -> System.out.println(property.getFullDetails()));

            System.out.println("\nSearch with invalid price range (-500000, 400000):");
            propertiesByPrice = manager.searchProperty(-500000, 400000);
            if (propertiesByPrice.isEmpty()) {
                System.out.println("No properties found.");
            }
        };

        Runnable updateProperty = () -> {
            System.out.println("\nUpdating price for '123 Main St' from 250000 to 275000:");
            manager.updatePropertyPrice("123 Main St", 275000);
            System.out.println("\nList the updated price of properties:");
            manager.listAllProperties();
        };

        Runnable updateStatus = () -> {
            System.out.println("\nUpdating status for '123 Main St' to SOLD:");
            manager.updatePropertyStatus("123 Main St", PropertyStatus.SOLD);
            System.out.println("\nList the updated properties:");
            manager.listAllProperties();
        };

        Runnable removeProperty = () -> {
            System.out.println("\nRemove property by address '456 Market Rd':");
            manager.removeProperty("456 Market Rd");
            System.out.println("\nList all properties after removal:");
            manager.listAllProperties();
        };

        PropertyAnalyzer analyzer = new PropertyAnalyzer(manager);
        Runnable logProperties = analyzer::logProperties;

        Runnable getDefaultProperty = () -> {
            System.out.println("\nGetting default property if list is empty:");
            Property defaultProperty = analyzer.getDefaultPropertyIfEmpty();
            System.out.println("Default or first property: " + defaultProperty.getFullDetails());
        };

        Runnable printFormattedProperties = analyzer::printFormattedProperties;

        Runnable analyzeProperties = () -> {
            System.out.println("\nAnalyzing properties with terminal operations:");
            System.out.println("Total properties: " + analyzer.countProperties());
            System.out.println("Any property: " +
                    analyzer.findAnyProperty().map(Property::getFullDetails).orElse("None"));
            System.out.println("First property: " +
                    analyzer.findFirstProperty().map(Property::getFullDetails).orElse("None"));
        };

        Runnable checkPropertiesStatus = () -> {
            System.out.println("\nChecking properties status with terminal operations:");
            System.out.println("Are all properties sold? " + analyzer.areAllPropertiesSold());
            System.out.println("Is any property available? " + analyzer.isAnyPropertyAvailable());
            System.out.println("Are no properties below $200000? " +
                    analyzer.areNoPropertiesBelowPrice(200000));
        };

        Runnable mapAndPartitionProperties = () -> {
            System.out.println("\nMapping and partitioning properties:");
            Map<String, Property> propertyMap = analyzer.mapPropertiesByAddress();
            System.out.println("Properties by address:");
            propertyMap.forEach((address, prop) ->
                    System.out.println(address + ": " + prop.getFullDetails()));

            Map<Boolean, List<Property>> partitioned = analyzer.partitionPropertiesByType();
            System.out.println("\nPartitioned by type (true=Residential, false=Commercial):");
            partitioned.forEach((isResidential, props) -> {
                System.out.println(isResidential ? "Residential:" : "Commercial:");
                props.forEach(prop -> System.out.println("  " + prop.getFullDetails()));
            });
        };

        Runnable getLimitedDistinctAddresses = () -> {
            System.out.println("\nGetting limited distinct addresses (limit 2):");
            List<String> addresses = analyzer.getLimitedDistinctAddressesByPrice(2);
            addresses.forEach(System.out::println);
        };

        Runnable sortPropertiesByPrice = () -> {
            System.out.println("\nSorting properties by price using Comparator.comparing():");
            List<Property> sortedProperties = analyzer.sortPropertiesByPrice();
            sortedProperties.forEach(property -> System.out.println(property.getFullDetails()));
        };

        Runnable calculateTotalPriceConcurrently = () -> {
            System.out.println("\nCalculating total price concurrently using ExecutorService:");
            try {
                double totalPrice = analyzer.calculateTotalPriceConcurrently();
                System.out.println("Total price: $" + totalPrice);
            } catch (Exception e) {
                System.out.println("Error during concurrent calculation: " + e.getMessage());
            }
        };

        Runnable savePropertiesToFileNIO2 = () -> {
            System.out.println("\nSaving properties to file using NIO2:");
            try {
                analyzer.savePropertiesToFile("properties.txt");
                System.out.println("Properties saved to properties.txt");
            } catch (IOException e) {
                System.out.println("Error saving properties: " + e.getMessage());
            }
        };

        Runnable displayPropertiesInLocale = () -> {
            System.out.println("\nDisplaying properties in US locale:");
            analyzer.displayPropertiesInLocale(Locale.US);
            System.out.println("\nDisplaying properties in French locale:");
            analyzer.displayPropertiesInLocale(Locale.FRANCE);
        };

        // Action: Find cheapest and most expensive properties using min() and max()
        Runnable findCheapestAndMostExpensive = () -> {
            System.out.println("\nFinding cheapest property using min():");
            analyzer.findCheapestProperty()
                    .ifPresentOrElse(
                            property -> System.out.println("Cheapest: " + property.getFullDetails()),
                            () -> System.out.println("No properties found.")
                    );

            System.out.println("\nFinding most expensive property using max():");
            analyzer.findMostExpensiveProperty()
                    .ifPresentOrElse(
                            property -> System.out.println("Most Expensive: " + property.getFullDetails()),
                            () -> System.out.println("No properties found.")
                    );
        };

        String[] actions = {
                "ADD_PROPERTY", "LIST_PROPERTY", "SEARCH_BY_ADDRESS", "SEARCH_BY_PRICE",
                "UPDATE_PROPERTY", "UPDATE_STATUS", "REMOVE_PROPERTY", "LOG_PROPERTIES",
                "GET_DEFAULT_PROPERTY", "PRINT_FORMATTED_PROPERTIES", "ANALYZE_PROPERTIES",
                "CHECK_PROPERTIES_STATUS", "MAP_AND_PARTITION_PROPERTIES",
                "GET_LIMITED_DISTINCT_ADDRESSES", "SORT_PROPERTIES_BY_PRICE",
                "CALCULATE_TOTAL_PRICE_CONCURRENTLY", "SAVE_PROPERTIES_TO_FILE_NIO2",
                "DISPLAY_PROPERTIES_IN_LOCALE", "FIND_CHEAPEST_AND_MOST_EXPENSIVE"
        };

        for (var action : actions) {
            try {
                switch (action) {
                    case "ADD_PROPERTY" -> addProperties.run();
                    case "LIST_PROPERTY" -> listProperties.run();
                    case "SEARCH_BY_ADDRESS" -> searchByAddress.run();
                    case "SEARCH_BY_PRICE" -> searchByPrice.run();
                    case "UPDATE_PROPERTY" -> updateProperty.run();
                    case "UPDATE_STATUS" -> updateStatus.run();
                    case "REMOVE_PROPERTY" -> removeProperty.run();
                    case "LOG_PROPERTIES" -> logProperties.run();
                    case "GET_DEFAULT_PROPERTY" -> getDefaultProperty.run();
                    case "PRINT_FORMATTED_PROPERTIES" -> printFormattedProperties.run();
                    case "ANALYZE_PROPERTIES" -> analyzeProperties.run();
                    case "CHECK_PROPERTIES_STATUS" -> checkPropertiesStatus.run();
                    case "MAP_AND_PARTITION_PROPERTIES" -> mapAndPartitionProperties.run();
                    case "GET_LIMITED_DISTINCT_ADDRESSES" -> getLimitedDistinctAddresses.run();
                    case "SORT_PROPERTIES_BY_PRICE" -> sortPropertiesByPrice.run();
                    case "CALCULATE_TOTAL_PRICE_CONCURRENTLY" -> calculateTotalPriceConcurrently.run();
                    case "SAVE_PROPERTIES_TO_FILE_NIO2" -> savePropertiesToFileNIO2.run();
                    case "DISPLAY_PROPERTIES_IN_LOCALE" -> displayPropertiesInLocale.run();
                    case "FIND_CHEAPEST_AND_MOST_EXPENSIVE" -> findCheapestAndMostExpensive.run();
                    default -> System.out.println("No action: " + action);
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Error due to exception: " + e.getMessage());
            }
        }
    }
}
package RealEstatePackage;

import java.util.List;

public class Main {
    public static void main(String[] args)
    {
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

        String[] actions = {"ADD_PROPERTY", "LIST_PROPERTY", "SEARCH_BY_ADDRESS", "SEARCH_BY_PRICE", "UPDATE_PROPERTY", "UPDATE_STATUS", "REMOVE_PROPERTY"};

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
                    default -> System.out.println("No action: " + action);
                }
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Error due to exception: " + e.getMessage());
            }
        }
    }
}

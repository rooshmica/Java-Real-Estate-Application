package RealEstatePackage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PropertyManager
{
    private List<Property> properties;

    public PropertyManager()
    {
        this.properties = new ArrayList<>();
    }

    public void addProperty(Property property)
    {
        try
        {
            if (property == null)
            {
                throw new NullPointerException("Property variable value cannot be null .");
            }
            properties.add(property);
            System.out.println("Added property: " + property.getFullDetails());
        }
        catch (NullPointerException e)
        {
            System.out.println("Error at method addProperty(): " + e.getMessage());
        }
    }
    public void listAllProperties()
    {
        try
        {
            if (properties.isEmpty())
            {
                throw new IllegalStateException("No properties available. Please add a new property.");
            }
            for (Property property : properties)
            {
                property.listProperty();
            }
        }
        catch (IllegalStateException e)
        {
            System.out.println("Error at method listAllProperties(): " + e.getMessage());
        }
    }

    public void updatePropertyPrice(String address, double newPrice)
    {
        try
        {
            if (address == null || address.isEmpty())
            {
                throw new IllegalArgumentException("Address cannot be null or empty. Please add a new address.");
            }
            for (Property property : properties)
            {
                if (property.getFullAddress().equalsIgnoreCase(address))
                {
                    property.updatePrice(newPrice);
                    return;
                }
            }
            throw new NullPointerException("Property not found: " + address);
        }
        catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Error at method updatePropertyPrice(): " + e.getMessage());
        }
    }

    public void updatePropertyStatus(String address, PropertyStatus newStatus) {
        try {
            if (address == null || address.isEmpty()) {
                throw new IllegalArgumentException("Address cannot be null or empty.");
            }
            for (Property property : properties) {
                if (property.getFullAddress().equalsIgnoreCase(address)) {
                    property.updateStatus(newStatus);
                    return;
                }
            }
            throw new NullPointerException("Property not found: " + address);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Error at method updatePropertyStatus(): " + e.getMessage());
        }
    }

    public Property searchProperty(String address)
    {
        try
        {
            if (address == null || address.isEmpty())
            {
                throw new IllegalArgumentException("Address cannot be null or empty. Please add a new address.");
            }
            for (Property property : properties)
            {
                if (property.getFullAddress().equalsIgnoreCase(address))
                {
                    return property;
                }
            }
            throw new NullPointerException("Property not found: " + address);
        }
        catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Error at method searchProperty(): " + e.getMessage());
            return null;
        }
    }

    public List<Property> searchProperty(double minPrice, double maxPrice) {
        List<Property> result = new ArrayList<>();
        try {
            if (minPrice < 0 || maxPrice < 0) {
                throw new IllegalArgumentException("Price values cannot be negative and it should be greater than zero.");
            }
            if (minPrice > maxPrice) {
                throw new IllegalArgumentException("Min price cannot be greater than max price.");
            }
            Predicate<Property> priceInRange = property -> property.getPrice() >= minPrice && property.getPrice() <= maxPrice;
            for (Property property : properties) {
                if (priceInRange.test(property)) {
                    result.add(property);
                }
            }
            if (result.isEmpty()) {
                throw new IllegalStateException("No properties found within the given price range.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error in searchProperty(): " + e.getMessage());
        }
        return result;
    }


    public void removeProperty(String address)
    {
        try
        {
            Property property = searchProperty(address);
            if (property == null)
            {
                throw new NullPointerException("Property not found: " + address);
            }
            properties.remove(property);
            System.out.println("Removed property: " + address);
        }
        catch (NullPointerException e)
        {
            System.out.println("Error at method removeProperty(): " + e.getMessage());
        }
    }

    // New method to return a defensive copy of the properties list
    public List<Property> getProperties() {
        return new ArrayList<>(properties);
    }

}

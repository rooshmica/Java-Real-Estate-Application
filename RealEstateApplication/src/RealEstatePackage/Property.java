package RealEstatePackage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract sealed class Property implements PropertyManagement permits ResidentialProperty, CommercialProperty {
    private PropertyDetails propertyDetails; // No longer final due to updatePrice
    private PropertyStatus status;

    public Property() {
        this("Unknown Address", 0.0); // this() calls the parameterized constructor
    }

    public Property(String address, double price) {
        this.propertyDetails = new PropertyDetails(address, price, PropertyStatus.AVAILABLE, LocalDateTime.now());
        this.status = PropertyStatus.AVAILABLE;
    }

    @Override
    public void listProperty() {
        System.out.println("Property listed: " + getFullDetails());
    }

    @Override
    public void updatePrice(double newPrice) {
        // Create a new PropertyDetails with the updated price
        this.propertyDetails = new PropertyDetails(
                propertyDetails.address(),
                newPrice,
                propertyDetails.status(),
                propertyDetails.addedDate()
        );
    }

    // Removed @Override since updateStatus is not in PropertyManagement
    public void updateStatus(PropertyStatus newStatus) {
        this.status = newStatus;
    }

    public String getFullDetails() {
        return "Address: " + propertyDetails.address() +
                ", Price: $" + propertyDetails.price() +
                ", Status: " + status +
                ", Added: " + propertyDetails.addedDate();
    }

    public String getFullAddress() {
        return propertyDetails.address();
    }

    public double getPrice() {
        return propertyDetails.price();
    }

    public PropertyStatus getStatus() {
        return status;
    }

    public String getFormattedPrice(Locale locale) {
        return String.format(locale, "%,.2f", propertyDetails.price());
    }

    // Concept: Localization - Format addedDate for a specific locale
    public String getFormattedAddedDate(Locale locale) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(java.time.format.FormatStyle.MEDIUM)
                .withLocale(locale);
        return propertyDetails.addedDate().format(formatter);
    }
}


final class ResidentialProperty extends Property
{

    private final int bedrooms;

    ResidentialProperty(String address, double price, int bedrooms) {
        super(address, price);
        this.bedrooms = bedrooms;
    }

    @Override
    public void listProperty()
    {
        try
        {
            if (super.getFullAddress() == null)
            {
                throw new NullPointerException("Residential Property address cannot be null");
            }
            System.out.println("Residential property at " + super.getFullAddress() + " with " + bedrooms + " bedrooms, priced at $" + super.getPrice() + " [" + super.getStatus() + "]");
        }
        catch (NullPointerException e)
        {
            System.out.println("Error at method listProperty(): " + e.getMessage());
        }
    }

     @Override
     public String getFullDetails()
     {
         return super.getFullDetails() + ", Bedrooms: " + bedrooms;
     }
}

final class CommercialProperty extends Property
 {

    private final String businessType;

    CommercialProperty(String address, double price, String businessType)
    {
        super(address, price);
        this.businessType = businessType;
    }

     @Override
     public void listProperty()
     {
         try
         {
             if (super.getFullAddress() == null)
             {
                 throw new NullPointerException("Commercial Property address cannot be null");
             }
             System.out.println("Commercial property for " + businessType + " business at " + super.getFullAddress() + ", priced at $" + super.getPrice() + " [" + super.getStatus() + "]");
         }
         catch (NullPointerException e)
         {
             System.out.println("Error at method listProperty(): " + e.getMessage());
         }
     }

     @Override
     public String getFullDetails()
     {
         return super.getFullDetails() + ", Business Type: " + businessType;
     }

 }

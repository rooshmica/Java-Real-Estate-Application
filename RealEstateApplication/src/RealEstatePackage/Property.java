package RealEstatePackage;

public sealed class Property implements PropertyManagement
        permits ResidentialProperty, CommercialProperty {

    private PropertyDetails PropertyDetails;

    public Property(String address, double price)
    {
        this.PropertyDetails = new PropertyDetails(address, price, PropertyStatus.AVAILABLE);
    }

    public Property()
    {
        this("Unknown Address", 0.0);
    }

    public void listProperty() {
        try {
            if (PropertyDetails.address() == null || PropertyDetails.address().isEmpty()) {
                throw new NullPointerException("Address cannot be null or empty");
            }
            System.out.println("Property at " + PropertyDetails.address() + " with price: $" + PropertyDetails.price() + " [" + PropertyDetails.status() + "]");
        } catch (NullPointerException e) {
            System.out.println("Error at method listProperty(): " + e.getMessage());
        }
    }

    public void updatePrice(double newPrice) {
        try {
            if (newPrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative. Please add amount more than 0");
            }
            this.PropertyDetails = new PropertyDetails(PropertyDetails.address(), newPrice, PropertyDetails.status());
            System.out.println("Updated price for address " + PropertyDetails.address() + " to $" + newPrice);
        } catch (IllegalArgumentException e) {
            System.out.println("Error at method updatePrice(): " + e.getMessage());
        }
    }

    public void updateStatus(PropertyStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.PropertyDetails = new PropertyDetails(
                PropertyDetails.address(),
                PropertyDetails.price(),
                newStatus
        );
        System.out.println("Status updated to " + newStatus + " for property at " + PropertyDetails.address());
    }


    public String getFullDetails() {
        return "Address: " + PropertyDetails.address() + ", Price: $" + PropertyDetails.price() + ", Status: " + PropertyDetails.status();
    }

    public String getFullAddress() {
        return PropertyDetails.address();
    }

    public double getPrice() {
        return PropertyDetails.price();
    }
    public PropertyStatus getStatus()
    {
        return PropertyDetails.status();
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

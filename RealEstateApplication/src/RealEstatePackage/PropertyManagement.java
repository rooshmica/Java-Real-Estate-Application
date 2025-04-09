package RealEstatePackage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface PropertyManagement
{

    default void validatePriceBeforeUpdate(double newPrice) {
        if (newPrice < 0) {
            logPriceValidation(newPrice);
            throw new IllegalArgumentException("Price cannot be negative: " + newPrice);
        }
        logPriceValidation(newPrice);
    }

    private void logPriceValidation(double price) {
        System.out.println("Validating price: $" + price);
    }
    
    void listProperty();

    void updatePrice(double newPrice);

    static void printWelcomeMessage()
    {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        System.out.println("\nWelcome to the Real Estate Management System!!!");
        System.out.println("Today's Date: " + currentDate.format(formatter));
    }
}
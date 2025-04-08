package RealEstatePackage;

import java.time.LocalDateTime;

public record PropertyDetails(String address, double price, PropertyStatus status, LocalDateTime addedDate) {
    // Canonical constructor to set a default value for addedDate if null
    public PropertyDetails {
        if (addedDate == null) {
            addedDate = LocalDateTime.now();
        }
    }
}
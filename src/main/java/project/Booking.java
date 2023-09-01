package project;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private Person person;
    private Vehicle vehicle;
    private int price;
    private int discount;
    private int moneySaved;
    private UUID bookingReference;
    private DatesHandler dateHandler = new DatesHandler();
    private DiscountHandler discountHandler = new DiscountHandler();
    private IUUIDGenerator UUIDClass;

    public Booking(Object UUIDType) throws FileNotFoundException {
        discountHandler.setUpDiscounts("discountFile");
        if (UUIDType.equals(new RandomUUIDGenerator())) {
            UUIDClass = new RandomUUIDGenerator();
        } else {
            UUIDClass = new ConstantUUIDGenerator();
        }
    }

    public Booking(LocalDate startDate, LocalDate endDate, Person person, Vehicle vehicle, String discountCode, IUUIDGenerator UUIDType) {
        setDates(startDate, endDate);
        UUIDClass = UUIDType;
        this.discount = discountHandler.getDiscountValue(discountCode);
        this.person = person;
        this.vehicle = vehicle;
        this.bookingReference = UUIDClass.getRandomeUUID();
        this.price = calculatePrice(vehicle, this.discount);
        this.moneySaved = calculateMoneySaved(vehicle, this.discount);

    }

    private void setDates(LocalDate startDate, LocalDate endDate) {
        dateHandler.checkIfValidDates(startDate, endDate);
        this.endDate = endDate;
        this.startDate = startDate;
    }

    private int calculatePrice(Vehicle vehicle, int discount) {
        int tempPrice = vehicle.getPricePerDay() * dateHandler.getDurationOfBooking(startDate, endDate);
        if (discount != 0.0) {
            return tempPrice - (tempPrice * discount) / 100;
        }
        return tempPrice;
    }

    private int calculateMoneySaved(Vehicle vehicle, int discount) {
        int temporaryPrice = vehicle.getPricePerDay() * dateHandler.getDurationOfBooking(startDate, endDate);
        if (discount != 0.0) {
            return (temporaryPrice * discount) / 100;
        }
        return 0;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Person getPerson() {
        return this.person;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public UUID getBookingReference() {
        return this.bookingReference;
    }

    public double getPrice() {
        return this.price;
    }

    public double getMoneySaved() {
        return this.moneySaved;
    }

    public double getDiscount() {
        return this.discount;
    }

    public String getBookingConfirmationText() {
        return this.person.getFirstName() + " " + this.person.getLastName()
                + ", we are pleased to inform you that your reservation has been received and confirmed." +
                "\n" + "VehicleBooking are looking forward to see you!"
                + "\n" + "\n" + "------ BOOKINGINFORMATION ------"
                + "\n" + "Booking reference: " + bookingReference
                + "\n" + "Start date: " + startDate
                + "\n" + "End date: " + endDate
                + "\n" + "Vehicle: " + vehicle.getVehicleName()
                + "\n" + "\n" + "-- Price details -- "
                + "\n" + getPriceForVechicleText();
    }

    public String getPriceForVechicleText() {
        if (discount != 0) {
            return "Total price: " + this.price + "\n"
                    + "Discount: " + this.discount + "%" + "\n"
                    + "Money saved: " + this.moneySaved;
        }
        return "Total price: " + this.price + "\n";
    }

    public String getVehicleDiscountText(String discountCode) {
        discount = discountHandler.getDiscountValue(discountCode);
        if (discount != 0) {
            return "Discount: " + this.discount + "%" + "\n";
        }
        return "";
    }

    

    @Override
    public String toString() {
        return bookingReference + ";" + person + ";" + startDate + ";" + endDate
                + ";" + vehicle + ";" + price + ";" + discount;
    }

}

package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DiscountHandler implements IFilePath {
    private static LinkedHashMap<String, Integer> discounts = new LinkedHashMap<>();

    public DiscountHandler() {
    }

    public void setUpDiscounts(String filename) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(getSelectedFile(filename))) {
            while (scanner.hasNext()) {
                String[] discount = scanner.nextLine().split(";");
                discounts.put(discount[0], Integer.parseInt(discount[1]));
            }
            scanner.close();
        }

        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find the file");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("The scanner fail");
        }
    }

    public void writeDiscountsToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(getSelectedFile(filename))) {
            String output = "";
            for (String key : discounts.keySet()) {
                output += key + ";" + String.valueOf(discounts.get(key)) + "\n";
            }
            writer.append(output);
            writer.close();
        } catch (IOException error) {
            throw new IOException("IO-Exception error");
        }
    }

    public void addDiscount(String discountName, int discountValue, String filename) throws FileNotFoundException, IOException {
        checkIfDiscountInputIsValid(discountName, discountValue);
        discounts.put(discountName, discountValue);
        writeDiscountsToFile(filename);
    }

    public void removeDiscount(String discountName, String filename) throws FileNotFoundException, IOException {
        if (discountName.equals("")) {
            throw new IllegalArgumentException("Dicount code cant be an empty string");
        }
        checkIfValidDiscount(discountName);
        discounts.remove(discountName);
        writeDiscountsToFile(filename);
    }

    public int getDiscountValue(String discountCode) {
        if (discountCode == (null)) {
            return 0;
        }
        if (discountCode == ("")) {
            return 0;
        }
        checkIfValidDiscount(discountCode);
        return discounts.get(discountCode);
    }

    public LinkedHashMap<String, Integer> returnDiscounts(String filename) throws FileNotFoundException {
        setUpDiscounts("discountFileTest");
        return DiscountHandler.discounts;
    }

    public void checkIfValidDiscount(String discountCode) {
        if (discountCode.length() > 20) {
            throw new IllegalArgumentException("The discount code contains to manye characthers.");
        }
        if (!(discounts.containsKey(discountCode))) {
            throw new IllegalArgumentException("Not a valid discount code");
        }
    }

    private void checkIfDiscountInputIsValid(String discountName, int discountValue) {
        if (discounts.containsKey(discountName)) {
            throw new IllegalArgumentException("The discount code already exists");
        }
        if (discountName.equals("")) {
            throw new IllegalArgumentException("Disount name input field must be filled");
        }
    }

    public String getVehicleDiscountText(String discountCode) {
        int discount = getDiscountValue(discountCode);
        if (discount != 0) {
            return "Discount: " + discount + "%" + "\n";
        }
        return "";
    }

    @Override
    public File getSelectedFile(String filename) {
        return new File(DiscountHandler.class.getResource("discounts/").getFile() + filename + ".txt");
    }

    public Path getDiscountFilPath(String filename) throws IOException {
        Path path = new File(getClass().getResource("discounts").getFile() + filename + ".txt").toPath();
        return path;
    }

    
}

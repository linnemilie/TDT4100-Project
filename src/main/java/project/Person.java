package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String age;
    private String email;

    public Person(String firstName, String lastName, String phoneNumber, String age, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNumber(phoneNumber);
        setAge(age);
        setEmail(email);
    }

    private void setFirstName(String firstName) {
        checkIfValidName(firstName);
        this.firstName = firstName.trim();
    }

    private void setLastName(String lastName) {
        checkIfValidName(lastName);
        this.lastName = lastName.trim();
    }

    private void setPhoneNumber(String phoneNumber) {
        checkIfValidPhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber.replaceAll("\\s+", "");
    }

    private void setAge(String age) {
        checkIfValidAge(age);
        this.age = age.replaceAll("\\s+", "");
    }

    private void setEmail(String email) {
        checkIfValidEmail(email);
        this.email = email.replaceAll("\\s+", "");
    }

    private void checkIfValidName(String name) {
        checkIfInputIsEmpty(name);
        String strippedName = name.replaceAll("\\s+", "");
        if ((strippedName.length() > 30) || (strippedName.length() < 2)) {
            throw new IllegalArgumentException("Neither first name nor last name can be longer than 30 characters, or less than two characters.");
        }
        if (strippedName.contains("-")) {
            String nameWithoutDash = strippedName.replaceAll("-", "");
            if (!(checkIfOnlyLetters(nameWithoutDash))) {
                throw new IllegalArgumentException("Your name can only contain alfabetic characters in addition to dash.");
            }
        } else {
            if (!(checkIfOnlyLetters(strippedName))) {
                throw new IllegalArgumentException("Your name can only contain alfabetic characters.");
            }
        }
    }

    private void checkIfValidAge(String age) {
        String strippedAge = age.replaceAll("\\s+", "");
        checkIfInputIsEmpty(strippedAge);
        checkIfInputIsOnlyDigits(strippedAge);
        if (strippedAge.length() > 3) {
            throw new IllegalArgumentException("Invalid age.");
        }
        int ageInt = Integer.parseInt(strippedAge);
        if (ageInt < 18) {
            throw new IllegalArgumentException("You must be over 18 to book a vehicles.");
        }
    }

    private void checkIfValidPhoneNumber(String number) {
        checkIfInputIsEmpty(number);
        String numbWithoutSpace = number.replaceAll("\\s", "");
        if ((!(numbWithoutSpace.matches("[+47]{3}[9]{1}[0-9]{7}")) && (!(numbWithoutSpace.matches("[+47]{3}[4]{1}[0-9]{7}"))))) {
            throw new IllegalArgumentException("Invalid phone number. The number must be a Norwegian telephone number that starts with +47 and has eight numbers.");
        }
    }

    private void checkIfValidEmail(String email) {
        checkIfInputIsEmpty(email);
        String emailadress = email.replaceAll("\\s+", "");
        try {
            String[] partsOfEmail = emailadress.split("@");
            String username = partsOfEmail[0];
            String domain = partsOfEmail[1];
            checkForSpecialCharacters(username);
            checkForSpecialCharacters(domain);
            char firstChar = emailadress.charAt(0);

            if (partsOfEmail.length != 2) {
                throw new IllegalArgumentException("Your email must contain exactly one @");
            }
            if (emailadress.length() != email.length()) {
                throw new IllegalArgumentException("Your email can't contain any spaces");
            }

            String[] usernameParts = username.split("\\.");
            if (usernameParts.length > 2) {
                throw new IllegalArgumentException("The part before @ can't contain more than two periods. ");
            }

            if (!(Character.isAlphabetic(firstChar))) {
                throw new IllegalArgumentException("First character in your email has to be in the english alphabet");
            }

            if (username.length() >= 30 || username.length() < 2) {
                throw new IllegalArgumentException("The part before @ in your email, can't be longer than 30 characters or or less than two characters.");
            }
            String[] domainSplitted = domain.split("\\.");
            if ((!(checkIfOnlyLetters(domainSplitted[0]))) || (domainSplitted[0].length() >= 20)) {
                throw new IllegalArgumentException("The first domain in your email must only contain letters, and can't be longer than 20 characters");
            }
            if ((!(checkIfOnlyLetters(domainSplitted[1]))) || (domainSplitted[1].length() >= 10)) {
                throw new IllegalArgumentException("The last domain in your email must only contain letters, and can't be longer than 10 characters");
            }
        } catch (ArrayIndexOutOfBoundsException error) {
            throw new IllegalArgumentException("Wrong format on mail.");
        }
    }

    private void checkForSpecialCharacters(String str) {
        String specialChar = "!@#$%&*,;:«&^()+=|<>?{}\\[]~";
        String stringSplitted[] = str.split("");
        for (int i = 0; i < str.length(); i++) {
            if (specialChar.contains(stringSplitted[i])) {
                throw new IllegalArgumentException("Your email can't contain any special characters.");
            }
        }
    }

    // Hentet fra: https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters, av: adarshr
    private boolean checkIfOnlyLetters(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private void checkIfInputIsEmpty(String str) {
        if (str.equals("")) {
            throw new IllegalArgumentException("Inputfields marked with * must be filled.");
        }
    }

    private void checkIfInputIsOnlyDigits(String age) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(age);
        if (!(matcher.matches())) {
            throw new IllegalArgumentException("Please type in your age with digits.");
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getAge() {
        return this.age;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return firstName + ";" + lastName + ";" + phoneNumber + ";" + email;
    }

}

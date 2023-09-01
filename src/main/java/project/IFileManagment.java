package project;

import java.io.IOException;
import java.util.ArrayList;

public interface IFileManagment {

    ArrayList<String> readFromFile(String filename) throws IOException;

    void writeToFile(String filename, Booking booking) throws IOException;

}
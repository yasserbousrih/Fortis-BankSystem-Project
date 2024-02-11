package bus;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    // Serialize and deserialize Customer objects
    public static void serializeCustomers(ArrayList<Customer> customers) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank.ser"))) {
            oos.writeObject(customers);
        }
    }

    public static ArrayList<Customer> deSerializeCustomers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank.ser"))) {
            return (ArrayList<Customer>) ois.readObject();
        }
    }

    // Serialize and deserialize Account objects
    public static void serializeAccounts(ArrayList<Account> accounts) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank.ser"))) {
            oos.writeObject(accounts);
        }
    }

    public static ArrayList<Account> deSerializeAccounts() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank.ser"))) {
            return (ArrayList<Account>) ois.readObject();
        }
    }
}



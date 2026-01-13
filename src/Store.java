import java.io.*;
import java.util.*;

public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, User> users;
    private Map<UUID, Wallet> wallets;

    public Store() {
        this.users = new HashMap<>();
        this.wallets = new HashMap<>();
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        wallets.put(user.getWalletId(), new Wallet());
    }

    public Wallet getWallet(UUID walletId) {
        return wallets.get(walletId);
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("store.bin"))) {
            oos.writeObject(this);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data.");
            e.printStackTrace();
        }
    }

    public static Store loadFromFile() {
        File file = new File("store.bin");

        if (!file.exists()) {
            System.out.println("store.bin not found. Creating a new file...");
            Store newStore = new Store();
            newStore.saveToFile();
            return newStore;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("store.bin"))) {
            Store store = (Store) ois.readObject();
            System.out.println("Data loaded successfully.");
            return store;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data.");
            e.printStackTrace();
            return new Store();
        }
    }
}

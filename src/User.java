import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private UUID walletId;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.walletId = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UUID getWalletId() {
        return walletId;
    }
}

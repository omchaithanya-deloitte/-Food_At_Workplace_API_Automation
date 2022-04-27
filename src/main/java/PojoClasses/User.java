package PojoClasses;

public class User {
    private String email;
    private String password;
    private boolean isAdmin;
    private boolean admin;

    public User() {
    }

    public User(String email, String password, boolean isAdmin, boolean admin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

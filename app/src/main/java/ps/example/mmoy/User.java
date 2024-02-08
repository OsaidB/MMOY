package ps.example.mmoy;
public class User {
    private String email;
    private String dateOfBirth;
    private String phone;
    private String password;
    private String gender;

    private boolean remembered;

    public User(String email, String dateOfBirth, String phone, String password, String gender) {
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.password = password;
        this.gender = gender;
    }

    public boolean isRemembered() {
        return remembered;
    }

    public void setRemembered(boolean remembered) {
        this.remembered = remembered;
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    // Setter methods (if needed)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

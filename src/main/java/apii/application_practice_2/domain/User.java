package apii.application_practice_2.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @Column(unique = true, length = 64)
    private String username;
    private String userPassword;
    @Column(length = 20)
    private String userTel;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUsername() {
        return username;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserTel() {
        return userTel;
    }
}

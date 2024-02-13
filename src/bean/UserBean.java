package bean;

import java.io.Serializable;

public class UserBean extends BaseBean implements Serializable {
    protected Integer id;
    protected String username;
    protected String password;
    protected Integer balance;


    public UserBean() {
    }

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 1000;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "[" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ']';
    }
}

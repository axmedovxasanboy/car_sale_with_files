package db;

import bean.UserBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static db.DataBase.users;

public class ReadUsers implements Runnable {

    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\users.ser";
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        while (true) {
            try {
                UserBean user = (UserBean) in.readObject();
                users.add(user);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Information loaded");
                break;
            }
        }
    }
}

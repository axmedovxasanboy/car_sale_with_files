package db;

import bean.UserBean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static db.DataBase.users;

public class SaveUserFiles implements Runnable {
    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\users.ser";
        for (UserBean user : users) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
                out.writeObject(user);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

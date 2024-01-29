package db;

import bean.UserBean;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static db.DataBase.users;

public class SaveUserFiles implements Runnable {
    @Override
    public void run() {
        StringBuilder userInfo = new StringBuilder();
        for (UserBean user : users) {
            userInfo.append(user).append("\n");
        }
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\users.txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        PrintWriter text = new PrintWriter(Objects.requireNonNull(fw));
        text.print(userInfo);
        text.close();

    }
}

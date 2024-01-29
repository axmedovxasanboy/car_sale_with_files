package db;

import bean.UserBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static db.DataBase.users;

public class ReadUsers implements Runnable {

    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\users.txt";
        Scanner reader = null;
        try {
            reader = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Integer id = null, balance = null;
        String username = null, password = null;
        if (reader != null) {
            reader.useDelimiter("\n");

            while (reader.hasNext()) {
                String userInfo = reader.next();
                String[] info = userInfo.split(", ");

                UserBean user = new UserBean();
                for (String element : info) {
                    if (element.startsWith("[id=")) {
                        id = Integer.parseInt(String.valueOf(element.charAt(4)));
                        user.setId(id);
                        continue;
                    }
                    if (element.startsWith("username='")) {
                        username = element.substring(10, element.length() - 1);
                        user.setUsername(username);
                        continue;
                    }
                    if (element.startsWith("password='")) {
                        password = element.substring(10, element.length() - 1);
                        user.setPassword(password);
                        continue;
                    }
                    if (element.startsWith("balance=")) {
                        balance = Integer.parseInt(element.substring(8, element.length() - 1));
                        user.setBalance(balance);
                    }
                    if (id != null && balance != null && username != null && password != null) {
                        users.add(user);
                    }
                }
            }
        }
        if (reader != null) {
            reader.close();
        }

    }
}

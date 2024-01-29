package db;

import bean.CarBean;
import bean.UserBean;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static db.DataBase.cars;
import static db.DataBase.users;

public class SaveCarFiles implements Runnable {
    @Override
    public void run() {
            StringBuilder carInfo = new StringBuilder();
            for (CarBean car : cars) {
                carInfo.append(car).append("\n");
            }
            String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\cars.txt";
            FileWriter fw = null;
            try {
                fw = new FileWriter(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            PrintWriter text = new PrintWriter(Objects.requireNonNull(fw));
            text.print(carInfo);
            text.close();
    }
}

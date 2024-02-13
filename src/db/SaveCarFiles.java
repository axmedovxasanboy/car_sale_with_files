package db;

import bean.CarBean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static db.DataBase.cars;

public class SaveCarFiles implements Runnable {
    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\cars.ser";
        for (CarBean car : cars) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
                out.writeObject(car);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

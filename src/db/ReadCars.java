package db;

import bean.CarBean;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import static db.DataBase.cars;

public class ReadCars implements Runnable {
    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\cars.ser";
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(new FileInputStream(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        while(true){
            try {
                CarBean car = (CarBean) in.readObject();
                cars.add(car);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Information loaded");
                break;
            }
        }
    }
}

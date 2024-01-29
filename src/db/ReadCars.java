package db;

import bean.CarBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static db.DataBase.cars;

public class ReadCars implements Runnable {
    @Override
    public void run() {
        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\cars.txt";
        Scanner reader = null;
        try {
            reader = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (reader != null) {
            reader.useDelimiter("\n");
        }
        Integer id = null, userId = null, price = null;
        String name = null, color = null;

        if (reader != null) {
            while (reader.hasNext()) {
                String userInfo = reader.next();
                String[] info = userInfo.replaceAll(" \\|\\|", ",").split(", ");

                CarBean car = new CarBean();
                for (String element : info) {
                    if (element.startsWith("[id=")) {
                        id = Integer.parseInt(element.substring(4));
                        car.setId(id);
                        continue;
                    }
                    if (element.startsWith("car-owner-id=")) {
                        userId = Integer.parseInt(element.substring(13));
                        car.setUserId(userId);
                        continue;
                    }
                    if (element.startsWith("name='")) {
                        name = element.substring(6, element.length() - 1);
                        car.setName(name);
                        continue;
                    }
                    if (element.startsWith("color='")) {
                        color = element.substring(7, element.length() - 1);
                        car.setColor(color);
                        continue;
                    }
                    if (element.startsWith("price=")) {
                        price = Integer.parseInt(element.substring(6));
                        car.setPrice(price);
                        continue;
                    }
                    if (element.startsWith("not")) {
                        car.setInStore(false);
                    } else if (element.startsWith("is")) {
                        car.setInStore(true);
                    }
                    if (id != null && userId != null && price != null && name != null && color != null && car.isInStore() != null)
                        cars.add(car);
                }
            }
        }
        if (reader != null) {
            reader.close();
        }

    }
}

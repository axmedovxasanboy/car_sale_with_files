package db;

import bean.CarBean;
import bean.UserBean;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBase {
    protected static List<UserBean> users = new ArrayList<>();
    protected static List<CarBean> cars = new ArrayList<>();
    public static UserBean session = null;
    public static List<CarBean> previousPrices = new ArrayList<>();

//    public static Runnable readCars = () -> {
//        String path = "D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information\\cars.txt";
//        Scanner reader = null;
//        try {
//            reader = new Scanner(new File(path));
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        if (reader != null) {
//            reader.useDelimiter("\n");
//        }
//        int id, userId, price;
//        String name, color;
//
//        if (reader != null) {
//            while (reader.hasNext()) {
//                String userInfo = reader.next();
//                String[] info = userInfo.replaceAll(" \\|\\|", ",").split(", ");
//
//                for (String element : info) {
//                    CarBean car = new CarBean();
//                    if (element.startsWith("[id=")) {
//                        id = Integer.parseInt(element.substring(4));
//                        car.setId(id);
//                    }
//                    if (element.startsWith("car-owner-id=")) {
//                        userId = Integer.parseInt(element.substring(13));
//                        car.setUserId(userId);
//                    }
//                    if (element.startsWith("name='")) {
//                        name = element.substring(6, element.length() - 1);
//                        car.setName(name);
//                    }
//                    if (element.startsWith("color='")) {
//                        color = element.substring(7, element.length() - 1);
//                        car.setColor(color);
//                    }
//                    if (element.startsWith("price=")) {
//                        price = Integer.parseInt(element.substring(6));
//                        car.setPrice(price);
//                    }
//                    if (element.startsWith("not")) {
//                        car.setInStore(false);
//                    } else if (element.startsWith("is")) {
//                        car.setInStore(true);
//                    }
//                    cars.add(car);
//                }
//            }
//        }
//        if (reader != null) {
//            reader.close();
//        }
//
//    };

    public static UserBean addUser(UserBean user) {
        for (UserBean userBean : users) {
            if (userBean.getUsername().equals(user.getUsername())) return null;
        }
        user.setId(users.size());
        users.add(user);
//        writeUserInfo();
        return user;
    }

    private static void writeUserInfo() {
//        StringBuilder userInfo = new StringBuilder();
//        for (UserBean user : users) {
//            userInfo.append(user).append("\n");
//        }
//        try (PrintWriter text = new PrintWriter("D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information.txt")) {
//            text.print(userInfo);
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }


//        StringBuilder userInfo = new StringBuilder();
//        for (UserBean user : users) {
//            userInfo.append(user).append("\n");
//        }
//        String info = userInfo.toString();
//        FileOutputStream text = null;
//        try {
//            text = new FileOutputStream("D:\\PDP\\Java\\JavaBackend (O'tkirbek aka)\\OnlineCarMarketWithFiles\\src\\db\\information.txt");
//            byte[] bytes = info.getBytes();
//            text.write(bytes);
//
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }finally {
//            try {
//                if (text != null) {
//                    text.close();
//                }
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }

        Runnable run = new SaveUserFiles();
        run.run();

    }

    public static UserBean getUser(String username, String password) {
        for (UserBean user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public static Integer addCar(CarBean car) {
        car.setId(cars.size());
        CarBean oldCar = new CarBean(car.getId(), car.getName(), car.getColor(), car.getUserId(), car.getPrice(), false);
        previousPrices.add(oldCar);
        cars.add(car);
//        writeCarInfo();
        return car.getId();
    }

    private static void writeCarInfo() {
        Runnable run = new SaveCarFiles();
        run.run();
    }

    public static List<CarBean> getUserCars(Integer id) {
        List<CarBean> carList = new ArrayList<>();
        for (CarBean car : cars) {
            if (car.getUserId().equals(id)) {
                carList.add(car);
            }
        }
        return carList;
    }

    public static Boolean deleteCar(Integer carId, Integer userId) {
        int carPrice;
        UserBean user = new UserBean();
        for (UserBean userBean : users) {
            if (userBean.getId().equals(userId)) user = userBean;
        }
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getId().equals(carId) && !cars.get(i).isInStore() && cars.get(i).getUserId().equals(userId)) {
                carPrice = cars.get(i).getPrice();
                cars.remove(i);
                user.setBalance(user.getBalance() + carPrice);
                return true;
            }
        }
        return false;
    }

    public static Boolean carSell(int id, int newPrice, Integer sessionId) {
        for (CarBean car : cars) {
            if (car.getId().equals(id) && car.getUserId().equals(sessionId) && !car.isInStore()) {
                car.setInStore(true);
                car.setPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    public static Boolean buyCarUser(Integer carId, UserBean user) {
        CarBean oldCar = new CarBean();
        for (CarBean old : previousPrices) {
            if (old.getId().equals(carId)) oldCar = old;
        }
        for (CarBean car : cars) {
            if (car.getId().equals(carId) && user.getBalance() >= car.getPrice() && car.isInStore()) {
                car.setInStore(false);
                for (UserBean us : users) {
                    if (us.getId().equals(car.getUserId())) {
                        us.setBalance(us.getBalance() + car.getPrice());
                        car.setUserId(user.getId());
                        oldCar.setPrice(car.getPrice());
                        oldCar.setUserId(user.getId());
                        user.setBalance(user.getBalance() - car.getPrice());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean marketCars(Integer userId) {
        List<CarBean> marketCars = new ArrayList<>();
        for (CarBean car : cars) {
            if (car.isInStore() && !car.getUserId().equals(userId)) {
                marketCars.add(car);
            }
        }
        marketCars.forEach(System.out::println);
        return marketCars.isEmpty();
    }

    public static List<CarBean> carsNotInStore(Integer userId) {
        List<CarBean> carList = new ArrayList<>();
        for (CarBean car : cars) {
            if (car.getUserId().equals(userId) && !car.isInStore()) {
                carList.add(car);
            }
        }
        return carList;
    }

    public static List<CarBean> carsInStore(Integer userId) {
        List<CarBean> carList = new ArrayList<>();
        for (CarBean car : cars) {
            if (car.getUserId().equals(userId) && car.isInStore()) {
                carList.add(car);
            }
        }
        return carList;
    }

    public static Boolean edit(UserBean user, String newUsername, String newPassword) {
        for (UserBean userBean : users) {
            if (userBean.getUsername().equals(user.getUsername()) && !userBean.getId().equals(user.getId()))
                return false;
        }
        if (!newUsername.isBlank()) user.setUsername(newUsername);
        if (!newPassword.isBlank()) user.setPassword(newPassword);
        return true;
    }

    public static Boolean takeCar(int carId, Integer userId) {
        CarBean car = new CarBean();
        UserBean user = new UserBean();
        CarBean oldCar = new CarBean();
        boolean carFound = false, userFound = false;
        for (CarBean c : cars) {
            if (c.getId().equals(carId) && c.getUserId().equals(userId)) {
                car = c;
                carFound = true;
                break;
            }
        }
        for (UserBean u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                userFound = true;
                break;
            }
        }
        for (CarBean old : previousPrices) {
            if (old.getUserId().equals(user.getId()) && old.getId().equals(car.getId())) {
                oldCar = old;
                System.out.println("old.getPrice() = " + old.getPrice());
            }
        }
        if (carFound && userFound) {
            car.setInStore(false);
            car.setPrice(oldCar.getPrice());
            return true;
        }
        return false;
    }

    public static CarBean getChosenCar(Integer carId) {
        for (CarBean car : cars) {
            if (car.getId().equals(carId)) return car;
        }
        return null;
    }

    public static void importInformation() {
        Runnable readCars = new ReadCars();
        readCars.run();
        Runnable readUsers = new ReadUsers();
        readUsers.run();
    }

    public static void exportInformation() {
        exportUserInfo();
        exportCarInfo();
    }

    private static void exportCarInfo() {
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

    private static void exportUserInfo() {
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

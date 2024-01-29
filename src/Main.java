import bean.ApiResponse;
import bean.CarBean;
import bean.UserBean;
import db.DataBase;
import resource.CarResource;
import resource.UserResource;

import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scannerNum = new Scanner(System.in);
    static Scanner scannerStr = new Scanner(System.in);

    public static void main(String[] args) {
        DataBase.importInformation();


        System.out.println("Welcome to the online market.");
        while (true) {
            if (DataBase.session == null) {
                System.out.print("""                        
                        0. Exit
                        1. Register
                        2. LogIn
                        >>\s""");
            } else {
                System.out.print("""
                                                
                        0. Exit
                        1. Add car
                        2. See my cars
                        3. Sell car
                        4. Purchase a new car
                        5. Delete car
                        6. My Information
                        9. LogOut
                        >>\s""");
            }
            String choice = scannerStr.nextLine();
            if(choice.equals("3")) DataBase.listOfUsers();
            if (DataBase.session == null) {
                switch (choice) {
                    case "0" -> {
                        DataBase.exportInformation();
                        return;
                    }
                    case "1" -> register();
                    case "2" -> login();

                }
            } else {
                switch (choice) {
                    case "0" -> {
                        DataBase.exportInformation();
                        return;
                    }
                    case "1" -> addCar();
                    case "2" -> myCarList();
                    case "3" -> sellCar();
                    case "4" -> buyCar();
                    case "5" -> deleteCar();
                    case "6" -> myInfo();
                    case "9" -> logOut();
                }
            }
        }
    }


    private static void register() {
        UserResource resource = new UserResource();

        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();

        UserBean user = new UserBean(username, password);

        ApiResponse response = resource.add(user);

        System.out.println(response.getMessage());
        if (response.getCode().equals(400)) register();

        else if (response.getCode().equals(200)) DataBase.session = (UserBean) response.getData();


    }

    private static void login() {
        System.out.print("Enter your username: ");
        String username = scannerStr.nextLine();
        System.out.print("Enter your password: ");
        String password = scannerStr.nextLine();

        UserResource resource = new UserResource();
        UserBean bean = new UserBean(username, password);
        ApiResponse login = resource.login(bean);
        System.out.println(login.getMessage());

        if (login.getCode().equals(200)) DataBase.session = (UserBean) login.getData();

    }

    private static void addCar() {
        System.out.println("-1. Back to menu");
        System.out.print("Enter car name: ");
        String carName = scannerStr.nextLine();
        if (carName.equals("-1")) return;

        System.out.print("Enter car color: ");
        String carColor = scannerStr.nextLine();
        if (carColor.equals("-1")) return;

        System.out.print("Enter car price: ");
        int carPrice = scannerNum.nextInt();
        if (carPrice == -1) return;

        if (carPrice <= DataBase.session.getBalance() && carPrice > 0) {
            CarBean car = new CarBean(carName, carColor, DataBase.session.getId(), carPrice);
            CarResource resource = new CarResource();
            ApiResponse api = resource.add(car);
            car.setInStore(false);
            System.out.println(api.getMessage());
            if (api.getCode().equals(400)) {
                addCar();
            }
            DataBase.session.setBalance(DataBase.session.getBalance() - carPrice);
        } else {
            System.out.println("Car price is invalid!");
        }
    }

    private static boolean showCars(ApiResponse cars) {
        System.out.println("*************");
        System.out.println(cars.getMessage());
        if (cars.getCode().equals(400)) {
            System.out.println("*************");
            return true;
        } else if (cars.getCode().equals(200)) {
            if (cars.getData() != null) {
                List<CarBean> list = (List<CarBean>) cars.getData();
                list.forEach(System.out::println);
            }
            System.out.println("*************");
        }
        return false;
    }

    private static void takeCarsFromStore() {
        CarResource resource = new CarResource();
        ApiResponse cars = resource.getCarsInStore(DataBase.session.getId());
        boolean carListEmpty = showCars(cars);
        if (!carListEmpty) {
            System.out.println("-1. Back to menu");
            System.out.print("Insert car ID: ");
            int id = scannerNum.nextInt();
            if (id == -1) return;
            ApiResponse response = resource.takeCar(id, DataBase.session.getId());
            System.out.println(response.getMessage());
        }
    }

    private static void myCarList() {
        System.out.println("Note: In order to edit car info you should delete that car first then add another car with correct db.information!!!");
        CarResource resource = new CarResource();
        ApiResponse cars = resource.get(DataBase.session.getId());
        boolean carList = showCars(cars);
        if (carList) return;
        System.out.print("1. Take cars from store.\nOr Press Enter to continue\n>>> ");
        String choice = scannerStr.nextLine();
        if (choice.equals("1")) takeCarsFromStore();
    }

    private static void sellCar() {
        CarResource resource = new CarResource();
        ApiResponse cars = resource.getCarsNotInStore(DataBase.session.getId());
        boolean carListEmpty = showCars(cars);
        if (!carListEmpty) {
            System.out.println("-1. Back to menu");
            System.out.print("Insert car ID: ");
            int id = scannerNum.nextInt();
            if (id == -1) return;
            System.out.print("Insert new price: ");
            int newPrice = scannerNum.nextInt();
            if (newPrice == -1) return;
            ApiResponse response = resource.sellCar(id, newPrice, DataBase.session.getId());
            System.out.println(response.getMessage());
        }
    }

    private static void buyCar() {
        boolean isMarketEmpty = DataBase.marketCars(DataBase.session.getId());

        if (!isMarketEmpty) {
            System.out.println("-1. Back to menu.");
            System.out.print("Insert car ID: ");
            int id = scannerNum.nextInt();
            if (id == -1) return;
            CarResource resource = new CarResource();
            System.out.println(DataBase.getChosenCar(id));
            System.out.print("Do you really want to buy this car? y/n\n>>> ");
            String answer = scannerStr.nextLine();
            if (answer.equals("n")) return;
            ApiResponse response = resource.buyCar(id, DataBase.session);
            System.out.println(response.getMessage());
        } else
            System.out.println("Car market is empty\n");
    }

    private static void deleteCar() {
        CarResource resource = new CarResource();
        ApiResponse cars = resource.getCarsNotInStore(DataBase.session.getId());
        boolean carListEmpty = showCars(cars);
        if (!carListEmpty) {
            System.out.println("-1. Back to menu");
            System.out.print("Insert car ID: ");
            int id = scannerNum.nextInt();
            if (id == -1) return;
            ApiResponse response = resource.delete(id, DataBase.session.getId());
            System.out.println(response.getMessage());
        }
    }

    private static void myInfo() {
        System.out.println(DataBase.session);
        System.out.print("""
                1. Edit profile
                Or press enter to continue...
                >>>\s""");

        String choice = scannerStr.nextLine();
        if (choice.equals("1")) editInfo();
    }

    private static void editInfo() {
        System.out.println("-1. Back to menu");
        System.out.print("Insert new username (press Enter to stay the same): ");
        String newUsername = scannerStr.nextLine();
        if (newUsername.equals("-1")) return;
        System.out.print("Insert new password (press Enter to stay the same): ");
        String newPassword = scannerStr.nextLine();
        if (newPassword.equals("-1")) return;
        UserResource resource = new UserResource();
        ApiResponse response = resource.edit(DataBase.session, newUsername, newPassword);
        System.out.println(response.getMessage());
    }

    private static void logOut() {
        System.out.println("Success");
        DataBase.session = null;
    }

}
package bean;

import java.io.Serializable;

public class CarBean extends BaseBean implements Serializable {
    protected Integer id;

    protected String name;
    protected String color;
    protected Integer userId;
    protected Integer price;
    protected Boolean isInStore;

    public CarBean() {
    }

    public CarBean(String name, String color, Integer userId, Integer price) {
        this.name = name;
        this.color = color;
        this.userId = userId;
        this.price = price;
        isInStore = false;
    }

    public CarBean(Integer id, String name, String color, Integer userId, Integer price, Boolean isInStore) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.userId = userId;
        this.price = price;
        this.isInStore = isInStore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean isInStore() {
        return isInStore;
    }

    public void setInStore(Boolean inStore) {
        isInStore = inStore;
    }

    @Override
    public String toString() {
        String store;
        if (isInStore) store = "In Store";
        else store = "not In Store";
        return "[" +
                "id=" + getId() +
                ", car-owner-id=" + userId +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                " || " + store +
                ']';
    }
}

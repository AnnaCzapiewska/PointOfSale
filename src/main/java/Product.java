import java.io.Serializable;

public class Product implements Serializable {
    private static long serialVersionUID;

    private String name;
    private Float price;
    private String barCode;

    public Product(String name, Float price, String barCode) {
        this.name = name;
        this.price = price;
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public String getBarCode() {
        return barCode;
    }

    public Float getPrice() {
        return price;
    }

}



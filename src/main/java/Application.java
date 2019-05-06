import java.util.List;

public class Application {
    public static void main(String[] args) {

        Utils utils = new Utils();
        Base base = new Base();
        base.save();

        List<Product> productList = utils.createProductList();
        utils.printReceipt(productList);

    }
}

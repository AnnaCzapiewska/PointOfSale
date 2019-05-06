import java.util.List;

public class Printer {

    public void printReceipt(List<Product> product, Float sum) {
        System.out.println("PRINTER");
        product.stream()
                .forEachOrdered(item -> System.out.println(item.getName() + "   " + item.getPrice()));
        System.out.println("Sum     =   " + sum);
    }
}



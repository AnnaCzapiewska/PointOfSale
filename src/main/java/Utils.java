import java.util.ArrayList;
import java.util.List;

public class Utils {
    private final CodeScanner codeScanner;
    private final LCD lcd;
    private final Base base;
    private final Printer printer;

    public Utils() {
        codeScanner = new CodeScanner();
        lcd = new LCD();
        base = new Base();
        printer = new Printer();
    }

    public Utils(CodeScanner codeScanner, LCD lcd, Base base, Printer printer) {
        this.codeScanner = codeScanner;
        this.lcd = lcd;
        this.base = base;
        this.printer = printer;
    }

    public List<Product> createProductList() {

        List<Product> productList = new ArrayList<>();

        System.out.println("Enter bar code: ");
        String barCode = codeScanner.scan();

        while((!barCode.equals("exit"))){
            Product product = checkBarcode(barCode);
            if(product != null) {
                productList.add(product);
            }
            barCode = codeScanner.scan();
        }

        return productList;
    }

    public Product checkBarcode (String barCode) {
        if(barCode.equals("")) {
            lcd.printEmpty();
        }else {
            Product product = base.reading(barCode);
            if (product != null) {
                lcd.printProduct(product);
                return product;
            } else {
                lcd.printNotFound();
            }
        }
        return null;
    }

    public Float calculateSum (List<Product> productList) {
        Float sum = 0.0F;
        for (Product item:productList) {
            sum += item.getPrice();
        }
        return sum;
    }

    public void printReceipt(List<Product> productList) {
        Float sum = calculateSum(productList);
        lcd.printSum(sum);
        printer.printReceipt(productList, sum);
    }
}

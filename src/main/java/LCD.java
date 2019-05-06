public class LCD {

    public void printProduct(Product product) {
        System.out.println("LCD");
        System.out.println(product.getName() + "    " + product.getPrice().toString());
    }

    public void printNotFound() {
        System.out.println("LCD");
        System.out.println("Product not found");
    }

    public void printEmpty() {
        System.out.println("LCD");
        System.out.println("Invalid bar-code");
    }
    public void printSum(Float sum) {
        System.out.println("LCD");
        System.out.println("Sum:   " + sum.toString());
    }
}

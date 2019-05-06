import java.io.*;
import java.util.Arrays;
import java.util.List;


public class Base {

   private final String filePath = "Database.bin";

    public void save() {

        List<Product> productList = Arrays.asList(
                new Product("milk 0,5l", 2.0F,"1234"),
                new Product("milk 1l", 3.0F,"2345"),
                new Product("chocolate", 3.5F,"3456"),
                new Product("water", 2.0F,"4567"),
                new Product("bread", 2.5F,"5678"),
                new Product("cheese", 6.0F,"6789"),
                new Product("yoghurt", 3.0F,"7890"),
                new Product("glue", 4.5F,"8901")
        );

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            productList.stream()
                    .forEach(product -> {
                        try {
                            objectOutputStream.writeObject(product);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
            }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public Product reading(String barCode) {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            while (true) {
                Product readObject = (Product) objectInputStream.readObject();
                if(readObject.getBarCode().equals(barCode)){
                    return readObject;
                }
            }
        }
        catch (EOFException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (null);
    }
}

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UtilsTest {
    private final String VALID_BARCODE = "999";
    private final String INVALID_BARCODE = "851";
    private final String LINE_SEPARATOR = System.lineSeparator();

    private final Product EXAMPLE_PRODUCT_1 = new Product("name1", 1.0F, VALID_BARCODE);
    private final Product EXAMPLE_PRODUCT_2 = new Product("name2", 2.0F, "123");
    private final Product EXAMPLE_PRODUCT_3 = new Product("name3", 3.0F, "124");

    private final Float SUM = 6.0F;
    private final List<Product> PRODUCT_LIST = new ArrayList<>(
            Arrays.asList(EXAMPLE_PRODUCT_1,EXAMPLE_PRODUCT_2,EXAMPLE_PRODUCT_3));

    private OutputStream outputStream;
    private Utils utils;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        CodeScanner mockCodeScanner = mock(CodeScanner.class);
        when(mockCodeScanner.scan()).thenReturn(VALID_BARCODE,INVALID_BARCODE,"exit");

        Base mockBase = mock(Base.class);
        when(mockBase.reading(VALID_BARCODE)).thenReturn(EXAMPLE_PRODUCT_1);
        when(mockBase.reading(INVALID_BARCODE)).thenReturn(null);

        utils = new Utils(mockCodeScanner, new LCD(), mockBase, new Printer());
    }

    @After
    public void tearDown() {
        PrintStream originalOut = System.out;
        System.setOut(originalOut);
    }

    @Test
    public void shouldCreateProductListWithOneProduct() {
        //GIVEN

        //WHEN
        List<Product> productList = utils.createProductList();

        //THEN
        assertTrue(productList.size() == 1);
        assertTrue(EXAMPLE_PRODUCT_1.equals(productList.get(0)));
    }

    @Test
    public void shouldCheckBarcode() {
        //GIVEN

        //WHEN
        Product result = utils.checkBarcode(VALID_BARCODE);

        //THEN
        assertTrue(EXAMPLE_PRODUCT_1.equals(result));
    }

    @Test
    public void shouldCheckBarcodeNotFound() {
        //GIVEN

        //WHEN
        Product result = utils.checkBarcode(INVALID_BARCODE);

        //THEN
        assertEquals("LCD" + LINE_SEPARATOR +
                "Product not found" + LINE_SEPARATOR, outputStream.toString());
        assertNull(result);
    }

    @Test
    public void shouldCheckBarcodeEmpty() {
        //GIVEN

        //WHEN
        Product result = utils.checkBarcode("");

        //THEN
        assertEquals("LCD" + LINE_SEPARATOR +
                "Invalid bar-code" + LINE_SEPARATOR, outputStream.toString());
        assertNull(result);
    }

    @Test
    public void shouldCalculateSumFromList() {
        //GIVEN

        //WHEN
        Float result = utils.calculateSum(PRODUCT_LIST);

        //THEN
        assertEquals(SUM, result);
    }

    @Test
    public void shouldCalculateSumFromOneProduct() {
        //GIVEN

        //WHEN
        Float result = utils.calculateSum(Arrays.asList(EXAMPLE_PRODUCT_1));

        //THEN
        assertEquals(EXAMPLE_PRODUCT_1.getPrice(), result);
    }

    @Test
    public void shouldPrintReceiptFromList() {
        //GIVEN

        //WHEN
        utils.printReceipt(PRODUCT_LIST);

        //THEN
        assertEquals("LCD" + LINE_SEPARATOR +
                "Sum:   6.0" + LINE_SEPARATOR +
                "PRINTER" + LINE_SEPARATOR +
                "name1   1.0" + LINE_SEPARATOR +
                "name2   2.0" + LINE_SEPARATOR +
                "name3   3.0" + LINE_SEPARATOR +
                "Sum     =   6.0" + LINE_SEPARATOR, outputStream.toString());
    }

    @Test
    public void shouldPrintReceiptFromOneProduct() {
        //GIVEN

        //WHEN
        utils.printReceipt(Arrays.asList(EXAMPLE_PRODUCT_1));

        //THEN
        assertEquals("LCD" + LINE_SEPARATOR +
                "Sum:   1.0" + LINE_SEPARATOR +
                "PRINTER" + LINE_SEPARATOR +
                "name1   1.0" + LINE_SEPARATOR +
                "Sum     =   1.0" + LINE_SEPARATOR, outputStream.toString());
    }
}
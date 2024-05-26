import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    void everyBranchTest() {
        List<Item> list = new ArrayList<>();
        RuntimeException ex;

        // allitems = null, payment = x(whatever)

        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 50));
        assertTrue(ex.getMessage().contains("allItems list can't be null!"));


        //  Allitems = [null, barcode = 0123456789, item discout = 5 , price = 70],

        list = new ArrayList<>();
        list.add(new Item(null, null, 70, 5));

        List<Item> finalList = list;
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(finalList, 0));
        assertTrue(ex.getMessage().contains("No barcode!"));

        // Allitems = [Goran, barcode = trpe123 , x , x ] discount > 0

        list = new ArrayList<>();
        list.add(new Item("Goran", "trpe123", 70, 5));

        List<Item> finalList2 = list;
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(finalList2, 0));
        assertTrue(ex.getMessage().contains("Invalid character in item barcode!"));

        // Allitems = [Goran, barcode = trpe123 , x , x ] discount < 0

        list = new ArrayList<>();
        list.add(new Item("Goran", "trpe123", 70, -5));

        List<Item> finalList3 = list;
        ex = assertThrows(RuntimeException.class, () -> SILab2.checkCart(finalList3, 0));
        assertTrue(ex.getMessage().contains("Invalid character in item barcode!"));

        // Allitems = price = 350, DIscount = 10, barcode = 000000, nepotreben test??

        list = new ArrayList<>();
        list.add(new Item("Goran", "000000", 350, 10));


        List<Item> items = new ArrayList<>();
        items.add(new Item("Item1", "12345", 100, 0.1f)); // Price = 100
        items.add(new Item("Item2", "67890", 200, 0.2f)); // Price = 200
        int payment = 350; // Set payment greater than the total sum of item prices

        boolean result = SILab2.checkCart(items, payment);
        assertTrue(result); // The total sum of item prices is 300, so it should return true

        List<Item> items2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) { // Changing the number of items to 3
            StringBuilder barcodeBuilder = new StringBuilder();
            barcodeBuilder.append(0);
            barcodeBuilder.append((i + 1) * 1000);
            StringBuilder nameBuilder = new StringBuilder();
            nameBuilder.append("Product");
            nameBuilder.append(i + 1);
            items2.add(new Item(nameBuilder.toString(), barcodeBuilder.toString(), (i + 1) * 150, i * 0.5f)); // Adjusting prices and discounts
        }
        payment = 200; // Adjusting the payment amount

        result = SILab2.checkCart(items2, payment);
        assertFalse(result); // Since the total sum of item prices exceeds the payment, it should return false
    }

    @Test
    void multipleConditionsTest() {
        List<Item> items = new ArrayList<>();

        // Adding items with various name conditions
        items.add(new Item(null, "12345", 100, 0));
        items.add(new Item("", "67890", 200, 0));
        items.add(new Item("ValidName", "54321", 300, 0.1f));

        // Expected result when payment covers the sum of items
        int payment = 600;

        // Testing the checkCart method
        boolean result = SILab2.checkCart(items, payment);

        // Verifying that items with null or empty names are set to "unknown"
        assertEquals("unknown", items.get(0).getName());
        assertEquals("unknown", items.get(1).getName());
        assertEquals("ValidName", items.get(2).getName());

        // Asserting the method returns true as the payment should cover the sum
        assertTrue(result);


        //if (item.getPrice() > 300 && item.getDiscount() > 0 && item.getBarcode().charAt(0) == '0')
        //T&&T&&T
        List<Item> lista = new ArrayList<Item>();
        lista.add(new Item("name1", "01234", 500, 1));
        assertEquals(false, SILab2.checkCart(lista, 100));

    }
}
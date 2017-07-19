package ManiplateWorkbook;

public class ShippingBibleExtract {
    public static void main(String[] args) {
        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook("Transport Bible.xlsx");
        System.out.println("Bible Start Date is :" + shippingBibleWorkbook.getStartDate());
        System.out.println("Bible End Date is   :" + shippingBibleWorkbook.getEndDate());
        DepotCrossReference depotCrossReference = shippingBibleWorkbook.buildDepotCrossReference();
        depotCrossReference.display();
        System.out.println(depotCrossReference.lookup("n/a"));

    }
}

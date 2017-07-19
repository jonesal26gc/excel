package ManiplateWorkbook;

public class ShippingBibleExtract {
    public static void main(String[] args) {
        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook("Transport Bible.xlsx");
        System.out.println("Bible Start Date is :" + shippingBibleWorkbook.getBibleStartDate());
        System.out.println("Bible End Date is   :" + shippingBibleWorkbook.getBibleEndDate());
        DepotCrossReference depotCrossReference = shippingBibleWorkbook.retrieveDepotCrossReference();
        depotCrossReference.display();
        System.out.println(depotCrossReference.lookup("n/a"));

    }
}

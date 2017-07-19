package ManiplateWorkbook;

import java.util.HashMap;
import java.util.Map;

public class DepotCrossReference {
    private HashMap<String, String[]> depotCrossReference;

    public DepotCrossReference() {
        this.depotCrossReference = new HashMap<String, String[]>();
    }

    public void add(String depotName, String[] depotList) {
        depotCrossReference.put(depotName, depotList);
    }

    public void display() {
        // List the cross-reference.
        for (Map.Entry<String, String[]> entry : depotCrossReference.entrySet()) {
            System.out.print(entry.getKey() + " |");
            for (String depot : entry.getValue()) {
                System.out.print(" " + depot);
            }
            System.out.println();
        }
    }

    public String[] lookup(String s) {
        return depotCrossReference.get(s);
    }
}

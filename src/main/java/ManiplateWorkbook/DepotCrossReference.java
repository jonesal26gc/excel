package ManiplateWorkbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepotCrossReference {
    private HashMap<String, ArrayList<String>> depotCrossReference;

    public DepotCrossReference() {
        this.depotCrossReference = new HashMap<String, ArrayList<String>>();
    }

    public void add(String depotName, ArrayList<String> depotList){
        depotCrossReference.put(depotName,depotList);
    }

    public void display() {
        // List the cross-reference.
        for (Map.Entry<String, ArrayList<String>> entry : depotCrossReference.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue().toString());
        }
    }

    public ArrayList<String> lookup(String s) {
        return depotCrossReference.get(s);
    }
}

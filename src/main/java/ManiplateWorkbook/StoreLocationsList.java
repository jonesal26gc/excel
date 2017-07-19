package ManiplateWorkbook;

import java.util.ArrayList;

public class StoreLocationsList {
    ArrayList<Location> locations = new ArrayList<Location>();

    public StoreLocationsList() {
    }

    public void add(Location store) {
        locations.add(store);
    }

    public void display() {
        for (Location location:locations){
            System.out.println(location.toString());
        }
    }
}

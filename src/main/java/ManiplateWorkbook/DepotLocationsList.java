package ManiplateWorkbook;

import java.util.ArrayList;

public class DepotLocationsList {
    ArrayList<Location> locations = new ArrayList<Location>();

    public DepotLocationsList() {
    }

    public void add(Location depot) {
        locations.add(depot);
    }

    public void display() {
        for (Location location:locations){
            System.out.println(location.toString());
        }
    }
}

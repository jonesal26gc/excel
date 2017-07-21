package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class StoreLocationsList implements Serializable {
    ArrayList<Location> locations = new ArrayList<Location>();

    public StoreLocationsList() {
    }

    public void add(Location store) {
        locations.add(store);
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void display() {
        for (Location location:locations){
            System.out.println(location.toString());
        }
    }

    public void sort(){
        Collections.sort(locations);
    }
}

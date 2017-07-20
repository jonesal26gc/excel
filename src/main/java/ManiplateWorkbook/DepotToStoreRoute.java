package ManiplateWorkbook;

import java.util.Arrays;
import java.util.Date;

public class DepotToStoreRoute {
    private String depot;
    private String store;
    private String [] depotList;
    private Date startDate;
    private Date endDate;

    public DepotToStoreRoute(String depot, String store, String [] depotList, Date startDate, Date endDate) {
        this.depot = depot;
        this.store = store;
        this.depotList = depotList;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDepot() {
        return depot;
    }

    public String getStore() {
        return store;
    }

    public String [] getDepotList() {
        return depotList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "DepotToStoreRoute{" +
                "depot='" + depot + '\'' +
                ", store='" + store + '\'' +
                ", depotList=" + Arrays.toString(depotList) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

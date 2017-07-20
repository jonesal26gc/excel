package ManiplateWorkbook;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class DepotToStoreRoute implements Serializable {
    private String depot;
    private String store;
    private String routeTypeCode;
    private String [] depotList;
    private Date startDate;
    private Date endDate;

    public DepotToStoreRoute(String depot, String store, String routeTypeCode, String[] depotList, Date startDate, Date endDate) {
        this.depot = depot;
        this.store = store;
        this.routeTypeCode = routeTypeCode;
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

    public String getRouteTypeCode() {
        return routeTypeCode;
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
                ", routeTypeCode='" + routeTypeCode + '\'' +
                ", depotList=" + Arrays.toString(depotList) +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}

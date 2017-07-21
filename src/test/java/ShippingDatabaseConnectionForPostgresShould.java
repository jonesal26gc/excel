
import ManiplateWorkbook.ShippingDatabaseConnectionForPostgres;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShippingDatabaseConnectionForPostgresShould {

    @Test
    public void connectToPostgres() {

        ShippingDatabaseConnectionForPostgres shippingDatabaseConnectionForPostgres = new ShippingDatabaseConnectionForPostgres();
        shippingDatabaseConnectionForPostgres.establishConnection();
        try {
            Statement stmt = shippingDatabaseConnectionForPostgres.c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) as num_of_locations FROM location;");
            while (rs.next()) {
                int numberOfLocations = rs.getInt("num_of_locations");
                System.out.println("number of locations is: " + numberOfLocations);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shippingDatabaseConnectionForPostgres.terminateConnection();
    }


    @Test
    public void connectToPostgresToListDepotLocations() {

        ShippingDatabaseConnectionForPostgres shippingDatabaseConnectionForPostgres = new ShippingDatabaseConnectionForPostgres();
        shippingDatabaseConnectionForPostgres.establishConnection();
        try {
            Statement stmt = shippingDatabaseConnectionForPostgres.c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT location_code" +
                            ", location_type_code" +
                            ", name" +
                            ", format" +
                            ", county" +
                            ", postcode" +
                            ", country" +
                            ", reporting_region" +
                            " FROM location" +
                            " where location_type_code = 'DEPOT' " +
                            "  and location_code between 0 and 999;");
            while (rs.next()) {
                int numberOfLocations = rs.getInt("location_code");
                String name = rs.getString("name");
                System.out.println("number of locations is: " + numberOfLocations + " " + name);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shippingDatabaseConnectionForPostgres.terminateConnection();
    }


    @Test
    public void connectToPostgresToListRoutes() {

        ShippingDatabaseConnectionForPostgres shippingDatabaseConnectionForPostgres = new ShippingDatabaseConnectionForPostgres();
        shippingDatabaseConnectionForPostgres.establishConnection();
        try {
            Statement stmt = shippingDatabaseConnectionForPostgres.c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT route_number" +
                            ", location_code_start" +
                            ", location_code_end" +
                            ", route_type_code" +
                            ", start_date" +
                            ", end_date" +
                            " FROM route" +
                            " where start_date = '2017-03-20' " +
                            "   and route_type_code = 'FRONT' " +
                            " order by location_code_start, location_code_end" +
                            " limit 10;");
            while (rs.next()) {
                int numberOfLocations = rs.getInt("route_number");
                String name = rs.getString("route_type_code");
                System.out.println("number of routes is: " + numberOfLocations + " " + name);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shippingDatabaseConnectionForPostgres.terminateConnection();
    }

//
//    @Test
//    public void connectToPostgresForShipping() {
//
//        ShippingDatabaseConnectionForPostgres shippingDatabaseConnectionForPostgres = new ShippingDatabaseConnectionForPostgres();
//        shippingDatabaseConnectionForPostgres.establishConnection();
//        try {
//            Statement statement = shippingDatabaseConnectionForPostgres.c.createStatement();
//            String bQueryStatement =
//                    "WITH RECURSIVE SUB_LOCATIONS ( " +
//                            "          LOCATION_NUMBER " +
//                            "        , RELATED_LOCATION_NUMBER " +
//                            "        , depth " +
//                            "        , paths ) AS " +
//                            "( SELECT L.LOCATION_NUMBER " +
//                            "       , L.RELATED_LOCATION_NUMBER " +
//                            "       , 0 as depth " +
//                            "       , cast ('{' ||" +
//                            "         'toNumber=' || cast(l.location_number as varchar(5)) || ', ' || " +
//                            "         'toName=' || t1.location_name || ', ' ||" +
//                            "         'toLocationType=' || t1.location_type || ', ' ||" +
//                            "         'relationshipType=' || l.relationship_type || ', ' ||" +
//                            "         'startDate=' || cast(l.start_date as date ) || ', ' ||" +
//                            "         'endDate=' || cast(l.end_date as date ) ||', ' ||" +
//                            "         'fromNumber=' || cast(l.related_location_number as varchar(5)) || ', ' ||" +
//                            "         'fromName=' || t2.location_name || ', ' ||" +
//                            "         'fromLocationType=' || t2.location_type || " +
//                            "         '}' as text) as paths " +
//                            "    FROM LOCATION_RELATIONSHIP L " +
//                            "       , LOCATION T1 " +
//                            "       , LOCATION T2 " +
//                            "    WHERE L.LOCATION_NUMBER    = 2055 " +
//                            "      AND L.RELATIONSHIP_TYPE  = 'FRONT-HAUL' " +
//                            "      AND L.START_DATE        <= '2017-06-07' " +
//                            "      AND L.END_DATE          >= '2017-06-07' " +
//                            "      AND T1.LOCATION_NUMBER    = L.LOCATION_NUMBER " +
//                            "      AND T2.LOCATION_NUMBER    = L.RELATED_LOCATION_NUMBER " +
//                            "    UNION ALL " +
//                            "    SELECT L.LOCATION_NUMBER " +
//                            "            , L.RELATED_LOCATION_NUMBER " +
//                            "            , s.depth + 1 as depth " +
//                            "            , cast ( s.paths || ', ' || " +
//                            "              '{' ||" +
//                            "              'toNumber=' || cast(l.location_number as varchar(5)) || ', ' || " +
//                            "              'toName=' || t1.location_name || ', ' ||" +
//                            "              'toLocationType=' || t1.location_type || ', ' ||" +
//                            "              'relationshipType=' || l.relationship_type || ', ' ||" +
//                            "              'startDate=' || cast(l.start_date as date ) || ', ' ||" +
//                            "              'endDate=' || cast(l.end_date as date ) ||', ' ||" +
//                            "              'fromNumber=' || cast(l.related_location_number as varchar(5)) || ', ' ||" +
//                            "              'fromName=' || t2.location_name || ', ' ||" +
//                            "              'fromLocationType=' || t2.location_type || " +
//                            "              '}' as text) as paths " +
//                            "    FROM SUB_LOCATIONS S " +
//                            "            , LOCATION_RELATIONSHIP L " +
//                            "            , LOCATION T1 " +
//                            "            , LOCATION T2 " +
//                            "    WHERE L.LOCATION_NUMBER    = S.RELATED_LOCATION_NUMBER " +
//                            "      AND L.RELATIONSHIP_TYPE  = 'FRONT-HAUL' " +
//                            "      AND L.START_DATE        <= '2017-06-07' " +
//                            "      AND L.END_DATE          >= '2017-06-07' " +
//                            "      AND L.LOCATION_NUMBER   <> 0 " +
//                            "      and position('460' in s.paths) = 0 " +
//                            "      AND T1.LOCATION_NUMBER    = L.LOCATION_NUMBER " +
//                            "      AND T2.LOCATION_NUMBER    = L.RELATED_LOCATION_NUMBER " +
//                            ")" +
//                            "            SELECT DISTINCT " +
//                            "              s.depth " +
//                            "            , s.paths " +
//                            "    FROM SUB_LOCATIONS S " +
//                            "    where position('460' in s.paths) > 0 " +
//                            "    ORDER BY S.depth " +
//                            "              ;";
//
//            statement.executeQuery(bQueryStatement);
//            ResultSet resultSet = statement.getResultSet();
//
//            List<Route> routes = new ArrayList<>();
//
//            while (resultSet.next()) {
//                String resultRow = resultSet.getString(2);
//                resultRow = resultRow.substring(1, (resultRow.length() - 1));
//                List<String> journeys = Arrays.asList(resultRow.split("\\}, \\{"));
////                System.out.println("There are " + journeys.size() + " journeys");
//
//                ArrayList<Leg> legs = new ArrayList<>();
//
//                for (String entry : journeys) {
//                    String[] journeyProperties = entry.split(", ");
//                    Location fromLocation = new Location();
//                    Location toLocation = new Location();
//                    Relationship relationship = new Relationship();
//                    for (String journeyProperty : journeyProperties) {
//                        String propertyNameAndValue[] = journeyProperty.split("=");
////                        System.out.println(propertyNameAndValue[0] + " is " + propertyNameAndValue[1]);
//
//                        switch (propertyNameAndValue[0]) {
//                            case "fromNumber":
//                                fromLocation.setLocationId(propertyNameAndValue[1]);
//                                break;
//                            case "fromName":
//                                fromLocation.setLocationName(propertyNameAndValue[1]);
//                                break;
//                            case "fromLocationType":
//                                fromLocation.setLocationTypes(Arrays.asList(LocationType.determineLocationType(propertyNameAndValue[1])));
//                                break;
//                            case "relationshipType":
//                                relationship.setRelationshipName(propertyNameAndValue[1]);
//                                break;
//                            case "startDate":
//                                relationship.setValidFromDate(RouteRepository.convertStringToDate(propertyNameAndValue[1]));
//                                break;
//                            case "endDate":
//                                relationship.setValidToDate(RouteRepository.convertStringToDate(propertyNameAndValue[1]));
//                                break;
//                            case "toNumber":
//                                toLocation.setLocationId(propertyNameAndValue[1]);
//                                break;
//                            case "toName":
//                                toLocation.setLocationName(propertyNameAndValue[1]);
//                                break;
//                            case "toLocationType":
//                                toLocation.setLocationTypes(Arrays.asList(LocationType.determineLocationType(propertyNameAndValue[1])));
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                    Leg leg = new Leg(relationship, fromLocation, toLocation);
//                    legs.add(leg);
//                }
//                Collections.reverse(legs);
//                routes.add(new Route(legs));
//            }
//
//            for (Route route : routes) {
//                System.out.println("Route with " + route.getLegs().size() + " journey legs:");
//                for (Leg leg : route.getLegs()) {
//                    System.out.println(leg.getFromLocation().toString());
//                    System.out.println(leg.getRelationship().toString());
//                    System.out.println(leg.getToLocation().toString());
//                    System.out.println();
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        shippingDatabaseConnectionForPostgres.terminateConnection();
//    }
}


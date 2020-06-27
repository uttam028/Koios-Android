package org.mlab.research.koios.ui.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MapDataProvider {

    private static ArrayList<SignificantLocation> visitedPlaces = new ArrayList<>();
    private static ArrayList<VisitEvent> visitHistory = new ArrayList<>();

    public static ArrayList<SignificantLocation> getSignificantLocations(int numOfDays){

        visitedPlaces.add(new SignificantLocation("1", "La Fortune", 41.702256, -86.237645));
        visitedPlaces.add(new SignificantLocation("2", "Debartolo", 41.6984196, -86.2362891));
        visitedPlaces.add(new SignificantLocation("3", "Jordan Hall of Science", 41.700782, -86.231959));
        visitedPlaces.add(new SignificantLocation("4", "South Dining Hall",41.699584, -86.241428));
        visitedPlaces.add(new SignificantLocation("5", "North Dining Hall",41.704641, -86.235425));
        visitedPlaces.add(new SignificantLocation("6", "Hesburgh Library",41.702462, -86.234868));

        return visitedPlaces;
    }

    public static ArrayList<VisitEvent> getVisitHistory(SignificantLocation location, int numOfDays){
        return null;
    }

    public static ArrayList<VisitEvent> getAllVisits(SignificantLocation location){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(2020, 5, 25, 7, 34);
        calendar2.set(2020, 5, 25, 11, 23);
        visitHistory.add(new VisitEvent(calendar1, calendar2));
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.set(2020, 5, 24, 13, 11);
        calendar2.set(2020, 5, 24, 22, 13);
        visitHistory.add(new VisitEvent(calendar1, calendar2));
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.set(2020, 5, 20, 8, 11);
        calendar2.set(2020, 5, 20, 13, 13);
        visitHistory.add(new VisitEvent(calendar1, calendar2));
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        calendar1.set(2020, 5, 20, 17, 11);
        calendar2.set(2020, 5, 20, 21, 13);
        visitHistory.add(new VisitEvent(calendar1, calendar2));
        return visitHistory;
    }



}

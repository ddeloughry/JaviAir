package application;

import java.util.ArrayList;

public class FlightInfo {

    private static ArrayList<String> locArr = new ArrayList<>();

    public static ArrayList<String> getTimes(int s) {
        ArrayList<String> timesArr = new ArrayList<>();
        ArrayList<String> timesArr2 = new ArrayList<>();

        timesArr.add("09:20");
        timesArr2.add(null);
        timesArr.add("10:30");
        timesArr2.add(null);
        timesArr.add("14:00");
        timesArr2.add(null);
        timesArr.add("09:00");
        timesArr2.add("18:20");
        timesArr.add("08:20");
        timesArr2.add("11:20");
        timesArr.add("08:00");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("12:00");
        timesArr2.add(null);
        timesArr.add("06:20");
        timesArr2.add(null);
        timesArr.add("08:00");
        timesArr2.add(null);
        timesArr.add("14:00");
        timesArr2.add("19:05");
        timesArr.add("08:00");
        timesArr2.add(null);
        timesArr.add("19:00");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("06:20");
        timesArr2.add(null);
        timesArr.add("08:05");
        timesArr2.add(null);
        timesArr.add("12:00");
        timesArr2.add(null);
        timesArr.add("10:00");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("08:00");
        timesArr2.add(null);
        timesArr.add("17:00");
        timesArr2.add(null);
        timesArr.add("08:00");
        timesArr2.add(null);
        timesArr.add("13:30");
        timesArr2.add("22:00");
        timesArr.add("19:20");
        timesArr2.add(null);
        timesArr.add("19:00");
        timesArr2.add(null);
        timesArr.add("20:00");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("11:50");
        timesArr2.add(null);
        timesArr.add("11:00");
        timesArr2.add("18:00");
        timesArr.add("10:20");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("09:00");
        timesArr2.add(null);
        timesArr.add("09:00");
        timesArr2.add(null);
        timesArr.add("08:00");
        timesArr2.add("13:30");
        timesArr.add("13:00");
        timesArr2.add(null);
        timesArr.add("20:00");
        timesArr2.add(null);
        timesArr.add("20:00");
        timesArr2.add(null);
        timesArr.add("18:00");
        timesArr2.add(null);
        timesArr.add("18:05");
        timesArr2.add(null);
        timesArr.add("15:00");
        timesArr2.add("20:35");
        if (s == 1) {
            return timesArr;
        }
        return timesArr2;
    }

    public static ArrayList<String> getLocations() {
        locArr.add("Cork");
        locArr.add("Madrid");
        locArr.add("St Brieuc");
        locArr.add("Jersey");
        locArr.add("Paris");
        locArr.add("Stansted");
        locArr.add("Malaga");
        return locArr;
    }

    public static ArrayList<String> getJourneys() {
        ArrayList<String> journeysArr = new ArrayList<>();

        journeysArr.add(locArr.get(0) + " " + locArr.get(1));
        journeysArr.add(locArr.get(0) + " " + locArr.get(2));
        journeysArr.add(locArr.get(0) + " " + locArr.get(3));
        journeysArr.add(locArr.get(0) + " " + locArr.get(4));
        journeysArr.add(locArr.get(0) + " " + locArr.get(5));
        journeysArr.add(locArr.get(0) + " " + locArr.get(6));
        journeysArr.add(locArr.get(1) + " " + locArr.get(0));
        journeysArr.add(locArr.get(1) + " " + locArr.get(2));
        journeysArr.add(locArr.get(1) + " " + locArr.get(3));
        journeysArr.add(locArr.get(1) + " " + locArr.get(4));
        journeysArr.add(locArr.get(1) + " " + locArr.get(5));
        journeysArr.add(locArr.get(1) + " " + locArr.get(6));
        journeysArr.add(locArr.get(2) + " " + locArr.get(0));
        journeysArr.add(locArr.get(2) + " " + locArr.get(1));
        journeysArr.add(locArr.get(2) + " " + locArr.get(4));
        journeysArr.add(locArr.get(2) + " " + locArr.get(5));
        journeysArr.add(locArr.get(2) + " " + locArr.get(6));
        journeysArr.add(locArr.get(3) + " " + locArr.get(0));
        journeysArr.add(locArr.get(3) + " " + locArr.get(1));
        journeysArr.add(locArr.get(3) + " " + locArr.get(4));
        journeysArr.add(locArr.get(3) + " " + locArr.get(5));
        journeysArr.add(locArr.get(3) + " " + locArr.get(6));
        journeysArr.add(locArr.get(4) + " " + locArr.get(0));
        journeysArr.add(locArr.get(4) + " " + locArr.get(1));
        journeysArr.add(locArr.get(4) + " " + locArr.get(2));
        journeysArr.add(locArr.get(4) + " " + locArr.get(3));
        journeysArr.add(locArr.get(4) + " " + locArr.get(5));
        journeysArr.add(locArr.get(4) + " " + locArr.get(6));
        journeysArr.add(locArr.get(5) + " " + locArr.get(0));
        journeysArr.add(locArr.get(5) + " " + locArr.get(1));
        journeysArr.add(locArr.get(5) + " " + locArr.get(2));
        journeysArr.add(locArr.get(5) + " " + locArr.get(3));
        journeysArr.add(locArr.get(5) + " " + locArr.get(4));
        journeysArr.add(locArr.get(5) + " " + locArr.get(6));
        journeysArr.add(locArr.get(6) + " " + locArr.get(0));
        journeysArr.add(locArr.get(6) + " " + locArr.get(1));
        journeysArr.add(locArr.get(6) + " " + locArr.get(2));
        journeysArr.add(locArr.get(6) + " " + locArr.get(3));
        journeysArr.add(locArr.get(6) + " " + locArr.get(4));
        journeysArr.add(locArr.get(6) + " " + locArr.get(5));

        return journeysArr;
    }

    public static int[] getPrices() {
        return new int[]{100, 100, 120, 80, 40, 240, 100, 200, 200, 60, 60, 60, 100, 200, 150, 80, 140, 120, 200,
                250, 250, 280, 80, 60, 150, 250, 60, 100, 40, 60, 80, 250, 60, 120, 240, 60, 140, 280, 100, 120};
    }
}

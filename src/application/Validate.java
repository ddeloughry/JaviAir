package application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Validate {

    public static boolean validateLocations(ArrayList<String> locArr, String a, String b) {
        if (a != null && b != null) {
            if (!(a.equals(locArr.get(2)) && b.equals(locArr.get(3)) || a.equals(locArr.get(3)) && b.equals(locArr.get(2)))) {
                return true;
            }
            AlertBox.display("No flights between " + a + " and " + b);
            return false;
        }
        AlertBox.display("Locations must not be null");
        return false;
    }

    public static boolean validatePassengers(int x, int y1, int y, int z) {
        if ((x + y1 + y + z) > 0) {
            if ((x + y1 + y + z) < 9) {
                if (x > 0) {
                    if ((x * 2) >= (y1 + y + z)) {
                        return true;
                    }
                    AlertBox.display("A maximum of two children allowd per adult booking");
                    return false;
                }
                AlertBox.display("At least one adult must be on the flight");
                return false;
            }
            AlertBox.display("8 people maximum per booking");
            return false;
        }
        AlertBox.display("Number of passengers can't be zero");
        return false;
    }

    public static boolean validateDates(ArrayList<String> locArr, String a, String b, LocalDate d, LocalDate d2, boolean journeyType) {
        Date input = new Date();
        Instant instant = input.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate date = zdt.toLocalDate();
        if (!journeyType) {
            if (d != null) {
                if (!d.isBefore(date)) {
                    String m = d.getMonth().toString();
                    if (!(a.equals(locArr.get(2)) && b.equals(locArr.get(5)) && ((m.equalsIgnoreCase("March") || m.equalsIgnoreCase("April"))))) {
                        if (!(a.equals(locArr.get(5)) && b.equals(locArr.get(2)) && ((m.equalsIgnoreCase("March") || m.equalsIgnoreCase("April"))))) {
                            if (!(a.equals(locArr.get(4)) && b.equals(locArr.get(5)) && m.equalsIgnoreCase("April"))) {
                                if (!(a.equals(locArr.get(5)) && b.equals(locArr.get(4)) && m.equalsIgnoreCase("April"))) {
                                    return true;
                                }
                                AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                                return false;
                            }
                            AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                            return false;
                        }
                        AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                        return false;
                    }
                    AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                    return false;
                }
                AlertBox.display("Date can't be before current date");
                return false;
            }
            AlertBox.display("Date must not be null");
            return false;
        } else if (journeyType) {
            if (d != null && d2 != null) {
                if (!d.isBefore(date)) {
                    if (d.isBefore(d2)) {
                        String m = d.getMonth().toString();
                        if (!(a.equals(locArr.get(2)) && b.equals(locArr.get(5)) && ((m.equalsIgnoreCase("March") || m.equalsIgnoreCase("April"))))) {
                            if (!(a.equals(locArr.get(5)) && b.equals(locArr.get(2)) && ((m.equalsIgnoreCase("March") || m.equalsIgnoreCase("April"))))) {
                                if (!(a.equals(locArr.get(4)) && b.equals(locArr.get(5)) && m.equalsIgnoreCase("April"))) {
                                    if (!(a.equals(locArr.get(5)) && b.equals(locArr.get(4)) && m.equalsIgnoreCase("April"))) {
                                        return true;
                                    }
                                    AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                                    return false;
                                }
                                AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                                return false;
                            }
                            AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                            return false;
                        }
                        AlertBox.display("No flights between " + a + " and " + b + " in " + m);
                        return false;
                    }
                    AlertBox.display("Date out can't be before date return");
                    return false;
                }
                AlertBox.display("Date can't be before current date");
                return false;
            }
            AlertBox.display("Dates must not be null");
            return false;
        }
        return false;
    }

    public static boolean validatePassengerNames(ArrayList<String> names) {
        boolean b = false;
        for (String name : names) {
            b = !(name == null || name.equalsIgnoreCase(""));
        }
        if (!b) {
            AlertBox.display("Please enter ALL passenger names");
        }
        return b;
    }

    public static boolean validateCountries(ArrayList<String> countries) {
        boolean b = false;
        for (String country : countries) {
            b = !(country == null || country.equalsIgnoreCase(""));
        }
        if (!b) {
            AlertBox.display("Please enter ALL passenger(s) country of residence");
        }
        return b;
    }

    public static boolean validateCreditCard(String cc) {
        if (cc == null || cc.equals("") || cc.length() != 16) {
            AlertBox.display("Credit Card number incorrect.\n" + cc.length());
            return false;
        }
        for (int i = 0; i < cc.length(); i++) {
            char x = cc.charAt(i);
            if (x < '0' || x > '9') {
                AlertBox.display("Credit Card number incorrect.\n" + x);
                return false;
            }
        }
        int totalNumber = 0;
        for (int i = 0; i < 16; i++) {
            int x = Integer.parseInt(String.valueOf(cc.charAt(i)));
            int num;
            if (i % 2 == 0) {
                num = x * 2;
                if (num > 9) {
                    num = num - 9;
                }
            } else {
                num = x;
            }
            totalNumber = totalNumber + num;
        }
        if (totalNumber % 10 != 0) {
            AlertBox.display("Credit Card number incorrect.");
            return false;
        }
        return true;
    }

    public static boolean validateDni(String dni, String countries) {
        String[] letters = {"t", "r", "w", "a", "g", "m", "y", "f", "p", "d", "x", "b", "n", "j", "z", "s", "q", "v", "h", "l", "k", "e"};
        if (countries.equalsIgnoreCase("spain")) {
            if ((dni != null) && (!dni.equalsIgnoreCase("")) && dni.length() == 9) {
                String str = dni.substring(0, dni.length() - 1);
                int strInt = Integer.parseInt(str);
                if (dni.substring(dni.length() - 1).equalsIgnoreCase(letters[strInt % 23])) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int dateToInt(LocalDate date) {
        int y, m, d;
        y = date.getYear();
        m = date.getMonthValue();
        d = date.getDayOfMonth();
        return Integer.parseInt(y + "" + m + "" + d);
    }
}

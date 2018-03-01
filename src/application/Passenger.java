package application;

import java.time.LocalDate;

public class Passenger {
    private String name, country, dni;
    private LocalDate dateOfBirth;
    private int numberOfBags;

    public Passenger(String name, LocalDate dateOfBirth, String country, String dni, int numberOfBags) {
        this.country = country;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dni = dni;
        this.numberOfBags = numberOfBags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

}

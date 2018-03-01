package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.ArrayList;

import static javafx.scene.paint.Color.BLACK;


public class Main extends Application {
    private Stage window;
    private Scene sc1, sc2, sc3, sc4, sc5;
    private BorderPane layout1 = new BorderPane(), layout2 = new BorderPane(), layout3 = new BorderPane();
    private ArrayList<String> locArr = new ArrayList<>();
    private int[] priceArr;
    private ArrayList<String> journeysArr = new ArrayList<>();
    private double price;
    private String departure, destination, journeyOut, journeyBack, journeyOutTime, journeyBackTime, creditCardNumber;
    private int numAdult, numChild17, numChild5, numInfant, numPassengers, reduction;
    private LocalDate departureDate, returnDate;
    private ComboBox<String> selectTime, selectReturnTime;
    private ArrayList<Passenger> passengers;
    private boolean journeyIsReturn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(e -> closeProgram());

        times();
        locArr = FlightInfo.getLocations();
        journeysArr = FlightInfo.getJourneys();
        priceArr = FlightInfo.getPrices();

        createHomePage();
        createOneWayPage();
        createReturnPage();


        window.setScene(sc1);
        window.setTitle("JaviAir");
        window.show();
    }

    private void createHomePage() {
        Button close = new Button("Close");
        close.setOnAction(e -> closeProgram());
        layout1.setPadding(new Insets(10, 10, 10, 10));
        layout1.setTop(createTop("Welcome to JaviAir"));
        layout1.setBottom(close);
        sc1 = new Scene(layout1, 1300, 700);
    }

    private void createOneWayPage() {
        GridPane g = new GridPane();
        ComboBox<String> dep, dest;
        ComboBox<Integer> adult, child17, child5, infant;
        Button close, book;
        DatePicker d;
        dep = new ComboBox<>();
        dep.getItems().addAll(locArr);
        dest = new ComboBox<>();
        dest.getItems().addAll(locArr);
        adult = new ComboBox<>();
        adult.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8);
        child17 = new ComboBox<>();
        child17.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        child5 = new ComboBox<>();
        child5.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        infant = new ComboBox<>();
        infant.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        d = new DatePicker();
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(6))) {
                                    setDisable(true);
                                }
                            }
                        };
                    }
                };
        d.setDayCellFactory(dayCellFactory);
        book = new Button("Book");
        dep.setOnAction(e -> {
            dest.getSelectionModel().clearSelection();
            dest.getItems().clear();
            dest.getItems().addAll(locArr);
            dest.getItems().remove(dep.getSelectionModel().getSelectedItem());
            if (dep.getSelectionModel().getSelectedItem().equals(locArr.get(2))) {
                dest.getItems().remove(locArr.get(3));
            } else if (dep.getSelectionModel().getSelectedItem().equals(locArr.get(3))) {
                dest.getItems().remove(locArr.get(2));
            }
        });
        book.setOnAction(e -> {
            departureDate = d.getValue();
            departure = dep.getSelectionModel().getSelectedItem();
            destination = dest.getSelectionModel().getSelectedItem();
            numAdult = adult.getSelectionModel().getSelectedIndex();
            if (numAdult == -1) {
                numAdult = 0;
            }
            numChild17 = child17.getSelectionModel().getSelectedIndex();
            if (numChild17 == -1) {
                numChild17 = 0;
            }
            numChild5 = child5.getSelectionModel().getSelectedIndex();
            if (numChild5 == -1) {
                numChild5 = 0;
            }
            numInfant = infant.getSelectionModel().getSelectedIndex();
            if (numInfant == -1) {
                numInfant = 0;
            }
            boolean locationsOk = Validate.validateLocations(locArr, departure, destination);
            if (locationsOk) {
                journeyOut = departure + " " + destination;
                boolean passengersOk = Validate.validatePassengers(numAdult, numChild17, numChild5, numInfant);
                if (passengersOk) {
                    journeyIsReturn = false;
                    boolean datesOk = Validate.validateDates(locArr, departure, destination, departureDate, null, false);
                    if (datesOk) {
                        String day = departureDate.getDayOfWeek().toString();
                        price = calculate(numAdult, numChild17, numChild5, day);
                        createBookingPage();
                        window.setScene(sc4);
                    }
                }
            }
        });
        close = new Button("Close");
        close.setOnAction(e -> closeProgram());
        g.addRow(1, new Label("From: "), dep, new Label("To: "), dest);
        g.addRow(2, new Label("Adults (18+): "), adult, new Label("Children (6-17): "), child17,
                new Label("Children (2-5): "), child5, new Label("Infants (0-1): "), infant);
        g.addRow(3, new Label("Date out: "), d);
        g.addRow(4, book);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setBorder(new Border(new BorderStroke(BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout2.setPadding(new Insets(10, 10, 10, 10));
        layout2.setTop(createTop("Book a ONE-WAY flight"));
        layout2.setCenter(g);
        layout2.setBottom(close);
        sc2 = new Scene(layout2, 1300, 700);
    }

    private void createReturnPage() {
        GridPane g = new GridPane();
        ComboBox<String> dep, dest;
        ComboBox<Integer> adult, child17, child5, infant;
        Button close, book;
        DatePicker d, d2;
        dep = new ComboBox<>();
        dep.getItems().addAll(locArr);
        dest = new ComboBox<>();
        dest.getItems().addAll(locArr);
        adult = new ComboBox<>();
        adult.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8);
        child17 = new ComboBox<>();
        child17.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        child5 = new ComboBox<>();
        child5.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        infant = new ComboBox<>();
        infant.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7);
        d = new DatePicker();
        d2 = new DatePicker();
        d2.setDisable(true);

        final Callback<DatePicker, DateCell> dc =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(6))) {
                                    setDisable(true);
                                }
                            }
                        };
                    }
                };
        d.setDayCellFactory(dc);

        d.setOnAction(e -> d2.setDisable(false));
        final Callback<DatePicker, DateCell> dc2 =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(d.getValue().plusDays(1)) || item.isAfter(d.getValue().plusMonths(6))) {
                                    setDisable(true);
                                }
                            }
                        };
                    }
                };
        d2.setDayCellFactory(dc2);
        dep.setOnAction(e -> {
            dest.getSelectionModel().clearSelection();
            dest.getItems().clear();
            dest.getItems().addAll(locArr);
            dest.getItems().remove(dep.getSelectionModel().getSelectedItem());
            if (dep.getSelectionModel().getSelectedItem().equals(locArr.get(2))) {
                dest.getItems().remove(locArr.get(3));
            } else if (dep.getSelectionModel().getSelectedItem().equals(locArr.get(3))) {
                dest.getItems().remove(locArr.get(2));
            }
        });
        book = new Button("Book");
        book.setOnAction(e -> {
            departureDate = d.getValue();
            returnDate = d2.getValue();
            departure = dep.getSelectionModel().getSelectedItem();
            destination = dest.getSelectionModel().getSelectedItem();
            numAdult = adult.getSelectionModel().getSelectedIndex();
            if (numAdult == -1) {
                numAdult = 0;
            }
            numChild17 = child17.getSelectionModel().getSelectedIndex();
            if (numChild17 == -1) {
                numChild17 = 0;
            }
            numChild5 = child5.getSelectionModel().getSelectedIndex();
            if (numChild5 == -1) {
                numChild5 = 0;
            }
            numInfant = infant.getSelectionModel().getSelectedIndex();
            if (numInfant == -1) {
                numInfant = 0;
            }
            boolean locationsOk = Validate.validateLocations(locArr, departure, destination);
            if (locationsOk) {
                journeyOut = departure + " " + destination;
                journeyBack = destination + " " + departure;
                boolean passengersOk = Validate.validatePassengers(numAdult, numChild17, numChild5, numInfant);
                if (passengersOk) {
                    journeyIsReturn = true;
                    boolean datesOk = Validate.validateDates(locArr, departure, destination, departureDate, returnDate, true);
                    if (datesOk) {
                        String outDay = departureDate.getDayOfWeek().toString();
                        String returnDay = returnDate.getDayOfWeek().toString();
                        price = (calculate(numAdult, numChild17, numChild5, outDay)) + (calculate(numAdult, numChild17, numChild5, returnDay));
                        createBookingPage();
                        window.setScene(sc4);
                    }
                }
            }
        });
        close = new Button("Close");
        close.setOnAction(e -> closeProgram());
        g.addRow(1, new Label("From: "), dep, new Label("To: "), dest);
        g.addRow(2, new Label("Adults (18+): "), adult, new Label("Children (6-17): "), child17,
                new Label("Children (2-5): "), child5, new Label("Infants (0-1): "), infant);
        g.addRow(3, new Label("Date out: "), d, new Label("Date return: "), d2);
        g.addRow(4, book);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        layout3.setPadding(new Insets(10, 10, 10, 10));
        layout3.setTop(createTop("Book a RETURN flight"));
        g.setBorder(new Border(new BorderStroke(BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout3.setCenter(g);
        layout3.setBottom(close);
        sc3 = new Scene(layout3, 1300, 700);
    }

    private void createBookingPage() {
        BorderPane layout4 = new BorderPane();
        Button close = new Button("Close");
        close.setOnAction(e -> closeProgram());
        GridPane g = new GridPane();
        numPassengers = 0;
        for (int i = 0; i < numAdult; i++) {
            g.add(new Label("#" + (numPassengers + 1) + " Adult: (18+)"), 0, numPassengers + 1);
            numPassengers++;
        }
        for (int i = 0; i < numChild17; i++) {
            g.add(new Label("#" + (numPassengers + 1) + " Child (6-17):"), 0, numPassengers + 1);
            numPassengers++;
        }
        for (int i = 0; i < numChild5; i++) {
            g.add(new Label("#" + (numPassengers + 1) + " Child (2-5):"), 0, numPassengers + 1);
            numPassengers++;
        }
        for (int i = 0; i < numInfant; i++) {
            g.add(new Label("#" + (numPassengers + 1) + " Infant (0-1):"), 0, numPassengers + 1);
            numPassengers++;
        }

        g.add(new Label("Name:"), 1, 0);
        ArrayList<TextField> t = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            t.add(new TextField());
            g.add(t.get(i), 1, i + 1);
        }

        g.add(new Label("Date of birth:"), 2, 0);
        ArrayList<DatePicker> d = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            d.add(new DatePicker());
            g.add(d.get(i), 2, i + 1);
        }
        for (int i = 0; i < numAdult; i++) {
            final Callback<DatePicker, DateCell> dc =
                    new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isAfter(LocalDate.now().minusYears(18))) {
                                        setDisable(true);
                                    }
                                }
                            };
                        }
                    };
            d.get(i).setDayCellFactory(dc);
        }
        for (int i = 0; i < numChild17; i++) {
            final Callback<DatePicker, DateCell> dc =
                    new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore((LocalDate.now().minusYears(18)).plusDays(1)) || item.isAfter(LocalDate.now().minusYears(6))) {
                                        setDisable(true);
                                    }
                                }
                            };
                        }
                    };
            d.get(i + numAdult).setDayCellFactory(dc);
        }
        for (int i = 0; i < numChild5; i++) {
            final Callback<DatePicker, DateCell> dc =
                    new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore((LocalDate.now().minusYears(6)).plusDays(1)) || item.isAfter(LocalDate.now().minusYears(2))) {
                                        setDisable(true);
                                    }
                                }
                            };
                        }
                    };
            d.get(i + numAdult + numChild17).setDayCellFactory(dc);
        }
        for (int i = 0; i < numInfant; i++) {
            final Callback<DatePicker, DateCell> dc =
                    new Callback<DatePicker, DateCell>() {
                        @Override
                        public DateCell call(final DatePicker datePicker) {
                            return new DateCell() {
                                @Override
                                public void updateItem(LocalDate item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item.isBefore((LocalDate.now().minusYears(2)).plusDays(1)) || item.isAfter(LocalDate.now())) {
                                        setDisable(true);
                                    }
                                }
                            };
                        }
                    };
            d.get(i + numAdult + numChild17 + numChild5).setDayCellFactory(dc);
        }
        g.add(new Label("Country:"), 3, 0);
        ArrayList<TextField> c = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            c.add(new TextField());
            g.add(c.get(i), 3, i + 1);
        }

        g.add(new Label("DNI number (Spanish only):"), 4, 0);
        ArrayList<TextField> dni = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            dni.add(new TextField());
            g.add(dni.get(i), 4, i + 1);
        }
        g.add(new Label("Baggage:"), 5, 0);
        ArrayList<ComboBox<String>> numLuggage = new ArrayList<>();
        for (int i = 0; i < numAdult; i++) {
            numLuggage.add(new ComboBox<>());
            if (journeyIsReturn) {
                numLuggage.get(i).getItems().addAll("None", "1 (Out journey / return)", "2 (Both ways)");
            } else {
                numLuggage.get(i).getItems().addAll("No", "Yes");
            }
            g.add(numLuggage.get(i), 5, i + 1);
        }
        for (int i = 0; i < numChild17; i++) {
            numLuggage.add(new ComboBox<>());
            if (journeyIsReturn) {
                numLuggage.get(i + numAdult).getItems().addAll("None", "1 (Out journey / return)", "2 (Both ways)");
            } else {
                numLuggage.get(i + numAdult).getItems().addAll("No", "Yes");
            }
            g.add(numLuggage.get(i + numAdult), 5, i + 1 + numAdult);
        }
        for (int i = 0; i < numChild5; i++) {
            numLuggage.add(new ComboBox<>());
            if (journeyIsReturn) {
                numLuggage.get(i + numAdult + numChild5).getItems().addAll("None", "1 (Out journey / return)", "2 (Both ways)");
            } else {
                numLuggage.get(i + numAdult + numChild5).getItems().addAll("No", "Yes");
            }
            g.add(numLuggage.get(i + numAdult + numChild17), 5, i + 1 + numAdult + numChild17);
        }
        for (int i = 0; i < numInfant; i++) {
            numLuggage.add(new ComboBox<>());
        }
        selectTime = new ComboBox<>();
        selectReturnTime = new ComboBox<>();
        times();
        if (journeyIsReturn) {
            g.addRow(numPassengers + 1, new Label("Select outward time:"), selectTime, new Label("Select return time:"), selectReturnTime);
        } else {
            g.addRow(numPassengers + 1, new Label("Select outward time:"), selectTime);
        }
        TextField credit = new TextField();
        g.addRow(numPassengers + 2, new Label("Credit card number:"), credit);
        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            journeyOutTime = selectTime.getSelectionModel().getSelectedItem();
            if (journeyIsReturn) {
                journeyBackTime = selectReturnTime.getSelectionModel().getSelectedItem();
            }
            for (int i = 0; i < numPassengers; i++) {
                numLuggage.get(i).getSelectionModel().getSelectedIndex();
            }
            ArrayList<String> testNames = new ArrayList<>();
            for (int i = 0; i < numPassengers; i++) {
                testNames.add(t.get(i).getText());
            }
            ArrayList<String> testCountries = new ArrayList<>();
            for (int i = 0; i < numPassengers; i++) {
                testCountries.add(c.get(i).getText());
            }
            boolean namesOk = Validate.validatePassengerNames(testNames);
            boolean countriesOk = Validate.validateCountries(testCountries);
            boolean ccOk = Validate.validateCreditCard(credit.getText());
            int currentReduction = 0;
            for (int i = 0; i < numPassengers; i++) {
                currentReduction += getDniReduction(dni.get(i).getText(), c.get(i).getText());
            }
            reduction = currentReduction;
            price -= reduction;
            if (countriesOk && namesOk && ccOk) {
                creditCardNumber = credit.getText();
                passengers = new ArrayList<>();
                for (int i = 0; i < numPassengers; i++) {
                    int n = numLuggage.get(i).getSelectionModel().getSelectedIndex();
                    if (n < 0) {
                        n = 0;
                    }
                    String dd = dni.get(i).getText();
                    if (!Validate.validateDni(dni.get(i).getText(), c.get(i).getText()) || dd.equalsIgnoreCase("")) {
                        dd = null;
                    }
                    passengers.add(new Passenger(t.get(i).getText(), d.get(i).getValue(), c.get(i).getText(), dd, n));
                }
                createFinalPage();
                window.setScene(sc5);
            }
        });

        g.add(submit, 5, numPassengers + 2);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        layout4.setPadding(new Insets(10, 10, 10, 10));
        layout4.setTop(createTop("Booking"));
        g.setBorder(new Border(new BorderStroke(BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout4.setCenter(g);
        Label priceLabel = new Label("Price: €" + price);

        VBox p = new VBox();
        p.getChildren().addAll(priceLabel, new Label("(Not including luggage)"));
        layout4.setRight(p);
        layout4.setBottom(close);
        sc4 = new Scene(layout4, 1300, 700);
    }

    private void createFinalPage() {
        BorderPane layout5 = new BorderPane();
        Button close = new Button("Close");
        close.setOnAction(e -> closeProgram());
        GridPane g = new GridPane();
        g.addRow(0, new Label("Name:"), new Label("Date of Birth:"), new Label("Country:"), new Label("DNI:"), new Label("Number of bags:"));
        for (int i = 0; i < numPassengers; i++) {
            g.addRow(i + 1, new Label(passengers.get(i).getName()),
                    new Label(passengers.get(i).getDateOfBirth().toString()),
                    new Label(passengers.get(i).getCountry()),
                    new Label(passengers.get(i).getDni()),
                    new Label(passengers.get(i).getNumberOfBags() + "")
            );
        }
        g.addRow(numPassengers + 1, new Label(""));
        g.addRow(numPassengers + 2, new Label("Outward:"), new Label("Departure:"), new Label(departure), new Label("Destination:"), new Label(destination), new Label("Time:"), new Label(journeyOutTime));

        if (journeyIsReturn) {
            g.addRow(numPassengers + 3, new Label("Return:"), new Label("Departure:"), new Label(destination), new Label("Destination:"), new Label(departure), new Label("Time:"), new Label(journeyBackTime));
        }
        g.addRow(numPassengers + 4, new Label(""));
        g.addRow(numPassengers + 5, new Label("Credit Card:"), new Label(creditCardNumber));
        g.addRow(numPassengers + 6, new Label("Price:"), new Label("€" + price));
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        g.setBorder(new Border(new BorderStroke(BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        layout5.setPadding(new Insets(10, 10, 10, 10));
        layout5.setTop(createTop("Final"));
        layout5.setCenter(g);
        layout5.setBottom(close);
        sc5 = new Scene(layout5, 1300, 700);
    }

    private double calculate(int x, int y1, int y, String day) {
        for (int i = 0; i < journeysArr.size(); i++) {
            if (journeyOut.equals(journeysArr.get(i))) {
                if (day.equalsIgnoreCase("Friday") || day.equalsIgnoreCase("Saturday") || day.equalsIgnoreCase("Sunday")) {
                    return ((x * priceArr[i]) + (y1 * priceArr[i]) + (y * 60)) * 1.2;
                }
                return ((x * priceArr[i]) + (y1 * priceArr[i]) + (y * 60));
            }
        }
        return 0;
    }

    private int getDniReduction(String d, String c) {
        int i = 0;
        if (Validate.validateDni(d, c)) {

            if ((departure.equals(locArr.get(1)) && destination.equals(locArr.get(6))) || (destination.equals(locArr.get(1)) && departure.equals(locArr.get(6)))) {
                i = 10;
            } else if (departure.equals(locArr.get(6)) || destination.equals(locArr.get(1)) || departure.equalsIgnoreCase(locArr.get(1)) || destination.equalsIgnoreCase(locArr.get(6))) {
                i = 5;
            }
        }
        return i;
    }

    private VBox createTop(String s) {
        GridPane g = new GridPane();
        Button btn1, btn2, btn3;
        Label title = new Label(s);
        title.setFont(new Font(40));
        g.addRow(1, title);
        btn1 = new Button("Home");
        btn1.setOnAction(e -> window.setScene(sc1));
        btn2 = new Button("One-way");
        btn2.setOnAction(e -> {
            returnDate = null;
            window.setScene(sc2);
        });
        btn3 = new Button("Return");
        btn3.setOnAction(e -> window.setScene(sc3));
        g.addRow(2, btn1, btn2, btn3);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10, 10, 10, 10));
        VBox vb = new VBox();
        vb.getChildren().addAll(title, g);
        return vb;
    }

    private void times() {

        ArrayList<String> timesArr = FlightInfo.getTimes(1);
        ArrayList<String> timesArr2 = FlightInfo.getTimes(2);
        for (int i = 0; i < journeysArr.size(); i++) {
            if (journeyOut.equals(journeysArr.get(i))) {
                selectTime.getItems().addAll(timesArr.get(i));
                if (timesArr2.get(i) != null) {
                    selectTime.getItems().addAll(timesArr2.get(i));
                }
            }
            if (journeyBack != null && journeyBack.equals(journeysArr.get(i))) {
                selectReturnTime.getItems().addAll(timesArr.get(i));
                if (timesArr2.get(i) != null) {
                    selectReturnTime.getItems().addAll(timesArr2.get(i));
                }
            }
        }
    }

    private void closeProgram() {
        window.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
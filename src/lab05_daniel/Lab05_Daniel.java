/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab05_daniel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author 6323612
 */
public class Lab05_Daniel extends Application{
    //Reimbursements
    private final static double DAILY_MEALS = 0.37;
    private final static double PER_MILE = 0.27;
    private final static double DAILY_TAXI = 20;
    private final static double DAILY_PARKING = 10;
    private final static double DAILY_LODGING = 95;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        //Setup parent elements
        GridPane grid = new GridPane();
        BorderPane rootBorder = new BorderPane(grid);
        
        String[] fieldNames = new String[] {"Days", "Airfare", "Rental fees"," Miles driven", "Parking fees", "Taxi charges", "Registration fees", "Lodging charges"};
        
        for (int i = 0; i < fieldNames.length; i++) {
            TextField field = createInputField(fieldNames[i], grid, i);
        }
        //Setup text fields
        TextField daysField = createInputField("Days", grid, 0);
        TextField airFareField = createInputField("Airfaire", grid, 1);
        TextField carRentalField = createInputField("Rental fees", grid, 2);
        TextField milesDrivenField = createInputField("Miles driven", grid, 3);
        TextField parkingFeesField = createInputField("Parking fees", grid, 4);
        TextField taxiChargesField = createInputField("Taxi charges", grid, 5);
        TextField registrationFeesField = createInputField("Registration fees", grid, 6);
        TextField lodgingChargesField = createInputField("Lodging charges", grid, 7);
        //Show
        Scene scene = new Scene(rootBorder);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    private TextField createInputField(String inputName, GridPane parentGrid, int verticalPos) {
        Label lbl = new Label(inputName);
        TextField txtField = new TextField();
        parentGrid.add(lbl, 0, verticalPos);
        parentGrid.add(txtField, 1, verticalPos);
        
        return txtField;
    }
    
    // Array order : 
    private double[] calcTravelExpenses(int dayCount, double airfare, double carRental, double milesDriven, double parkingFees, double taxiCharges, double registrationFees, double lodgingCharges)  {
        //not counting miles driven since there is no stated fee for miles driven
        double totalExpenses = airfare + carRental + parkingFees + taxiCharges + registrationFees + lodgingCharges * (double) dayCount;
        double allowableExpenses = (double) dayCount * (DAILY_MEALS + DAILY_TAXI + DAILY_PARKING + DAILY_LODGING) + milesDriven * PER_MILE;
        double excessExpenses = totalExpenses - allowableExpenses;
        return new double[] {totalExpenses, allowableExpenses, excessExpenses};
    }
}

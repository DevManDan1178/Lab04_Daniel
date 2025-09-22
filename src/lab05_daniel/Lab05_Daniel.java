/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab05_daniel;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author 6323612
 */
public class Lab05_Daniel extends Application{
    //Reimbursements
    private final static double DAILY_MEALS = 37;
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
        String[] fieldNames = new String[] {"Days: ", "Airfare: ", "Rental fees: ","Miles driven: ", "Parking fees: ", "Taxi charges: ", "Registration fees: ", "Nightly lodging fees: "};
        Label totalExpensesLbl = new Label("Total expenses: 0$");    
        Label allowableExpensesLbl = new Label("Allowable expenses: 0$");
        Label excessOrSavedLbl = new Label("Excess/Saved: 0$");
        grid.add(totalExpensesLbl, 1, 8);
        grid.add(allowableExpensesLbl, 1, 9);
        grid.add(excessOrSavedLbl, 1, 10);
        double[] priceCalcParams = new double[8];
        
        for (int i = 0; i < fieldNames.length; i++) {
            TextField field = createInputField(fieldNames[i], grid, i);
            final int thisIndex = i;
            field.setOnKeyReleased(keyEvent -> {
                try {
                    double entry = Double.parseDouble(field.getText());
                    if (entry < 0) {
                        field.setText(priceCalcParams[thisIndex] <= 0? "" : String.format("%.2f", priceCalcParams[thisIndex]));
                        return;
                    }
                    //Since is money, round at cents (1/100)
                    priceCalcParams[thisIndex] = Math.round(entry * 100) / 100 ;
                    double[] results = processCalcTravelExpenses(priceCalcParams);
                    totalExpensesLbl.setText(String.format("Total expenses: %.2f$", results[0]));
                    allowableExpensesLbl.setText(String.format("Allowable expenses: %.2f$", results[1]));
                    excessOrSavedLbl.setText(String.format("%s: %.2f$", results[2] < 0? "Saved" : "Excess", Math.abs(results[2])));
                } catch (Exception e) {
                    //Reset text if invalid characters to previous value or empty if invalid value
                    field.setText(priceCalcParams[thisIndex] <= 0? "" : String.format("%.2f", priceCalcParams[thisIndex]));
                }
            });
        }

        //Show
        Scene scene = new Scene(rootBorder, 750, 750);
        scene.getStylesheets().add("style.css");
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
    
    private double[] processCalcTravelExpenses(double[] args) {
        return calcTravelExpenses((int) args[0], args[1], args[2], args[3], args[4], args[5], args[6], args[7]);
    }
    
    private double[] calcTravelExpenses(int dayCount, double airfare, double carRental, double milesDriven, double parkingFees, double taxiCharges, double registrationFees, double nightlyLodgingCharges)  {
        double dayCountDbl = (double) dayCount;
        //not counting miles driven since there is no stated fee for miles driven
        double totalExpenses = airfare + carRental + parkingFees + taxiCharges + registrationFees + nightlyLodgingCharges * (double) dayCount;
        double allowableExpenses = dayCountDbl * (DAILY_MEALS + Math.min(DAILY_TAXI, taxiCharges/dayCountDbl) + Math.min(DAILY_PARKING, parkingFees/dayCountDbl) + Math.min(DAILY_LODGING, nightlyLodgingCharges)) + milesDriven * PER_MILE;
        double excessExpenses = totalExpenses - allowableExpenses; //if under 0, this becomes total saved
        return new double[] {totalExpenses, allowableExpenses, excessExpenses};
    }
}

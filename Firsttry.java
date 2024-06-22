import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Random;

public class Firsttry extends Application {

    private static final int PIN_CODE_LENGTH = 4;
    private static int[] pinCode = new int[100000000]; 
    private static double balance = 1000.00;

    @Override
    public void start(Stage primaryStage) {
        generateRandomPINs(); 

        primaryStage.setTitle("ATM Transaction");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label pinLabel = new Label("Enter PIN:");
        GridPane.setConstraints(pinLabel, 0, 0);

        PasswordField pinField = new PasswordField();
        GridPane.setConstraints(pinField, 1, 0);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 2, 0);
        loginButton.setOnAction(e -> {
            int userPin = Integer.parseInt(pinField.getText());
            if (isValidPin(userPin)) {
                showTransactionScene(primaryStage);
            } else {
                showAlert("Invalid PIN code");
            }
            pinField.clear();
        });

        grid.getChildren().addAll(pinLabel, pinField, loginButton);

        Scene scene = new Scene(grid, 300, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void generateRandomPINs() {
        Random random = new Random();
        for (int i = 0; i < pinCode.length; i++) {
            pinCode[i] = 1000 + random.nextInt(9000); 
        }
    }

    private boolean isValidPin(int pin) {
        for (int i = 0; i < pinCode.length; i++) {
            if (pin == pinCode[i]) {
                return true;
            }
        }
        return false;
    }

    private void showTransactionScene(Stage primaryStage) {
        GridPane transactionGrid = new GridPane();
        transactionGrid.setPadding(new Insets(20, 20, 20, 20));
        transactionGrid.setVgap(10);
        transactionGrid.setHgap(10);

        Label balanceLabel = new Label("Balance: $" + balance);
        GridPane.setConstraints(balanceLabel, 0, 0, 2, 1);

        Label amountLabel = new Label("Enter Amount:");
        GridPane.setConstraints(amountLabel, 0, 1);

        TextField amountField = new TextField();
        GridPane.setConstraints(amountField, 1, 1);

        Button withdrawButton = new Button("Withdraw");
        GridPane.setConstraints(withdrawButton, 0, 2);
        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert("Invalid amount");
            } else if (amount > balance) {
                showAlert("Insufficient funds");
            } else {
                balance -= amount;
                updateBalanceLabel(balanceLabel);
            }
            amountField.clear();
        });

        Button depositButton = new Button("Deposit");
        GridPane.setConstraints(depositButton, 1, 2);
        depositButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                showAlert("Invalid amount");
            } else {
                balance += amount;
                updateBalanceLabel(balanceLabel);
            }
            amountField.clear();
        });

        Button logoutButton = new Button("Logout");
        GridPane.setConstraints(logoutButton, 0, 3, 2, 1);
        logoutButton.setOnAction(e -> {
            primaryStage.close();
            start(new Stage());
        });

        transactionGrid.getChildren().addAll(balanceLabel, amountLabel, amountField, withdrawButton, depositButton, logoutButton);

        Scene transactionScene = new Scene(transactionGrid, 300, 200);
        primaryStage.setScene(transactionScene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Stage alertStage = new Stage();
        alertStage.setTitle("Alert");
        Label label = new Label(message);
        Scene scene = new Scene(label, 200, 100);
        alertStage.setScene(scene);
        alertStage.show();
    }

    private void updateBalanceLabel(Label balanceLabel) {
        balanceLabel.setText("Balance: $" + balance);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

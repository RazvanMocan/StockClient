package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class StockController {
    @FXML
    private TextField nrStocks;
    @FXML
    private TextField priceStocks;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader input;

    private static String clientType;

    public void setClientType(String clientType) {
        StockController.clientType = clientType;
    }

    public void setCommunication(Socket socket, PrintWriter writer, BufferedReader input) {
        this.socket = socket;
        this.writer = writer;
        this.input = input;
        System.out.println(writer);
        System.out.println(input);
        System.out.println(socket);
    }

    public void startTransaction() {
        writer.println("offer");
        writer.println(clientType + " " + socket.getPort());
        writer.println(nrStocks.getText());
        writer.println(priceStocks.getText());
    }

    public void getBuyOffers() {
        writer.println("Buy offers");
        display();
    }

    public void getSellOffers() {
        writer.println("Sell offers");
        display();
    }


    public void getAllTransactions() {
        writer.println("Transactions");
        display();
    }

    private void display() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();

            List<String> l = new ArrayList<>();
            int size = Integer.parseInt(input.readLine());
            for (int i = 0; i < size; i++) {
                l.add(input.readLine());
            }

            ListController listController = fxmlLoader.getController();
            listController.updateList(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

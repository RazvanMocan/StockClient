package com.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StockController {
    @FXML
    private TextField nrStocks;
    @FXML
    private TextField priceStocks;

    @FXML
    private Label nrOfStocksLabel;

    @FXML
    private Label priceOfStocksLabel;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader input;

    private static String clientType;
    private int index = -1;

    public void initialize() {
        System.out.println("init");
        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    if (input.ready()) {
                        String s = input.readLine();
                        if (!s.startsWith("notif")) {
                            int size = Integer.parseInt(s);
                            ArrayList<String> l = new ArrayList<>();
                            for (int i = 0; i < size; i++) {
                                l.add(input.readLine());
                            }
                            Platform.runLater(() -> display(size, l));
                        } else
                            Platform.runLater(() -> notif(s.replace("notif", "")));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void notif(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setContentText(str);
        alert.show();
    }

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

        if(this.validateFields()) {
            writer.println("offer");
            String msg = clientType +  socket.getLocalPort();

            if (index != -1)
                msg += " index " + index;
            writer.println(msg);
            writer.println(nrStocks.getText());
            writer.println(priceStocks.getText());

            nrStocks.clear();
            priceStocks.clear();
            index = -1;

            nrOfStocksLabel.setVisible(false);
            priceOfStocksLabel.setVisible(false);
        }

    }

    public void getBuyOffers() {
        getOffers("Buy offers");
    }

    public void getSellOffers() {
        getOffers("Sell offers");
    }

    public void getAllTransactions() {
        getOffers("Transactions");
    }
    public void getAllOffers() {
        getOffers("All offers");
    }

    public void getAllOffersMine() {
        getOffers("My offers");
    }

    private void getOffers(String s) {
        writer.println(s);
        if (!s.equals("My offers"))
            index = -1;
    }

    private boolean validateFields() {
        try {
            int nrOfStocks = Integer.parseInt(nrStocks.getText());
            if(nrOfStocks > 0) {
                try {
                    int priceOfStocks = Integer.parseInt(priceStocks.getText());
                    if (priceOfStocks > 0) {
                        return true;
                    } else {
                        this.setLabelRed(priceOfStocksLabel);
                    }
                } catch (Exception e) {
                    this.setLabelRed(priceOfStocksLabel);
                }
            } else {
                this.setLabelRed(nrOfStocksLabel);
            }
        } catch (Exception e) {
            this.setLabelRed(nrOfStocksLabel);
        }

        return false;
    }

    private void setLabelRed(Label label) {
        label.setVisible(true);
        label.setText("Invalid input");
        label.setTextFill(Color.RED);
    }

    private void display(int size, List l) {
        System.out.println("display");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Exchange Stocks");
            stage.setScene(new Scene(root1));

            ListController listController = fxmlLoader.getController();
            listController.updateList(l);

            stage.showAndWait();

            index = listController.selectedItem();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

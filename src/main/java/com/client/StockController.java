package com.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

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
    
    @FXML
    private Label nrOfStocksLabel;
    
    @FXML
    private Label priceOfStocksLabel;
    
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader input;

    private static String clientType;
    private int index = -1;

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
        display();
        if (!s.equals("My offers"))
            index = -1;
    }
    
    private boolean validateFields() {
    	try {
			 Integer nrOfStocks = Integer.valueOf(nrStocks.getText());
			 if(nrOfStocks > 0) {
				 try {
					 Integer priceOfStocks = Integer.valueOf(priceStocks.getText());
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

    private void display() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/list.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("Exchange Stocks");
            stage.setScene(new Scene(root1));

            List<String> l = new ArrayList<>();
            int size = Integer.parseInt(input.readLine());
            for (int i = 0; i < size; i++) {
                l.add(input.readLine());
            }

            ListController listController = fxmlLoader.getController();
            listController.updateList(l);

            stage.showAndWait();

            index = listController.selectedItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Random;
import java.util.*;
public class Controller {
    private static final int port = 5000;
    private static String host = "localhost";

    private static Socket socket;
    private static PrintWriter writer;
    private static BufferedReader input;


    @FXML
    private Button Seller;

    static void closeConnections()  {
        try {
            writer.println("end");
            input.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectType(Event e) {
        String clientType = ((Control) e.getSource()).getId();
        System.out.println(clientType);
        try {
            socket = new Socket(host, port);
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(clientType);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stocks.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();

            StockController controller = fxmlLoader.getController();

            controller.setClientType(clientType);
            controller.setCommunication(socket, writer, input);

            stage.setScene(new Scene(root1, 300, 300));
        } catch (Exception exc) {
            exc.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while establishing connection" + exc.getLocalizedMessage()).show();
        }
    }

    public void selectServer() {
        TextInputDialog dialog = new TextInputDialog(host);
        dialog.setTitle("Select server IP");
        dialog.setHeaderText("Please choose the IP of the server");
        dialog.setContentText("Server IP:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(s -> host = s);
    }
}

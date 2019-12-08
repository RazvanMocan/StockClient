package main.java.com.client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;

public class ListController {
    @FXML
    private ListView<String> listView;

    private void initialize() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void updateList(List<String> list) {
        List<String> l = listView.getItems();
        l.clear();
        l.addAll(list);
    }



    public int selectedItem() {
        return listView.getSelectionModel().getSelectedIndex();
    }

    public void close(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Stage stage = (Stage)listView.getScene().getWindow();
            stage.close();
        }
    }
}


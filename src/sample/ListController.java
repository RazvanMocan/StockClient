package sample;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class ListController {
    @FXML
    private ListView<String> listView;

    public void updateList(List<String> list) {
        List<String> l = listView.getItems();
        l.clear();
        l.addAll(list);
    }
}


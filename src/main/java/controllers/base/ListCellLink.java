package controllers.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import model.Link;
import model.LinkList;
import service.LinkService;

import java.io.IOException;

public class ListCellLink extends ListCell<Link> {
    public AnchorPane pane;
    public Label textDescription;
    public Button btnDelete;
    public Button btnEdit;
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Link emp, boolean empty) {
        super.updateItem(emp, empty);

        if(empty || emp == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/views/listCellLink.fxml"));
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ListCellLink controller = mLLoader.getController();
            if (controller.textDescription != null)
                controller.textDescription.setText(emp.toString());
            controller.btnDelete.setOnAction(event -> {
                getListView().getItems().remove(getItem());
                new LinkService().saveLinks(new LinkList(getListView().getItems()));
            });
            setText(null);
            setGraphic(controller.pane);
        }

    }
}

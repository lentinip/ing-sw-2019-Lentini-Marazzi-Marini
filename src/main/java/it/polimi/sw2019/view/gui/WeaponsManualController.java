package it.polimi.sw2019.view.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class WeaponsManualController {

    /* Attributes */

    @FXML
    AnchorPane anchorPane;

    @FXML
    Pane pane;

    @FXML
    private StackPane stackPane;

    private List<ImageView> pages = new ArrayList<>();



    /* Methods */

    public void initialize(){
        Pagination pagination = new Pagination(4);
        pagination.setMaxPageIndicatorCount(4);

        imageInitialization();

        pagination.setPageFactory(new Callback<Integer, Node>() {
            public Node call(Integer pageIndex) {
                stackPane = new StackPane();
                stackPane.getChildren().add(pages.get(pageIndex));
                return stackPane;
            }
        });

        AnchorPane.setTopAnchor(pagination, 0.0);
        AnchorPane.setLeftAnchor(pagination, 0.0);
        AnchorPane.setRightAnchor(pagination, 0.0);
        AnchorPane.setBottomAnchor(pagination, 0.0);
        anchorPane.getChildren().add(pagination);

    }

    public void imageInitialization(){
        for (int i = 0; i<4; i++){
            String url = String.format("/images/weaponsManual/WeaponsManualPage%d.png", i);
            ImageView page = new ImageView(new Image(url));
            page.setPreserveRatio(true);
            page.setSmooth(true);
            page.setCache(true);
            page.setFitHeight(779);
            page.setFitWidth(760);
            pages.add(page);
        }
    }

    public void configure(){
        Scene scene = pane.getScene();

        double origW = 565;
        double origH = 850;

        pane.setMinSize(origW, origH);
        pane.setMaxSize(origW, origH);

        NumberBinding maxScale = Bindings.min(scene.widthProperty().divide(origW),scene.heightProperty().divide(origH));
        pane.scaleXProperty().bind(maxScale);
        pane.scaleYProperty().bind(maxScale);
    }

}

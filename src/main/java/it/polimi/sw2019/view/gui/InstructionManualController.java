package it.polimi.sw2019.view.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class InstructionManualController {

    /* Attributes */

    @FXML
    AnchorPane anchorPane;

    @FXML
    private StackPane stackPane;

    private List<ImageView> pages = new ArrayList<>();



    /* Methods */

    public void initialize(){
        Pagination pagination = new Pagination(12);
        pagination.setMaxPageIndicatorCount(12);

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
        for (int i = 0; i<12; i++){
            String url = String.format("/images/instructionManual/InstructionPage%d.png", i);
            ImageView page = new ImageView(new Image(url));
            page.setPreserveRatio(true);
            page.setSmooth(true);
            page.setCache(true);
            page.setFitHeight(779);
            page.setFitWidth(760);
            pages.add(page);
        }
    }

}
package stickman.view;

import stickman.model.engine.GameEngine;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ParallaxBackground implements BackgroundDrawer {

    private Rectangle floor;
    private double floorYPos;
    private double width;
    private int heroLife;
    private Image[] images;
    private ImageView[] backgroundViews;
    private ImageView gameOver;
    private ImageView gameFinished;
    private List<ImageView> hearts = new ArrayList<>();
    private double[] parallaxEffect;

    @Override
    public void draw(GameEngine model, Pane pane) {
        this.width = pane.getWidth();
        double height = pane.getHeight();
        double floorHeight = model.getCurrentLevel().getFloorHeight();

        this.images = new Image[9];

        images[0] = new Image("media/landscape_0000_1_trees.png");
        images[1] = new Image("media/landscape_0001_2_trees.png");
        images[2] = new Image("media/landscape_0002_3_trees.png");
        images[3] = new Image("media/landscape_0003_4_mountain.png");
        images[4] = new Image("media/landscape_0004_5_clouds.png");
        images[5] = new Image("media/landscape_0005_6_background.png");
        images[6] = new Image("media/heart.png");
        images[7] = new Image("media/game-over.png");
        images[8] = new Image("media/trophy.png");

        this.parallaxEffect = new double[6];

        parallaxEffect[0] = 0.5;
        parallaxEffect[1] = 0.4;
        parallaxEffect[2] = 0.2;
        parallaxEffect[3] = 0.05;
        parallaxEffect[4] = 0.05;
        parallaxEffect[5] = 0.0;

        this.backgroundViews = new ImageView[7];

        for (int i = 0;i < 6; i++) {
            backgroundViews[i] = new ImageView(images[i]);
            double rawHeight = images[i].getHeight();
            double rawWidth = images[i].getWidth() / 2; // images are all double stitched

            backgroundViews[i].setViewOrder(1001.0 + i);
            backgroundViews[i].setFitHeight(height);
            backgroundViews[i].setFitWidth(width);
            backgroundViews[i].setX(0);
            backgroundViews[i].setY(0);
            backgroundViews[i].setViewport(new Rectangle2D(0, 0, rawWidth, rawHeight));

            pane.getChildren().add(backgroundViews[i]);
        }

        floor = new Rectangle(0, floorHeight, width, height - floorHeight);
        floorYPos = floorHeight;
        floor.setFill(Paint.valueOf("#1d2b38"));
        floor.setViewOrder(1000.0);
;       pane.getChildren().add(floor);
    }

    @Override
    public void update(double xViewportOffset,double yViewportOffset) {
        floor.setY(floorYPos - yViewportOffset);
        for (int i = 0;i < 6; i++) {
            double rawHeight = images[i].getHeight();
            double rawWidth = images[i].getWidth() / 2; // images are all double stitched
            double widthScale = rawWidth / width;
            double translation = (xViewportOffset * widthScale * parallaxEffect[i]) % rawWidth;
            backgroundViews[i].setViewport(new Rectangle2D(translation, 0, rawWidth, rawHeight));
        }
    }
}

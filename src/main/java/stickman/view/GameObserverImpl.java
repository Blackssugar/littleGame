package stickman.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import stickman.model.engine.GameSubject;

import java.util.ArrayList;
import java.util.List;


public class GameObserverImpl implements Observer {
    protected GameSubject subject;
    private Pane pane;
    private Text scoreView;
    private Text savingStatus;
    private boolean imageSet;
    private ImageView gameFinished;
    private ImageView gameOver;
    private List<ImageView> life;

    public GameObserverImpl(GameSubject subject, Pane pane) {
        this.subject = subject;
        this.pane = pane;
        scoreView = new Text("");
        scoreView.setLayoutY(30);
        scoreView.setLayoutX(450);
        scoreView.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        scoreView.setFill(Color.color(231.0/255, 76.0/255, 60.0/255));
        pane.getChildren().add(scoreView);

        savingStatus = new Text("");
        savingStatus.setLayoutY(60);
        savingStatus.setLayoutX(450);
        savingStatus.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        savingStatus.setFill(Color.color(236.0/255, 64.0/255, 122.0/255));
        pane.getChildren().add(savingStatus);

        gameFinished = new ImageView(new Image("media/award.png"));
        gameFinished.setPreserveRatio(true);
        gameFinished.setFitHeight(150);
        gameFinished.setFitWidth(150);
        gameFinished.setX(pane.getWidth()/2-gameFinished.getFitWidth()/2);
        gameFinished.setY(pane.getHeight()/2-gameFinished.getFitHeight()/2);

        gameOver = new ImageView(new Image("/media/game-over.png"));
        gameOver.setPreserveRatio(true);
        gameOver.setFitHeight(150);
        gameOver.setFitWidth(150);
        gameOver.setX(pane.getWidth()/2-gameOver.getFitWidth()/2);
        gameOver.setY(pane.getHeight()/2-gameOver.getFitHeight()/2);
        imageSet = false;

        life = new ArrayList<>();
        int distance = 20;
        for(int i=0;i<subject.getHeroLife();i++) {
            ImageView heart = new ImageView("media/heart.png");
            heart.setFitHeight(16);
            heart.setPreserveRatio(true);
            heart.setX(20+distance);
            heart.setY(20);
            distance += 20;
            pane.getChildren().add(heart);
            life.add(heart);
        }
    }
    public void update() {
        if(subject.getHeroLife()<life.size()) {
            life.get(life.size()-1).setImage(null);
            life.remove(life.size()-1);
        }
        String savingText = "Game Saved: " + subject.isSaved();
        savingStatus.setText(savingText);
        if(subject.isWinning() && !imageSet) {
            pane.getChildren().add(gameFinished);
            imageSet = true;
        }
        else if(subject.isDead() && !imageSet) {
            pane.getChildren().add(gameOver);
            imageSet = true;
        }
        else if(!subject.isDead() && !subject.isWinning()) {
            String scoreText = "Current Score: " + String.valueOf(subject.getCurrentScore())
                    + "\nAccumulated Score: " + String.valueOf(subject.getTotalScore());
            scoreView.setText(scoreText);
        }
    };
}
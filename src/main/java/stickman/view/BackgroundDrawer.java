package stickman.view;

import javafx.scene.layout.Pane;
import stickman.model.engine.GameEngine;

/** The BackgroundDrawer interface */
public interface BackgroundDrawer {

  /**
   * Draws a background texture onto the game window.
   *
   * @param model the GameEngine being used for level execution.
   * @param pane the Pane being used for display.
   */
  void draw(GameEngine model, Pane pane);

  /**
   * Shift the background on the x-axis
   *
   * @param xViewportOffset the amount of x-displacement.
   */
  void update(double xViewportOffset,double yViewportOffset);
}

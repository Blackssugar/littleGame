package stickman.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import stickman.model.engine.GameEngine;
import stickman.model.entity.Entity;

/** A class for drawing objects onto the game window. */
public class GameDrawer {

  private GameEngine model;
  private Pane pane;
  private final int width;
  private final int height;

  private BackgroundDrawer backgroundDrawer;
  private Observer scoreObserver;

  private double xViewportOffset = 0.0;
  private double yViewportOffset = 0.0;
  private static final double Y_VIEWPORT_MARGIN = 200.0;
  private static final double VIEWPORT_MARGIN = 280.0;

  private List<EntityView> entityViews;

  public GameDrawer(GameEngine model, Pane pane, BackgroundDrawer backgroundDrawer, int width, int height, Observer scoreObserver) {

    this.model = model;
    this.pane = pane;
    this.backgroundDrawer = backgroundDrawer;
    this.width = width;
    this.height = height;
    this.scoreObserver = scoreObserver;

    this.entityViews = new ArrayList<>();
  }

  /** Draws and updates all currently displayed EntityView objects. */
  public void draw() {
    model.tick();

    scoreObserver.update();

    List<Entity> entities = model.getCurrentLevel().getEntities();

    for (EntityView entityView : entityViews) {
      entityView.markForDelete();
    }

    double heroXPos = model.getCurrentLevel().getHeroX();
    double heroYPos = model.getCurrentLevel().getHeroY();

    heroXPos -= xViewportOffset;

    if (heroXPos < VIEWPORT_MARGIN) {
      if (xViewportOffset >= 0) { // Don't go further left than the start of the level
        xViewportOffset -= VIEWPORT_MARGIN - heroXPos;
        if (xViewportOffset < 0) {
          xViewportOffset = 0;
        }
      }
    } else if (heroXPos > width - VIEWPORT_MARGIN) {
      xViewportOffset += heroXPos - (width - VIEWPORT_MARGIN);
    }

    if (heroYPos <= Y_VIEWPORT_MARGIN) {
      yViewportOffset = heroYPos - Y_VIEWPORT_MARGIN;
    } else {
      yViewportOffset = 0;
    }

    backgroundDrawer.update(xViewportOffset,yViewportOffset);

    for (Entity entity : entities) {
      boolean notFound = true;
      for (EntityView view : entityViews) {
        if (view.matchesEntity(entity)) {
          notFound = false;
          view.updateView(xViewportOffset, yViewportOffset);
          break;
        }
      }
      if (notFound) {
        EntityView entityView = new EntityViewImpl(entity);
        entityViews.add(entityView);
        pane.getChildren().add(entityView.getNode());
      }
    }

    for (EntityView entityView : entityViews) {
      if (entityView.isMarkedForDelete()) {
        pane.getChildren().remove(entityView.getNode());
      }
    }
    entityViews.removeIf(EntityView::isMarkedForDelete);
  }

  public void removeView(Entity e) {
    entityViews.removeIf(v -> v.matchesEntity(e));
  }

  // exposed for testing
  public double getxViewportOffset() {
    return xViewportOffset;
  }

  // exposed for testing
  public double getyViewportOffset() {
    return yViewportOffset;
  }
}

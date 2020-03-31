package stickman.model.entity.impl;

import stickman.model.level.Level;
import stickman.view.GameManager;

/** The cloud Entity type. */
public class CloudEntity extends AbstractEntity {

  private double velocity;

  public CloudEntity(
      String imagePath,
      double xPos,
      double yPos,
      double width,
      double height,
      Layer layer,
      double velocity) {
    super(imagePath, xPos, yPos, width, height, layer);
    this.velocity = (velocity / 1000) * GameManager.FRAMEFRATE_MS;
  }

  @Override
  public void move(Level level) {
    this.setXPos(this.getXPos() + this.velocity);
  }

  @Override
  public CloudEntity clone() {

    return new CloudEntity(this.getImagePath(),this.getXPos(),this.getYPos(),
            this.getWidth(),this.getHeight(),this.getLayer(),
            this.velocity/GameManager.FRAMEFRATE_MS*1000);
  }
}

package stickman.model.entity.impl;

import javafx.geometry.Rectangle2D;
import stickman.model.entity.Entity;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The base implementation for all Entity-type objects. */
public abstract class AbstractEntity implements Entity {

  private String imagePath;
  private double xPos;
  private double yPos;
  private double width;
  private double height;
  private Layer layer;

  public AbstractEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    this.imagePath = imagePath;
    this.xPos = xPos;
    this.yPos = yPos;
    this.width = width;
    this.height = height;
    this.layer = layer;
  }

  @Override
  public String getImagePath() {
    return imagePath;
  }

  @Override
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public double getXPos() {
    return this.xPos;
  }

  @Override
  public void setXPos(double xPos) {
    this.xPos = xPos;
  }

  @Override
  public double getYPos() {
    return this.yPos;
  }

  @Override
  public void setYPos(double yPos) {
    this.yPos = yPos;
  }

  @Override
  public double getWidth() {
    return this.width;
  }

  @Override
  public double getHeight() {
    return this.height;
  }

  @Override
  public Layer getLayer() {
    return this.layer;
  }

  @Override
  public Rectangle2D getBoundary() {
    return new Rectangle2D(xPos, yPos, width, height);
  }

  @Override
  public boolean intersects(Entity other) {
    return other.getBoundary().intersects(this.getBoundary());
  }

  @Override
  public void move(Level level) {
   // System.out.println(imagePath);
    // no operation unless explicitly overridden
  }

  @Override
  public boolean handleCollision(Entity otherx, CollisionDirection direction, Level level) {
    // no operation unless explicitly overridden
    return false;
  }

  @Override
  public AbstractEntity clone() {
    return null;
  }
}

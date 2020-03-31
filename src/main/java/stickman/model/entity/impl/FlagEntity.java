package stickman.model.entity.impl;

/** The flag Entity type. */
public class FlagEntity extends AbstractEntity {

  public FlagEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    super(imagePath, xPos, yPos, width, height, layer);
  }

  @Override
  public FlagEntity clone() {
    return new FlagEntity(this.getImagePath(),this.getXPos(),this.getYPos(),
            this.getWidth(),this.getHeight(),this.getLayer());
  }
}

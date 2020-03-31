package stickman.model.entity.impl;

/** The tile/platform Entity type. */
public class TileEntity extends AbstractEntity {

  public TileEntity(
      String imagePath, double xPos, double yPos, double width, double height, Layer layer) {
    super(imagePath, xPos, yPos, width, height, layer);
  }
  @Override
  public TileEntity clone() {
    return new TileEntity(this.getImagePath(),this.getXPos(),this.getYPos(),
            this.getWidth(),this.getHeight(),this.getLayer());
  }
}

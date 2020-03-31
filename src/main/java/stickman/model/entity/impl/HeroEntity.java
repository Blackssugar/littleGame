package stickman.model.entity.impl;

import stickman.Constants;
import stickman.model.entity.Animatable;
import stickman.model.entity.Controllable;
import stickman.model.entity.Damageable;
import stickman.model.entity.Entity;
import stickman.model.entity.movement.JumpMovementHandler;
import stickman.model.entity.movement.MovementHandler;
import stickman.model.entity.movement.MovementTriggerHandler;
import stickman.model.entity.movement.SimpleMovementHandler;
import stickman.model.level.Level;
import stickman.model.level.collision.CollisionDirection;

/** The game's hero (stickman) Entity type. */
public class HeroEntity extends AbstractEntity implements Controllable, Damageable, Animatable {

  private final double FRICTION = 0.99;
  private final double GRAVITY = 0.05;
  private final double RUN_SPEED = 1;
  private final double JUMP_STRENGTH = 3.5;
  private final double TERMINAL_VELOCITY = 3;

  private boolean movingRight;
  private boolean movingLeft;
  private boolean jumping;
  private boolean onGround;

  private double xSpeed;
  private double ySpeed;
  private int Life;

  private double spawnXPos;
  private double spawnYPos;

  private MovementHandler movementHandler;
  private MovementTriggerHandler jumpHandler;
  private int frames;

  public HeroEntity(
      String imagePath,
      double xPos,
      double yPos,
      double width,
      double height,
      Layer layer,
      int Life) {

    super(imagePath, xPos, yPos, width, height, layer);

    movementHandler = new SimpleMovementHandler();
    jumpHandler = new JumpMovementHandler();
    this.spawnXPos = xPos;
    this.spawnYPos = yPos;
    this.Life = Life;
  }

  /**
   * Returns whether or not the hero will move right at the next tick
   *
   * @return Whether the hero will move right
   */
  public boolean isMovingRight() {
    return this.movingRight;
  }

  /**
   * Toggles the hero's movement in the positive X direction.
   *
   * @param set whether the hero should attempt to move in the direction.
   * @return whether the hero will move in the direction.
   */
  public boolean setMovingRight(boolean set) {
    if(!set && movingRight) {
      this.movingRight = false;
      alterImagePath("RIGHT");
    }
    this.movingRight = set;
    if(set) {
      alterImagePath("RIGHT");
    }
    return this.movingRight;
  }

  /**
   * Returns whether or not the hero will move left at the next tick
   *
   * @return whether the hero will move left
   */
  public boolean isMovingLeft() {
    return this.movingLeft;
  }

  /**
   * Toggles the hero's movement in the negative X direction.
   *
   * @param set Whether the hero should attempt to move in the direction
   * @return Whether the hero will move in the direction
   */
  public boolean setMovingLeft(boolean set) {
    if (getXPos() <= 0) {
      this.movingLeft = false;
      if(set) {
        alterImagePath("LEFT");
      }
      return false;
    }
    if(movingLeft && !set) {
      this.movingLeft = set;
      alterImagePath("LEFT");
    }
    this.movingLeft = set;
    if(set) {
      alterImagePath("LEFT");
    }
    return this.movingLeft;
  }

  /**
   * Returns whether or not the hero will jump at the next tick
   *
   * @return Whether the hero will jump
   */
  public boolean isJumping() {
    return this.jumping;
  }

  /**
   * Toggles the the hero's jump movement.
   *
   * @param set Whether the hero should attempt to jump.
   * @return Whether the hero will jump.
   */
  public boolean setJumping(boolean set) {

    if (set) {
      if (!jumping) {
        this.jumping = true;
        return true;
      }
    } else {
      this.jumping = false;
    }

    return false;
  }

  @Override
  public boolean isOnGround() {
    return this.onGround;
  }

  @Override
  public void setOnGround(boolean set) {

    if (set) {
      this.jumping = false;
    }

    this.onGround = set;
  }

  @Override
  public double getRunSpeed() {
    return this.RUN_SPEED;
  }

  @Override
  public double getJumpStrength() {
    return this.JUMP_STRENGTH;
  }

  // exposed for testing
  public double getYSpeed() {
    return this.ySpeed;
  }

  @Override
  public void setYSpeed(double yVelocity) {
    this.ySpeed = yVelocity;
  }

  @Override
  public void accelerate(double dx, double dy) {
    xSpeed += dx;
    ySpeed += dy;
  }

  @Override
  public void step(double dx, double dy) {
    this.setXPos(this.getXPos() + dx);
    this.setYPos(this.getYPos() + dy);
  }

  @Override
  public void update() {

    step(xSpeed, ySpeed);

    xSpeed *= this.FRICTION;
    ySpeed *= this.FRICTION;

    if (ySpeed >= TERMINAL_VELOCITY && onGround) {
      return;
    }

    accelerate(0, this.GRAVITY);
  }

  @Override
  public void move(Level level) {
    animate();
    if (jumping) {
      jumpHandler.trigger(level, this);
    }
    movementHandler.updateMovement(level, this);
  }

  /**
   * This method is used to change the image when there is a change in action
   */
  public void alterImagePath(String direction) {
    if(isMovingLeft()) {
      setImagePath("media/ch_walk5.png");
    }
    else if(isMovingRight()) {
      setImagePath("media/ch_walk1.png");
    } else {
      if("LEFT".equals(direction)) {
        setImagePath("media/ch_stand4.png");
      } else {
        setImagePath("media/ch_stand1.png");
      }
    }
  }

  @Override
  public boolean handleCollision(Entity other, CollisionDirection direction, Level level) {

    if (other instanceof EnemyEntity) {

      if (direction.equals(CollisionDirection.TOP)) {
        return false;
      }

      this.takeDamage(level);
      return true;
    }

    if (other instanceof FlagEntity) {
      level.finish("FLAG");
      return true;
    }

    if (other instanceof TileEntity) {

      if (!direction.equals(CollisionDirection.TOP)) {
        return false;
      }

      if (this.ySpeed < 0) {
        return false;
      }

      this.setYPos(other.getYPos() - this.getHeight());
      this.setOnGround(true);

      return true;
    }

    return false;
  }

  @Override
  public void die(Level level) {
    level.finish("DEATH");
  }

  @Override
  public void takeDamage(Level level) {

    int newLife = Life - 1;
    this.Life = newLife;

    if (newLife <= 0) {
      die(level);
    } else {
      this.setXPos(this.spawnXPos);
      this.setYSpeed(this.spawnYPos);
    }
  }

  public int getLife() {
    return Life;
  }

  @Override
  public void animate() {
    frames++;
    if (frames % 20 == 0) {
      frames = 0;
      switch (getImagePath()) {
        case "media/ch_stand1.png":
          setImagePath("media/ch_stand2.png");
          break;
        case "media/ch_stand2.png":
          setImagePath("media/ch_stand3.png");
          break;
        case "media/ch_stand4.png":
          setImagePath("media/ch_stand5.png");
          break;
        case "media/ch_stand5.png":
          setImagePath("media/ch_stand6.png");
          break;
        case "media/ch_stand6.png":
          setImagePath("media/ch_stand4.png");
          break;
        case "media/ch_walk1.png":
          setImagePath("media/ch_walk2.png");
          break;
        case "media/ch_walk2.png":
          setImagePath("media/ch_walk3.png");
          break;
        case "media/ch_walk3.png":
          setImagePath("media/ch_walk4.png");
          break;
        case "media/ch_walk4.png":
          setImagePath("media/ch_walk1.png");
          break;
        case "media/ch_walk5.png":
          setImagePath("media/ch_walk6.png");
          break;
        case "media/ch_walk6.png":
          setImagePath("media/ch_walk7.png");
          break;
        case "media/ch_walk7.png":
          setImagePath("media/ch_walk8.png");
          break;
        case "media/ch_walk8.png":
          setImagePath("media/ch_walk5.png");
          break;
        default:
          setImagePath("media/ch_stand1.png");
      }
    }
  }

  /**
   * An enum containing the possible hero sizes, and internal helper methods for dealing with the
   * size.
   */
  public enum HeroSize {
    TINY,
    NORMAL,
    LARGE,
    GIANT;

    /**
     * Get a double width value based on HeroSize.
     *
     * @return the HeroSize width.
     */
    public double getWidth() {

      switch (this) {
        case TINY:
          return Constants.HERO_DEFAULT_WIDTH;
        case LARGE:
          return Constants.HERO_DEFAULT_WIDTH * 3;
        case GIANT:
          return Constants.HERO_DEFAULT_WIDTH * 4;
        default:
          return Constants.HERO_DEFAULT_WIDTH * 2;
      }
    }

    /**
     * Get a double height value based on HeroSize
     *
     * @return the HeroSize height.
     */
    public double getHeight() {

      switch (this) {
        case TINY:
          return Constants.HERO_DEFAULT_HEIGHT;
        case LARGE:
          return Constants.HERO_DEFAULT_HEIGHT * 3;
        case GIANT:
          return Constants.HERO_DEFAULT_HEIGHT * 4;
        default:
          return Constants.HERO_DEFAULT_HEIGHT * 2;
      }
    }
  }

  @Override
  public HeroEntity clone() {
    HeroEntity clone = new HeroEntity(this.getImagePath(),this.spawnXPos,this.spawnYPos,
            this.getWidth(),this.getHeight(),this.getLayer(),this.Life);
    clone.setJumping(jumping);
    clone.setMovingLeft(movingLeft);
    clone.setMovingRight(movingRight);
    clone.setOnGround(onGround);
    clone.setXPos(this.getXPos());
    clone.setYPos(this.getYPos());
    return clone;
  }
}

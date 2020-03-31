package stickman.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import stickman.config.parser.LevelSettingsParser;

/** A data storage object for storing Level and Entity data */
public class ConfigurationProvider {

  private JSONArray levels;
  private LevelSettings levelSettings;
  private int levelId;
  private final int levelCount;

  public ConfigurationProvider(String configPath) {
    levelId = 0;
    try (Reader fileReader =
        new InputStreamReader(getClass().getResourceAsStream("/" + configPath))) {
      JSONObject configObject = (JSONObject) new JSONParser().parse(fileReader);
      levels = (JSONArray) configObject.get("levels");
      levelCount = levels.size();
      fileReader.close();
    } catch (NullPointerException | IOException | ParseException e) {
      System.out.println("Error: Configuration file missing or malformed.");
      System.out.println("Exiting Program.");
      throw new IllegalArgumentException("Missing or malformed configuration file");
    }
  }

  public void selectLevelToRead(int levelId) {
    if(levelId < levelCount-1 || this.levelId != levelId) {
      this.levelId = levelId;
      levelSettings = null;
    }
  }

  public int getLevelCount() { return levelCount; }

  public int getCurrentLevelId() { return levelId; }

  /**
   * Gets the LevelSettings object, or Creates the LevelSettings object if non-existent.
   *
   * @return the created/stored LevelSettings object.
   */
  public LevelSettings getLevelData() {
    if(levelSettings==null) {
      JSONObject configObject = (JSONObject) levels.get(levelId);
      levelSettings = new LevelSettingsParser().parse(configObject);
    }
    return levelSettings;
  }
}

package persistance;

import business.entities.DDBBInfo;
import org.json.JSONObject;
import persistance.exceptions.ConfigFileNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigDatabaseDAO implements ConfigDAO {
    private final String path;
    public ConfigDatabaseDAO(String path) {
        this.path = path;
    }
    @Override
    public DDBBInfo readConfigJson() throws IOException, ConfigFileNotFoundException {
        if (!new File(path).exists()) { throw new ConfigFileNotFoundException("File not found"); }

        JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Path.of(path))));
        return new DDBBInfo(jsonObject.getInt("port"),
                            jsonObject.getString("ip"),
                            jsonObject.getString("name"),
                            jsonObject.getString("user"),
                            jsonObject.getString("password"));
    }
}

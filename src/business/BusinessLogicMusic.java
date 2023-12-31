package business;

import business.entities.Music;
import business.entities.Song;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import persistance.APIAccess;
import persistance.SongDAO;
import persistance.exceptions.ApiServerException;

import java.util.List;

public class BusinessLogicMusic {
    private final SongDAO songDAO;
    private final APIAccess apiAccess;

    /**
     * Constructor
     * @param songDAO Song DAO
     * @param apiAccess API access
     */
    public BusinessLogicMusic(SongDAO songDAO, APIAccess apiAccess) {
        this.songDAO = songDAO;
        this.apiAccess = apiAccess;
    }

    /**
     * List music
     * @return List of music
     */
    public List<Song> listMusic(){ return songDAO.readSongList(); }

    /**
     * get music lyrics from api
     * @param nom Nombre de la cancion
     * @param artist Nombre del artista
     * @return Lyrics
     * @throws ApiServerException
     */
    public String getMusicLyricsFromApi(String nom, String artist) throws ApiServerException {
        JsonObject jsonObject = new Gson().fromJson(apiAccess.getFromUrl(artist, nom), JsonObject.class);
        if (jsonObject.has("lyrics")) {
            return jsonObject.get("lyrics").getAsString();
        }
        return "Error: " + jsonObject.get("error").getAsString();
    }

    /**
     * Search music
     * @param searchText Text to search
     * @return List of music
     */
    public List<Song> searchMusic(String searchText) {
        return songDAO.searchAllMusicFromSearchText(searchText);
    }
}

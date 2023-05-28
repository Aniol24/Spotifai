package presentation.controller;

import business.BusinessLogicMusic;
import business.BusinessLogicPlayList;
import presentation.view.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaylistSongsController implements PlaylistSongsViewListener {
    private final PlaylistSongsView playlistSongsView;
    private final BusinessLogicPlayList businessLogicPlayList;
    private final ViewsController viewsController;
    private final AddSongToPlaylistView addSongToPlaylistView;
    private final DeleteSongFromPlaylistView deleteSongFromPlaylistView;
    private final BusinessLogicMusic businessLogicMusic;
    private final PlayMusicController playMusicController;

    /**
     * Playlist songs controller
     * @param playlistSongsView
     * @param businessLogicPlayList
     * @param viewsController
     * @param addSongToPlaylistView
     * @param deleteSongFromPlaylistView
     * @param businessLogicMusic
     * @param playMusicController
     */
    public PlaylistSongsController(PlaylistSongsView playlistSongsView, BusinessLogicPlayList businessLogicPlayList, ViewsController viewsController, AddSongToPlaylistView addSongToPlaylistView, DeleteSongFromPlaylistView deleteSongFromPlaylistView, BusinessLogicMusic businessLogicMusic, PlayMusicController playMusicController) {
        this.playlistSongsView = playlistSongsView;
        this.businessLogicPlayList = businessLogicPlayList;
        this.viewsController = viewsController;
        this.addSongToPlaylistView = addSongToPlaylistView;
        this.deleteSongFromPlaylistView = deleteSongFromPlaylistView;
        this.businessLogicMusic = businessLogicMusic;
        this.playMusicController = playMusicController;
        tableListenerFunctionality();
        playlistSongsView.setListener(this);
        playlistSongsView.getOptionsButton().addActionListener(e -> playlistSongsView.showOptionsDialog());
        playlistSongsView.getSortAlphaButton().addActionListener(e -> sortSongsAlphabetically());
        playlistSongsView.getSortByUserButton().addActionListener(e -> sortSongsByGenre());
    }

    private void sortSongsByGenre() {
        playlistSongsView.setSongs(businessLogicPlayList.sortSongsByGenre(playlistSongsView.getPlaylistName()));
    }

    private void sortSongsAlphabetically() {
        playlistSongsView.setSongs(businessLogicPlayList.sortSongsAlphabetically(playlistSongsView.getPlaylistName()));
    }

    private void tableListenerFunctionality() {
        playlistSongsView.addTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = playlistSongsView.getTable().getSelectedRow();
                if (selectedRow != -1) {
                    playMusicController.setSongsToMPlayer(businessLogicPlayList.getSongsFromPlaylist(playlistSongsView.getPlaylistName()), true);
                    playMusicController.playSong(playlistSongsView.getTable().getValueAt(selectedRow, 0).toString());
                }
            }
        });
    }

    /**
     * Load songs from api
     * @param playlistName
     */
    public void loadSongsFromApi(String playlistName) {
        playlistSongsView.setSongs(businessLogicPlayList.getSongsFromPlaylist(playlistName));
        playlistSongsView.deactivateAddButton(businessLogicPlayList.isPlaylistFromUser(playlistName));
    }

    /**
     * On add song
     */
    @Override
    public void onAddSong() {
        viewsController.setAddSongToPlaylistView();
        addSongToPlaylistView.setPlaylistName(playlistSongsView.getPlaylistName());
        addSongToPlaylistView.setSongs(businessLogicMusic.listMusic());
    }

    /**
     * On delete song
     */
    @Override
    public void onDeleteSong() {
        viewsController.setDeleteSongFromPlaylistView();
        deleteSongFromPlaylistView.setPlaylistName(playlistSongsView.getPlaylistName());
    }

    /**
     * Set playlist name
     * @param selectedPlaylistName
     */
    public void setPlaylistName(String selectedPlaylistName) {
        playlistSongsView.setPlaylistName(selectedPlaylistName);
    }
}

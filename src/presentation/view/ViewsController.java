package presentation.view;

import presentation.controller.AddSongToPlaylistController;
import presentation.view.Utilities.UIPalette;

import javax.swing.*;
import java.awt.*;

public class ViewsController {
    private final CardLayout cardLayout;

    private CardLayout mainPanelCardLayout;

    private final JFrame window;

    private final JPanel cardPanelInici;

    private JPanel panelSuperiorDerecha;

    private JPanel panelPrincipal;

    public ViewsController(SignInView signInView, SignUpView signUpView, LogOutView logOutView, WelcomeView welcomeView,
                           AddMusicView addMusicView, ListMusicView listMusicView, DeleteMusicView deleteMusicView,
                           MainMenuView mainMenuView, PlayMusicView playMusicView, PlaylistView playlistView,
                           MusicStatisticsView musicStatisticsView, ShowMusicInfoView showMusicInfoView,
                           AddPlaylistView addPlaylistView, PlaylistSongsView playlistSongsView,
                           AddSongToPlaylistView addSongToPlaylistView, DeletePlaylistView deletePlaylistView,
                           DeleteSongFromPlaylistView deleteSongFromPlaylistView, DeleteUserView deleteUserView) {

        ImageIcon spotifyIcon = new ImageIcon("data/img/spotify.png");
        this.window = new JFrame("Espotifai");
        this.window.setIconImage(spotifyIcon.getImage());

        cardLayout = new CardLayout();
        cardPanelInici = new JPanel(cardLayout);
        cardPanelInici.add(signInView.getPanelSignIn(), "signIn");
        cardPanelInici.add(signUpView.getPanelSignup(), "signUp");
        cardPanelInici.add(welcomeView.getWelcomePanel(), "welcome");

        crearPanelPrincipal(mainMenuView, playMusicView, playlistView, listMusicView, logOutView, musicStatisticsView,
                addMusicView, showMusicInfoView, deleteMusicView, addPlaylistView, playlistSongsView,
                addSongToPlaylistView, deletePlaylistView, deleteSongFromPlaylistView, deleteUserView);
    }

    private void crearPanelPrincipal(MainMenuView mainMenuView, PlayMusicView playMusicView, PlaylistView playlistView, ListMusicView listMusicView,
                                     LogOutView logOutView, MusicStatisticsView musicStatisticsView, AddMusicView addMusicView, ShowMusicInfoView showMusicInfoView,
                                     DeleteMusicView deleteMusicView, AddPlaylistView addPlaylistView, PlaylistSongsView playlistSongsView,
                                     AddSongToPlaylistView addSongToPlaylistView, DeletePlaylistView deletePlaylistView, DeleteSongFromPlaylistView deleteSongFromPlaylistView,
                                     DeleteUserView deleteUserView) {

        panelPrincipal = new JPanel(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Peso en la dirección X
        gbc.weighty = 1.0; // Peso en la dirección Y

        JPanel panelSuperiorIzquierda = mainMenuView.getMenuPanel();
        panelSuperiorIzquierda.setBackground(UIPalette.COLOR_PRIMARIO_CLARO .getColor());

        JPanel outView = new JPanel();
        outView.setBackground(UIPalette.COLOR_PRIMARIO.getColor());
        outView.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(100, 0, 0, 0);
        outView.add(deleteUserView.getPanelLogOut(), c);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(70, 0, 100, 0);
        outView.add(logOutView.getContentPane(), c);

        mainPanelCardLayout = new CardLayout();
        panelSuperiorDerecha = new JPanel(mainPanelCardLayout);
        panelSuperiorDerecha.add(playlistView.getContentPane(), "playlist");
        panelSuperiorDerecha.add(listMusicView.getPanelList(), "listMusic");
        panelSuperiorDerecha.add(outView, "logout");
        panelSuperiorDerecha.add(addMusicView.getPanelAddSong(), "addMusic");
        panelSuperiorDerecha.add(deleteMusicView.getPanelDeleteSong(), "deleteSong");
        panelSuperiorDerecha.add(addPlaylistView.getPanelAddSong(), "addPlaylist");
        panelSuperiorDerecha.add(deletePlaylistView.getPanelAddSong(), "deletePlaylist");
        panelSuperiorDerecha.add(showMusicInfoView.getPanelShowSongInfo(), "showMusicInfo");
        panelSuperiorDerecha.add(musicStatisticsView.getContentPane(), "musicStatistics");
        panelSuperiorDerecha.add(playlistSongsView.getPanelList(), "playlistSongs");
        panelSuperiorDerecha.add(addSongToPlaylistView.getPanelList(), "addSongToPlaylist");
        panelSuperiorDerecha.add(deleteSongFromPlaylistView.getPanel(), "deleteSongFromPlaylist");

        mainPanelCardLayout.show(panelSuperiorDerecha, "listMusic");
        panelSuperiorDerecha.setBackground(UIPalette.COLOR_PRIMARIO.getColor());

        panelSuperiorIzquierda.setPreferredSize(new Dimension(400, 700));
        panelPrincipal.add(panelSuperiorIzquierda,BorderLayout.LINE_START);
        panelSuperiorDerecha.setPreferredSize(new Dimension(900, 700));
        panelPrincipal.add(panelSuperiorDerecha, BorderLayout.CENTER);
        JPanel panelInferior = playMusicView.getPanelReproductor();
        panelInferior.setPreferredSize(new Dimension(1300, 100));
        panelPrincipal.add(panelInferior, BorderLayout.PAGE_END);
    }

    public void setMusicStatisticsView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "musicStatistics");
    }
    public void createViewPrincipal(){
        window.add(this.cardPanelInici);
        window.setSize(500, 700);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        cardLayout.show(cardPanelInici, "welcome");
        window.setVisible(true);
    }
    public void createViewReproductor(){
        window.add(panelPrincipal);
        window.remove(cardPanelInici);
        window.pack();
        window.setSize(1300, 800);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        cardLayout.show(cardPanelInici, "welcome");
        window.setVisible(true);
    }
    public void setSignInView(){
        cardLayout.show(cardPanelInici, "signIn");
    }
    public void setRegisterView(){
        cardLayout.show(cardPanelInici, "signUp");
    }
    public void setWelcomeView(){
        cardLayout.show(cardPanelInici, "welcome");
    }
    public void setAddMusicView(){mainPanelCardLayout.show(panelSuperiorDerecha, "addMusic");}
    public void setDeleteMusicView(){mainPanelCardLayout.show(panelSuperiorDerecha, "deleteSong");}
    public void setPlaylistView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "playlist");
    }
    public void setListMusicView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "listMusic");
    }
    public void setAddPlaylistView() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "addPlaylist");
    }
    public void setDeletePlaylistView() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "deletePlaylist");
    }
    public void setLogOutView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "logout");
    }

    public void showMusicInfo() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "showMusicInfo");
    }

    public void setPlaylistSongsView() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "playlistSongs");
    }

    public void setAddSongToPlaylistView() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "addSongToPlaylist");
    }

    public void setDeleteSongFromPlaylistView() {
        mainPanelCardLayout.show(panelSuperiorDerecha, "deleteSongFromPlaylist");
    }

    public void closeWindow() {
        window.dispose();
    }
}

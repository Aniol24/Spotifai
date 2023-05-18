package presentation.view;

import business.BusinessLogicMusic;
import business.BusinessLogicSong;
import com.sun.tools.javac.Main;
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

    private JPanel panelSuperiorIzquierda;

    public ViewsController(SignInView signInView, SignUpView signUpView, LogOutView logOutView, WelcomeView welcomeView,
                           AddMusicView addMusicView, ListMusicView listMusicView, DeleteMusicView deleteMusicView,
                           MainMenuView mainMenuView, PlayMusicView playMusicView, PlaylistView playlistView, MusicStatisticsView musicStatisticsView) {


        ImageIcon spotifyIcon = new ImageIcon("data/img/spotify.png");
        this.window = new JFrame("Espotifai");
        this.window.setIconImage(spotifyIcon.getImage());

        cardLayout = new CardLayout();
        cardPanelInici = new JPanel(cardLayout);
        cardPanelInici.add(signInView.getPanelSignIn(), "signIn");
        cardPanelInici.add(signUpView.getPanelSignup(), "signUp");
        cardPanelInici.add(logOutView.ventanaEmergenteLogOut(), "logout");
        cardPanelInici.add(welcomeView.getWelcomePanel(), "welcome");
        cardPanelInici.add(addMusicView.getPanel_add_song(), "addSong");
        cardPanelInici.add(listMusicView.getPanel_list(), "listMusic");
        cardPanelInici.add(deleteMusicView.getPanel_delete_song(), "deleteSong");

        crearPanelPrincipal(mainMenuView, playMusicView, playlistView, listMusicView, logOutView, musicStatisticsView);
    }

    private void crearPanelPrincipal(MainMenuView mainMenuView, PlayMusicView playMusicView, PlaylistView playlistView, ListMusicView listMusicView,
                                     LogOutView logOutView, MusicStatisticsView musicStatisticsView) {

        panelPrincipal = new JPanel(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Peso en la dirección X
        gbc.weighty = 1.0; // Peso en la dirección Y

        panelSuperiorIzquierda = mainMenuView.getMenuPanel();
        panelSuperiorIzquierda.setBackground(UIPalette.COLOR_PRIMARIO_CLARO .getColor());

        mainPanelCardLayout = new CardLayout();
        panelSuperiorDerecha = new JPanel(mainPanelCardLayout);
        panelSuperiorDerecha.add(playlistView.getContentPane(), "playlist");
        panelSuperiorDerecha.add(listMusicView.getPanel_list(), "listMusic");
        panelSuperiorDerecha.add(musicStatisticsView.getContentPane(), "musicStatistics");
        panelSuperiorDerecha.add(logOutView.ventanaEmergenteLogOut(), "logout");
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

    public void setSignInView(){
        cardLayout.show(cardPanelInici, "signIn");
    }

    public void setRegisterView(){
        cardLayout.show(cardPanelInici, "signUp");
    }

    public void setWelcomeView(){
        cardLayout.show(cardPanelInici, "welcome");
    }

    public void setAddMusicView(){cardLayout.show(cardPanelInici, "addSong");}
    public void setDeleteMusicView(){cardLayout.show(cardPanelInici, "deleteSong");}

    public void setPlaylistView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "playlist");
    }

    public void setListMusicView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "listMusic");
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

    public void setLogOutView(){
        mainPanelCardLayout.show(panelSuperiorDerecha, "logout");
    }
}

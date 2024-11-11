
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    JPanel logo;
    JLabel lbl_logo;
    JPanel BD; // should be fit to resolution
    JButton Play;
    JPanel PlayBD; // background for the jbutton, ActionListener to launch Game
    JButton Settings;
    JPanel SettingsBD;
    JButton Exit;
    JPanel ExitBD;

    public MainMenu() {
        // Create an ImageIcon
        ImageIcon imagelogo = new ImageIcon("UniSim-Logo.png"); // UniSim logo should be stored within same directory
        Image scaledImage = imagelogo.getImage().getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        lbl_logo = new JLabel(scaledIcon);
        lbl_logo.setBounds(100, 100, scaledIcon.getIconWidth(), scaledIcon.getIconHeight());
        // Add the JLabel to the panel

        // logo - JPanel
        logo = new JPanel();
        logo.setBackground(Color.white);
        logo.setBounds(400, 0, 300, 300);
        logo.setVisible(true);
        logo.setLayout(null);
        logo.add(lbl_logo);

        Play = new JButton("Play");
        Play.setBounds(250, 100, 100, 30);
        Play.addActionListener(this); // add ActionListener that goes into the MainGame

        // JPanel

        Settings = new JButton("Settings");
        Settings.setBounds(250, 200, 100, 30);
        // Settings.addActionListener(this); for Settings Menu

        Exit = new JButton("Exit");
        Exit.setBounds(250, 300, 100, 30);
        // Exit.addActionListener(this); for Exit

        // for Menu
        this.setLayout(null);
        this.setVisible(true); // Makes sure you can see frame
        this.setSize(1000, 1000); // Sets X, Y dimensions
        this.setTitle("Menu"); // Sets Title for frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.add for Buttons
        this.add(Play);
        this.add(Settings);
        this.add(Exit);
        this.add(logo);
        this.add(lbl_logo);
    }

    public static void main(String[] args) {
        new MainMenu();
    }

    /**
     * @Override
     *           public void actionPerformed(ActionEvent e) {
     *           if (e.getSource() == Play) {
     *           dispose();
     * 
     *           //this is to open the LibGDX game,
     *           new Thread(() -> {
     *           EnterGameClassHere.launchGame(); //Launch the LibGDX game
     *           }).start();
     *           }
     *           else if (e.getSource() == Settings) {
     *           dispose();
     * 
     *           //to open Settings, could do it the JFrame way of revalidating,
     *           repaint and then go to the Settings or set it up in LibGDX
     *           }
     *           else if (e.getSource() == Exit){
     *           dispose();
     *           System.exit(0); //this is actually just a guess honestly
     *           }
     *           /
     **/

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
// @Override with ActionListener, clicking on play should take you to class
// Game, Exit should quit the game, Settings to Settings
// Class Settings should be in LibGDX
// }

// to add Background, import Background.png, size it to the dimensions of the
// JFrame, simple stuff

// to add JButton backgrounds it should simply be the JPanel imported the same
// way as JButton, dimensions adjusted to be the border
// then add it in. Incredibly simple stuff.
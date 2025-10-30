package VueControleur;

import Modele.Grid;
import Modele.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;

public class View extends JPanel implements Observer {
    private final ViewGridGame viewGridGame;
    private final ViewGridNextPiece viewGridNextPiece;
    private final JTextField score;
    private final JTextField credit;
    private final JButton newGameSolo;
    private final JButton newGameDuo;

    private final JPanel scoreBackground;
    private final JPanel creditBackground;

    public static String SCORE_TEXT = "   Score: ";
    private final VC vc;

    private void initButton(Executor ex){
        newGameSolo.addActionListener(new ActionListener() {
            // Evénement bouton : Object contrôleur qui réceptionne
            @Override
            public void actionPerformed(ActionEvent e) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        vc.gameReset(Model.GameType.TETRISSOLO);
                    }
                });
            }
        });

        newGameDuo.addActionListener(new ActionListener() {
            // Evénement bouton : Object contrôleur qui réceptionne
            @Override
            public void actionPerformed(ActionEvent e) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        vc.gameReset(Model.GameType.TETRISDUO);
                    }
                });
            }
        });
    }

    public View(VC _vc) {
        vc = _vc;
        viewGridGame = new ViewGridGame(vc, Grid.WIDTH,Grid.HEIGHT);
        viewGridGame.setPreferredSize(new Dimension(5,1));

        viewGridNextPiece = new ViewGridNextPiece(vc);
        viewGridNextPiece.setPreferredSize(new Dimension(1,1));

        score = new JTextField(SCORE_TEXT+String.valueOf(0));
        score.setPreferredSize(new Dimension(5,1));
        score.setOpaque(false);
        score.setBorder(BorderFactory.createEmptyBorder());
        score.setForeground(Color.WHITE);
        score.setFont(new Font("Comic Sans MS",Font.BOLD,20));

        credit = new JTextField();
        credit.setPreferredSize(new Dimension(5,1));
        credit.setOpaque(false);
        credit.setEditable(false);
        credit.setBorder(BorderFactory.createEmptyBorder());
        credit.setForeground(Color.WHITE);

        Image im = ImagesGetter.getImageBackGround(4);
        if(im==null){
            System.exit(-1);
        }
        im = im.getScaledInstance(
                10*VC.COEF+5, 2*VC.COEF+5, java.awt.Image.SCALE_SMOOTH ) ;

        newGameSolo = new Button("New Game Solo",im);//New Game Solo
        newGameSolo.setPreferredSize(new Dimension(5,1));

        newGameDuo = new Button("New Game Duo",im);//New Game Duo
        newGameDuo.setPreferredSize(new Dimension(5,1));


        scoreBackground = new JPanel(){
            public void paintComponent(Graphics g) {
                g.drawImage(ImagesGetter.getImageBackGround(4),
                        0,0,10*VC.COEF+5, 2*VC.COEF+5,null);
            }
        };
        scoreBackground.setPreferredSize(new Dimension(5,1));
        scoreBackground.setOpaque(false);
        scoreBackground.setBackground(new Color(255, 255, 255, 255));

        creditBackground = new JPanel(){
            public void paintComponent(Graphics g) {
                g.drawImage(ImagesGetter.getImageBackGround(7),
                        0,0,10*VC.COEF+5, 20*VC.COEF+5,null);
            }
        };
        creditBackground.setPreferredSize(new Dimension(5,10));
        creditBackground.setOpaque(false);
        creditBackground.setBackground(new Color(255, 255, 255, 255));



        initButton(vc.getEx());
    }

    public JPanel createPanel(int coef ){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0,0,0,0);

        c.gridx = 0;
        c.gridy = 0;

        c.ipadx = 20*coef+5;
        c.ipady = 40*coef+5;

        c.gridwidth = 20;
        c.gridheight = 40;

        p.add(viewGridGame, c);



        c.gridx = 20;
        c.gridy = 0;

        c.ipadx = coef;
        c.ipady = 40*coef;

        c.gridwidth = 1;
        c.gridheight = 40;

        p.add(Box.createHorizontalGlue(), c);

        c.gridx = 21;
        c.gridy = 0;

        c.ipadx = 10*coef;
        c.ipady = 2*coef;

        c.gridwidth = 10;
        c.gridheight = 2;

        p.add(score, c);
        p.add(scoreBackground, c);



        c.gridx = 21;
        c.gridy = 2;

        c.ipadx = 10*coef;
        c.ipady = coef;

        c.gridwidth = 5;
        c.gridheight = 1;

        p.add(Box.createVerticalGlue(), c);

        c.gridx = 21;
        c.gridy = 3;

        c.ipadx = 10*coef+5;
        c.ipady = 10*coef+5;

        c.gridwidth = 10;
        c.gridheight = 10;


        p.add(viewGridNextPiece, c);

        c.gridx = 21;
        c.gridy = 13;

        c.ipadx = 10*coef;
        c.ipady = coef;

        c.gridwidth = 10;
        c.gridheight = 1;

        p.add(Box.createVerticalGlue(), c);

        c.gridx = 21;
        c.gridy = 14;

        c.ipadx = 10*coef;
        c.ipady = 2*coef;

        c.gridwidth = 10;
        c.gridheight = 2;

        p.add(newGameSolo, c);

        c.gridx = 21;
        c.gridy = 16;

        c.ipadx = 10*coef;
        c.ipady = coef;

        c.gridwidth = 10;
        c.gridheight = 1;

        p.add(Box.createVerticalGlue(), c);

        c.gridx = 21;
        c.gridy = 17;

        c.ipadx = 10*coef;
        c.ipady = 2*coef;

        c.gridwidth = 10;
        c.gridheight = 2;

        p.add(newGameDuo, c);


        c.gridx = 21;
        c.gridy = 19;

        c.ipadx = 10*coef;
        c.ipady = coef;

        c.gridwidth = 10;
        c.gridheight = 1;

        p.add(Box.createVerticalGlue(), c);

        c.gridx = 21;
        c.gridy = 20;

        c.ipadx = 10*coef;
        c.ipady = 20*coef-3;

        c.gridwidth = 10;
        c.gridheight = 20;

        p.add(credit, c);
        p.add(creditBackground, c);

        p.setOpaque(false);
        return p;
    }

    @Override
    public void update(Observable o, Object arg) {
        viewGridGame.update(o, arg);
        viewGridNextPiece.update(o, arg);
        score.setText(String.valueOf(SCORE_TEXT+vc.getScore()));
    }
}

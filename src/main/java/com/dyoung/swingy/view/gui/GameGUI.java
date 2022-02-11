package com.dyoung.swingy.view.gui;

import com.dyoung.swingy.controller.GameController;
import com.dyoung.swingy.controller.MenuController;
import com.dyoung.swingy.model.Game;
import com.dyoung.swingy.util.DataBase;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JPanel {
    private final JButton northButton = new JButton("North");
    private final JButton westButton = new JButton("West");
    private final JButton eastButton = new JButton("East");
    private final JButton southButton = new JButton("South");
    private final JButton consoleButton = new JButton("Console");

    private final JEditorPane infoPane = new JEditorPane();
    private final JTextPane tPane = new JTextPane();

    public static final String VIEW = "gui";
    private final GameController controller;

    public GameGUI() {
        controller = GameController.getInstance();
    }

    public void start() {
        controller.update(VIEW);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        tPane.setFont(new Font("monospaced", Font.PLAIN, 15));
        JScrollPane mapScroll = new JScrollPane(tPane);
        int len = controller.getGame().getMap().length;
        ;
        if (len < 15) {
            len = 300;
        } else
            len *= 20;
        mapScroll.setPreferredSize(new Dimension(len, len));
        mapScroll.setMinimumSize(new Dimension(150, 150));
        this.add(mapScroll, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        infoPane.setEditable(false);
        infoPane.setFont(new Font("monospaced", Font.PLAIN, 15));
        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setPreferredSize(new Dimension(300, 300));
        infoScroll.setMinimumSize(new Dimension(150, 150));
        this.add(infoScroll, gbc);

        this.add(northButton, gbc);
        this.add(westButton, gbc);
        this.add(eastButton, gbc);
        this.add(southButton, gbc);
        this.add(consoleButton, gbc);

        this.setVisible(true);

        MenuController.getFrame().setContentPane(this);
        MenuController.getFrame().revalidate();
        MenuController.showFrame();

        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.move("w", VIEW);
            }
        });

        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.move("a", VIEW);
            }
        });

        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.move("d", VIEW);
            }
        });

        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.move("s", VIEW);
            }
        });

        consoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.chooseSwitch(VIEW);
            }
        });
    }

    public void printMap(int[][] map, int x, int y) {
        tPane.setEditable(true);
        tPane.setText("");
        appendToPane(tPane, String.format("%dX%d\n", map.length, map.length), Color.BLACK);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (x == j && y == i) {//a
                    appendToPane(tPane, "H ", Color.MAGENTA);
                } else {
                    switch (map[i][j]) {
                        case 0:
                            appendToPane(tPane, "T ", Color.ORANGE);
                            break;
                        case 1:
                            appendToPane(tPane, "S ", Color.GREEN);
                            break;
                        case 2:
                            appendToPane(tPane, "M ", Color.BLUE);
                            break;
                        case 3:
                            appendToPane(tPane, "Y ", Color.RED);
                            break;
                        default:
                            appendToPane(tPane, ". ", Color.BLACK);
                    }
                }
            }
            appendToPane(tPane, "\n", Color.BLACK);
        }
        tPane.setEditable(false);
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void update(Game game) {
        infoPane.setText(game.getCandidate().toString());
        controller.printMap(VIEW);
    }

    public void overGame() {
        controller.overGame();
    }

    public void finish() {
        MenuController.hideFrame();
        MenuController.getFrame().dispose();
        DataBase.close();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(MenuController.getFrame(), message);
    }

    public void invitedInterview(int hr) {
        Object[] options = {"Go", "Refuse"};

        int result = JOptionPane.showOptionDialog(MenuController.getFrame(),
                "You were invited for an interview",
                "Choose", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.YES_OPTION)
            controller.chooseGo(hr, VIEW);
        else
            controller.chooseRefuse(hr, VIEW);
    }

    public boolean keepArtifact(String replaceMessage) {
        Object[] options = {"Keep", "Leave"};

        int result = JOptionPane.showOptionDialog(MenuController.getFrame(),
                replaceMessage,
                "Choose", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return result == JOptionPane.YES_OPTION;
    }

    public void winGame() {
        Object[] options = {"Continue", "Exit"};

        int result = JOptionPane.showOptionDialog(MenuController.getFrame(),
                "YOU WON",
                "Choose", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.YES_OPTION)
            controller.chooseContinue(VIEW);
        else
            controller.chooseExit(VIEW);
    }

    public void switchView() {
        MenuController.hideFrame();
        controller.update("console");
    }
}

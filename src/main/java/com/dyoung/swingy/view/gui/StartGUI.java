package com.dyoung.swingy.view.gui;

import com.dyoung.swingy.controller.MenuController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartGUI extends JPanel {
    public static final String VIEW = "gui";
    private final MenuController controller;

    private final JButton createButton = new JButton("Create");
    private final JButton selectButton = new JButton("Select");

    public StartGUI() {
        controller = MenuController.getInstance();
    }

    public void start() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./src/main/resources/logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert myPicture != null;
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));

        this.add(picLabel, gbc);
        this.add(createButton, gbc);
        this.add(selectButton, gbc);

        this.setVisible(true);
        MenuController.getFrame().setContentPane(this);
        MenuController.getFrame().revalidate();
        MenuController.showFrame();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.create(VIEW);
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.select(VIEW);
            }
        });
    }
}

package com.dyoung.swingy.view.gui;

import com.dyoung.swingy.controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class CreateGUI extends JPanel {
    private final JLabel heroName = new JLabel("Name:");
    private final JTextField heroNameField = new JTextField(37);

    private final JButton createButton = new JButton("Create");
    private final JButton selectButton = new JButton("Select");

    private final JRadioButton doRadioButton = new JRadioButton("DevOps");
    private final JRadioButton beRadioButton = new JRadioButton("BackEnd");
    private final JRadioButton feRadioButton = new JRadioButton("FrontEnd");
    private final JRadioButton mdRadioButton = new JRadioButton("MobileDev");
    private final JRadioButton dsRadioButton = new JRadioButton("DataScience");

    private final JLabel heroClass = new JLabel("Class:");
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JEditorPane infoPane = new JEditorPane();

    public static final String VIEW = "gui";
    private final MenuController controller;

    public CreateGUI() {
        controller = MenuController.getInstance();
    }

    public void start() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel namePanel = new JPanel();
        namePanel.add(heroName);
        namePanel.add(heroNameField);
        namePanel.setVisible(true);
        heroNameField.setFont(new Font("monospaced", Font.PLAIN, 15));
        this.add(namePanel, gbc);

        JPanel classPanel = new JPanel();
        classPanel.add(heroClass);
        doRadioButton.setSelected(true);
        buttonGroup.add(doRadioButton);
        buttonGroup.add(beRadioButton);
        buttonGroup.add(feRadioButton);
        buttonGroup.add(mdRadioButton);
        buttonGroup.add(dsRadioButton);
        classPanel.add(doRadioButton);
        classPanel.add(beRadioButton);
        classPanel.add(feRadioButton);
        classPanel.add(mdRadioButton);
        classPanel.add(dsRadioButton);
        classPanel.setVisible(true);
        this.add(classPanel, gbc);

        infoPane.setEditable(false);
        infoPane.setFont(new Font("monospaced", Font.PLAIN, 15));
        infoPane.setText(" Class           Attack     Defense     Hit Points\n" +
                "                                      \n" +
                " DevOps            35         30           85\n" +
                " BackEnd           50         25           70\n" +
                " FrontEnd          45         15           85\n" +
                " MobileDev         55         20           75\n" +
                " DataScience       40         30           90\n");
        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setPreferredSize(new Dimension(600, 300));
        infoScroll.setMinimumSize(new Dimension(600, 300));
        this.add(infoScroll, gbc);

        this.add(createButton, gbc);
        this.add(selectButton, gbc);
        this.setVisible(true);

        MenuController.getFrame().setContentPane(this);
        MenuController.getFrame().revalidate();
        MenuController.showFrame();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createCandidate(heroNameField.getText(), getSelectedButtonText(buttonGroup), VIEW);
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.select(VIEW);
            }
        });
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}

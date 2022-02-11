package com.dyoung.swingy.view.gui;

import com.dyoung.swingy.controller.MenuController;
import com.dyoung.swingy.util.DataBase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectGUI extends JPanel {
    private final JEditorPane infoPane = new JEditorPane();
    private final JButton selectButton = new JButton("Select");
    private final JButton createButton = new JButton("Create");

    private String lastSelectedIdx;
    public static final String VIEW = "gui";
    private final MenuController controller;

    public SelectGUI() {
        controller = MenuController.getInstance();
    }

    public void start() {
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] data = controller.getListData();

        final JList<String> list = new JList<>(data);
        list.setFont(new Font("monospaced", Font.PLAIN, 15));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(300, 300));
        listScroll.setMinimumSize(new Dimension(150, 150));
        this.add(listScroll, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        infoPane.setEditable(false);
        infoPane.setFont(new Font("monospaced", Font.PLAIN, 15));
        infoPane.setText("Select candidate");
        if (data.length == 0)
            infoPane.setText("The list is empty");
        JScrollPane infoScroll = new JScrollPane(infoPane);
        infoScroll.setPreferredSize(new Dimension(300, 300));
        infoScroll.setMinimumSize(new Dimension(150, 150));
        this.add(infoScroll, gbc);

        this.add(selectButton, gbc);
        this.add(createButton, gbc);
        selectButton.setEnabled(false);

        this.setVisible(true);

        MenuController.getFrame().setContentPane(this);
        MenuController.getFrame().revalidate();
        MenuController.showFrame();

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (list.getSelectedIndex() != -1) {
                        infoPane.setText(DataBase.selectCandidateByName(list.getSelectedValue()).toString());
                        selectButton.setEnabled(true);
                        lastSelectedIdx = list.getSelectedValue();
                    } else
                        selectButton.setEnabled(false);
                }
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.selectCandidate(lastSelectedIdx, VIEW);
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.create(VIEW);
            }
        });
    }
}

package sk.fiit.jim.gui;

import sk.fiit.jim.init.SkillsFromXmlLoader;
import sk.fiit.jim.log.Log;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import static sk.fiit.jim.log.LogType.GUI;

public class ReplanWindow extends JFrame {

    private JLabel  actualPositionTitle;
    private JLabel  actualPositionValue;
    private JLabel  actualStrategyTitle;
    private JLabel  actualStrategyValue;
    private JLabel  actualTacticTitle;
    private JLabel  actualTacticValue;
    private JButton cancelBtn;
    private JLabel  goingToPositionTitle;
    private JLabel  goingToPositionValue;
    private JPanel  jPanel1;
    private JPanel  jPanel2;
    private JButton reloadAllBtn;
    private JButton reloadXmlBtn;
    private JButton replanBtn;

    private static ReplanWindow self = null;

    public static final int VALUE_STRATEGY         = 0;
    public static final int VALUE_TACTICS          = 1;
    public static final int VALUE_CURRENT_POSITION = 2;
    public static final int VALUE_GOING_POSITION   = 3;

    public static ReplanWindow getInstance() {
        if (ReplanWindow.self == null) {
            ReplanWindow.self = ReplanWindow.initInstance();
        }
        return ReplanWindow.self;
    }

    public ReplanWindow() {
        super("JIM control center");
        initComponents();
        registerListeners();

        // call onCancel() when cross is clicked
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }


    private void onCancel() {
        System.exit(0);
    }

    private void registerListeners() {
        this.replanBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                reloadScripts();
            }
        });

        this.reloadXmlBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                reloadXml();
            }
        });
        this.reloadAllBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                reloadXml();
                reloadScripts();
            }
        });

        this.cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void reloadScripts() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Log.log(GUI, "Script reload request from GUI");
            }
        });
    }

    private void reloadXml() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Log.log(GUI, "XML reload request from GUI");
                    new SkillsFromXmlLoader(new File("./moves")).load();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(ReplanWindow.this, "Unable to reload XML, consult logs for details");
                }
            }
        });
    }

    private void initComponents() {

        jPanel1 = new JPanel();
        cancelBtn = new JButton();
        replanBtn = new JButton();
        reloadAllBtn = new JButton();
        reloadXmlBtn = new JButton();
        jPanel2 = new JPanel();
        actualStrategyTitle = new JLabel();
        actualTacticTitle = new JLabel();
        goingToPositionTitle = new JLabel();
        actualPositionTitle = new JLabel();
        actualTacticValue = new JLabel();
        actualStrategyValue = new JLabel();
        actualPositionValue = new JLabel();
        goingToPositionValue = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cancelBtn.setText("Cancel");

        replanBtn.setText("Replan");
        replanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //replanBtnActionPerformed(evt);
            }
        });

        reloadAllBtn.setText("XML + Script reload");
        reloadAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //reloadAllBtnActionPerformed(evt);
            }
        });

        reloadXmlBtn.setText("XML reload");
        reloadXmlBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //reloadXmlBtnActionPerformed(evt);
            }
        });

        actualStrategyTitle.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        actualStrategyTitle.setText("Actual strategy");

        actualTacticTitle.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        actualTacticTitle.setText("Actual tactic");

        goingToPositionTitle.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        goingToPositionTitle.setText("Going to position");

        actualPositionTitle.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        actualPositionTitle.setText("Actual position");

        actualTacticValue.setText("None");

        actualStrategyValue.setText("None");

        actualPositionValue.setText("None");

        goingToPositionValue.setText("None");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGroup(jPanel2Layout.createParallelGroup(
                                                            GroupLayout.Alignment.LEADING)
                                                                           .addGroup(
                                                                                   jPanel2Layout.createSequentialGroup()
                                                                                                .addGap(16, 16, 16)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                  .createParallelGroup(
                                                                                                                          GroupLayout.Alignment.LEADING,
                                                                                                                          false)
                                                                                                                  .addComponent(
                                                                                                                          actualStrategyTitle,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          Short.MAX_VALUE)
                                                                                                                  .addComponent(
                                                                                                                          actualPositionTitle,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          Short.MAX_VALUE)
                                                                                                                  .addComponent(
                                                                                                                          goingToPositionTitle,
                                                                                                                          GroupLayout.DEFAULT_SIZE,
                                                                                                                          150,
                                                                                                                          Short.MAX_VALUE))
                                                                                                .addPreferredGap(
                                                                                                        javax.swing
                                                                                                                .LayoutStyle.ComponentPlacement.RELATED,
                                                                                                        javax.swing
                                                                                                                .GroupLayout.DEFAULT_SIZE,
                                                                                                        Short.MAX_VALUE
                                                                                                )
                                                                                                .addGroup(jPanel2Layout
                                                                                                                  .createParallelGroup(
                                                                                                                          GroupLayout.Alignment.LEADING)
                                                                                                                  .addComponent(
                                                                                                                          actualStrategyValue,
                                                                                                                          GroupLayout.Alignment.TRAILING,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          227,
                                                                                                                          GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addComponent(
                                                                                                                          actualPositionValue,
                                                                                                                          GroupLayout.Alignment.TRAILING,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          227,
                                                                                                                          GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addComponent(
                                                                                                                          goingToPositionValue,
                                                                                                                          GroupLayout.Alignment.TRAILING,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          227,
                                                                                                                          GroupLayout.PREFERRED_SIZE))
                                                                           )
                                                                           .addGroup(
                                                                                   GroupLayout.Alignment
                                                                                           .TRAILING,
                                                                                   jPanel2Layout.createSequentialGroup()
                                                                                                .addContainerGap(
                                                                                                        javax.swing
                                                                                                                .GroupLayout.DEFAULT_SIZE,
                                                                                                        Short.MAX_VALUE
                                                                                                )
                                                                                                .addComponent(
                                                                                                        actualTacticValue,
                                                                                                        javax.swing
                                                                                                                .GroupLayout.PREFERRED_SIZE,
                                                                                                        227,
                                                                                                        javax.swing
                                                                                                                .GroupLayout.PREFERRED_SIZE
                                                                                                )
                                                                           ))
                                                    .addContainerGap())
                             .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                                           .addGap(16, 16, 16)
                                                                           .addComponent(actualTacticTitle,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         150,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           )
                                                                           .addContainerGap(229, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addGap(17, 17, 17)
                                                    .addComponent(actualTacticValue,
                                                                  GroupLayout.PREFERRED_SIZE, 31,
                                                                  GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(
                                                            LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel2Layout.createParallelGroup(
                                                            GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(actualStrategyTitle,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           )
                                                                           .addComponent(actualStrategyValue,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           ))
                                                    .addPreferredGap(
                                                            LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel2Layout.createParallelGroup(
                                                            GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(actualPositionTitle,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           )
                                                                           .addComponent(actualPositionValue,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           ))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel2Layout.createParallelGroup(
                                                            GroupLayout.Alignment.BASELINE)
                                                                           .addComponent(goingToPositionTitle,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           )
                                                                           .addComponent(goingToPositionValue,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE
                                                                           ))
                                                    .addContainerGap(70, Short.MAX_VALUE))
                             .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                                           .addGap(16, 16, 16)
                                                                           .addComponent(actualTacticTitle,
                                                                                         GroupLayout
                                                                                                 .PREFERRED_SIZE,
                                                                                         31,
                                                                                         GroupLayout.PREFERRED_SIZE
                                                                           )
                                                                           .addContainerGap(206, Short.MAX_VALUE)))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel1Layout.createParallelGroup(
                                                            GroupLayout.Alignment.LEADING)
                                                                           .addGroup(
                                                                                   GroupLayout.Alignment.TRAILING,
                                                                                   jPanel1Layout.createSequentialGroup()
                                                                                                .addGap(0, 0,
                                                                                                        Short.MAX_VALUE)
                                                                                                .addComponent(
                                                                                                        cancelBtn)
                                                                           )
                                                                           .addGroup(
                                                                                   jPanel1Layout.createSequentialGroup()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                  .createParallelGroup(
                                                                                                                          GroupLayout.Alignment.LEADING)
                                                                                                                  .addComponent(
                                                                                                                          reloadAllBtn,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          158,
                                                                                                                          GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addComponent(
                                                                                                                          replanBtn,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          158,
                                                                                                                          GroupLayout.PREFERRED_SIZE)
                                                                                                                  .addComponent(
                                                                                                                          reloadXmlBtn,
                                                                                                                          GroupLayout.PREFERRED_SIZE,
                                                                                                                          158,
                                                                                                                          GroupLayout.PREFERRED_SIZE))
                                                                                                .addGap(35, 35, 35)
                                                                                                .addComponent(jPanel2,
                                                                                                              GroupLayout.DEFAULT_SIZE,
                                                                                                              GroupLayout.DEFAULT_SIZE,
                                                                                                              Short.MAX_VALUE)
                                                                           ))
                                                    .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                    .addContainerGap()
                                                                                    .addGroup(jPanel1Layout
                                                                                                      .createParallelGroup(
                                                                                                              GroupLayout.Alignment.LEADING)
                                                                                                      .addGroup(
                                                                                                              jPanel1Layout
                                                                                                                      .createSequentialGroup()
                                                                                                                      .addComponent(
                                                                                                                              replanBtn,
                                                                                                                              GroupLayout.PREFERRED_SIZE,
                                                                                                                              47,
                                                                                                                              GroupLayout.PREFERRED_SIZE)
                                                                                                                      .addGap(18,
                                                                                                                              18,
                                                                                                                              18)
                                                                                                                      .addComponent(
                                                                                                                              reloadXmlBtn,
                                                                                                                              GroupLayout.PREFERRED_SIZE,
                                                                                                                              47,
                                                                                                                              GroupLayout.PREFERRED_SIZE)
                                                                                                                      .addGap(18,
                                                                                                                              18,
                                                                                                                              18)
                                                                                                                      .addComponent(
                                                                                                                              reloadAllBtn,
                                                                                                                              GroupLayout.PREFERRED_SIZE,
                                                                                                                              47,
                                                                                                                              GroupLayout.PREFERRED_SIZE)
                                                                                                                      .addGap(0,
                                                                                                                              0,
                                                                                                                              Short.MAX_VALUE)
                                                                                                      )
                                                                                                      .addComponent(
                                                                                                              jPanel2,
                                                                                                              GroupLayout.DEFAULT_SIZE,
                                                                                                              GroupLayout.DEFAULT_SIZE,
                                                                                                              Short.MAX_VALUE))
                                                                                    .addPreferredGap(
                                                                                            LayoutStyle
                                                                                                    .ComponentPlacement.RELATED)
                                                                                    .addComponent(cancelBtn)
                                                                                    .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                      .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                    Short.MAX_VALUE)
        );

        pack();
    }

    public void makeVisible() {
        ReplanWindow.self.setVisible(true);
    }

    private static ReplanWindow initInstance() {
        ReplanWindow window = new ReplanWindow();
        return window;
    }

    public static void main(String[] args) {
        ReplanWindow window = ReplanWindow.getInstance();
        window.makeVisible();
    }

    public JLabel getGoingToPositioValue() {
        return this.goingToPositionValue;
    }

    public JLabel getActualPositionValue() {
        return actualPositionValue;
    }

    public JLabel getActualStrategyValue() {
        return actualStrategyValue;
    }

    public JLabel getActualTacticsValue() {
        return this.actualTacticValue;
    }

    public void updateText(int value, String s) {
        switch (value) {
            case ReplanWindow.VALUE_TACTICS:
                ReplanWindow.self.getActualTacticsValue().setText(s);
                break;
            case ReplanWindow.VALUE_STRATEGY:
                ReplanWindow.self.getActualStrategyValue().setText(s);
                break;
            case ReplanWindow.VALUE_CURRENT_POSITION:
                ReplanWindow.self.getActualPositionValue().setText(s);
                break;
            case ReplanWindow.VALUE_GOING_POSITION:
                ReplanWindow.self.getGoingToPositioValue().setText(s);
                break;
            default:
                break;
        }
    }
}

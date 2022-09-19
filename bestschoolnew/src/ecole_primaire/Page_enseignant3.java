package ecole_primaire;

import com.pack.impression.imprimernotes;
import enseignant.*;
import etablissement.Curseur;
import etablissement.PasswordAuthentication;

import etablissement.connexion;
import etablissement.impressionmodel_11;
import etablissement.liste_classe;
import etablissement.memoire;
import etablissement.model_eleves;
import intendancee.autres;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Michelle Wafo
 */
public class Page_enseignant3 extends javax.swing.JFrame {

    private final String USER_AGENT = "Mozilla/5.0";
    List<model_eleves> liste_eleve = new ArrayList<>();
    JComboBox sequences = new JComboBox();
    String inline;
    private javax.swing.JButton deconnexion;

    public static String[] classes;

    private String lnaissance, date,
            anglais, langue, litterature, redaction, hist_geo, ecm, svt, chimie, physiques, mathematiques, eps, tm, pct, informatique, esf,
            allemand, espagnole,
            metier_formation, qhse, entrep, techno, circuit_elect, machine_elect, techno_instaleclec, schema_elec, transp_dist, dessin_tech, TST,
            cir_num, cir_anal, tp_elect, install_elect, intall_elect_ind, mesure_essai_elect, TSP;

    JMenuItem cc1, cc2, cc3, cc4, cc5, cc6, cc7, cc8, cc9, ds1, ds2, ds3, ds4, ds5, ds6, ds7, ds8, ds9, sequence1, sequence2, sequence3, sequence4, sequence5, sequence6;
    private String[] tclasse;

    public Page_enseignant3() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        classe = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableau_eleves = new javax.swing.JTable();
        save = new javax.swing.JButton();
        annuler = new javax.swing.JButton();
        trait = new javax.swing.JLabel();
        disciplines = new javax.swing.JComboBox<>();
        effectif = new javax.swing.JLabel();
        impression = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(memoire.setup).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        classe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Classes diponibles" }));
        classe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                classeItemStateChanged(evt);
            }
        });
        classe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classeActionPerformed(evt);
            }
        });

        jLabel1.setText("Choisir la classe");

        jInternalFrame1.setVisible(true);

        tableau_eleves.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Noms et prenoms", "note1", "note2", "note3"
            }
        ));
        tableau_eleves.setRowHeight(30);
        jScrollPane1.setViewportView(tableau_eleves);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        save.setText("Enregistrer");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        annuler.setText("Deconnexion");
        annuler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annulerActionPerformed(evt);
            }
        });

        trait.setFont(new java.awt.Font("Roboto Bk", 2, 14)); // NOI18N

        disciplines.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "  " }));
        disciplines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disciplinesActionPerformed(evt);
            }
        });

        effectif.setFont(new java.awt.Font("Segoe Script", 0, 14)); // NOI18N

        impression.setText("Imprimer etat");
        impression.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                impressionActionPerformed(evt);
            }
        });

        jMenu1.setText("Gestion des notes");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("                  Modifier mot de passe");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("                         Requetes");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(effectif, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(impression)
                .addGap(130, 130, 130)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(classe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disciplines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trait, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(annuler)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(save)
                .addGap(6, 6, 6))
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(save, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(annuler, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(trait, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(disciplines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(classe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(effectif, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(impression, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jInternalFrame1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed


    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked

//        
//        JInternalFrameTest jn = new JInternalFrameTest();
//        jn.show();
    }/*
liste_eleves();      }//GEN-LAST:event_jMenu1MouseClicked
*/
    private void classeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_classeActionPerformed

    private void classeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_classeItemStateChanged

        String value = classe.getSelectedItem().toString();
        if (!value.equals("Classes diponibles")) {

            try {
                liste_matiere();

            } catch (IOException ex) {
                Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            disciplines.setEnabled(false);
        }

    }//GEN-LAST:event_classeItemStateChanged

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Curseur.startWaitCursor(this);
        this.setExtendedState(Page_enseignant3.MAXIMIZED_BOTH);
        disciplines.setEnabled(false);
        try {
            liste_lasse_en_fonction_Ensiegnant(memoire.nom_user);
        } catch (IOException ex) {
            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
        }

        save.setBackground(Color.blue);
        save.setForeground(Color.white);
        annuler.setBackground(Color.red);
        annuler.setForeground(Color.white);
        String[] liste_sequences = {"Séquence 1", "Séquence 2", "Séquence 3", "Séquence 4", "Séquence 5", "Séquence 6"};
        JComboBox sequences = new JComboBox(liste_sequences);
        //jMenu1

        sequence1 = new JMenu("TRIMESTRE 1");

        sequence2 = new JMenu("TRIMESTRE 2");
        sequence3 = new JMenu("TRIMESTRE 3");
        cc1 = new JMenuItem("septembre");
        cc2 = new JMenuItem("octobre");
        cc3 = new JMenuItem("novembre");
        cc4 = new JMenuItem("decembre");
        cc5 = new JMenuItem("janvier");
        cc6 = new JMenuItem("fevrier");
        cc7 = new JMenuItem("mars");
        cc8 = new JMenuItem("avril");
        cc9 = new JMenuItem("mai");

        ds1 = new JMenuItem("DS 1");
        ds2 = new JMenuItem("DS 2");
        ds3 = new JMenuItem("DS 3");
        ds4 = new JMenuItem("DS 4");
        ds5 = new JMenuItem("DS 5");
        ds6 = new JMenuItem("DS 6");
        ds7 = new JMenuItem("DS 7");
        ds8 = new JMenuItem("DS 8");
        ds9 = new JMenuItem("DS 9");

        sequence1.add(cc1);
        sequence1.add(cc2);
        sequence1.add(cc3);

        //sequence2.add(cc4);
        sequence2.add(cc5);
        sequence2.add(cc6);
        sequence2.add(cc7);

        sequence3.add(cc8);
        sequence3.add(cc9);

        jMenu1.add(sequence1);
        jMenu1.add(sequence2);
        jMenu1.add(sequence3);

        cc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m1";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de  SEPTEMBRE" + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m1";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m2";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de OCTOBRE  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "cc2";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m3";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de NOVEMBRE  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m3";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m4";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m4";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m4";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de JANVIER  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m4";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m5";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de FEVRIER  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m5";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npour pouvoir continuer");

                    }
                }
            }
        });
        cc7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m6";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de MARS " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m6";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npouvoir continuer");

                    }
                }
            }
        });
        cc8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m7";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de AVRIL  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m7";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npouvoir continuer");

                    }
                }
            }
        });
        cc9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "m8";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    if (!disciplines.getSelectedItem().toString().trim().equals("")) {

                        final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                        try {
                            memoire.classe = list_des_elevs_par_classe;
                            trait.setText("Notes de MAI  " + list_des_elevs_par_classe + " : " + disciplines.getSelectedItem().toString());
                            memoire.numero_sequence = "m8";
                            liste_eleve_par_classe1(list_des_elevs_par_classe);
                        } catch (IOException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (JSONException ex) {
                            Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Veuillez selectionner votre matiere\npouvoir continuer");

                    }
                }
            }
        });
        ds1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds1";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 1 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds1";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds2";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 2 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds2";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        ds3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds3";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 3 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds3";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds4";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 4 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds4";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds5";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 5 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds5";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds6";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS6 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds6";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds7";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS7 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds7";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds8";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS 8 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds8";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        ds9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {

                String value = classe.getSelectedItem().toString();
                memoire.choix_sequence = "ds9";
                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();
                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de DS9 : " + disciplines.getSelectedItem().toString());
                        memoire.numero_sequence = "ds9";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (JSONException ex) {
                        Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        sequence2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                memoire.choix_sequence = "sq2";

                String value = classe.getSelectedItem().toString();

                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n pour continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();

                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de : " + disciplines.getSelectedItem().toString() + " pour la sequence 2");

                        memoire.numero_sequence = "s2";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {

                    } catch (JSONException ex) {

                    }
                }
            }
        });

        sequence3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                memoire.choix_sequence = "sq3";

                String value = classe.getSelectedItem().toString();

                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n à saisir les pour pouvoir continuer");
                } else {

                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();

                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de : " + disciplines.getSelectedItem().toString() + " pour la sequence 3");

                        memoire.numero_sequence = "s3";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {

                    } catch (JSONException ex) {

                    }
                }
            }
        });

        sequence4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                memoire.choix_sequence = "sq4";

                String value = classe.getSelectedItem().toString();

                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n à saisir les pour pouvoir continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();

                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de : " + disciplines.getSelectedItem().toString() + " pour la sequence 4");

                        memoire.numero_sequence = "s4";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {

                    } catch (JSONException ex) {

                    }
                }
            }
        });

        sequence5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                memoire.choix_sequence = "sq5";

                String value = classe.getSelectedItem().toString();

                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n à saisir les pour pouvoir continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();

                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de : " + disciplines.getSelectedItem().toString() + " pour la sequence 5");

                        memoire.numero_sequence = "s5";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {

                    } catch (JSONException ex) {

                    }
                }
            }
        });

        sequence6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                memoire.choix_sequence = "sq6";

                String value = classe.getSelectedItem().toString();

                if (value.equals("Classes diponibles")) {
                    JOptionPane.showMessageDialog(null, "Veuillez séléctionner votre classe\n à saisir les pour pouvoir continuer");
                } else {
                    final String list_des_elevs_par_classe = classe.getSelectedItem().toString();

                    try {
                        memoire.classe = list_des_elevs_par_classe;
                        trait.setText("Notes de : " + disciplines.getSelectedItem().toString() + " pour la sequence 6");

                        memoire.numero_sequence = "s6";
                        liste_eleve_par_classe1(list_des_elevs_par_classe);
                    } catch (IOException ex) {

                    } catch (JSONException ex) {

                    }
                }
            }
        });
        setTitle(" Bienvenue à vous  <<     M. " + memoire.nom_user + "     >> dans votre espace de travail ");
    }//GEN-LAST:event_formWindowOpened

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked

        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();

        String mo = JOptionPane.showInputDialog(null, "Veuillez Entrer votre nouveau mot de passe", "Entrer votre nouveau mot de passe", JOptionPane.QUESTION_MESSAGE);
        if (mo == null || (mo != null && ("".equals(mo)))) {
            JOptionPane.showMessageDialog(null, "votre mot de passe ne doit pas etre null", "ECHEC", JOptionPane.ERROR_MESSAGE);

        } else {
            String no = JOptionPane.showInputDialog(null, "Veuillez Confirmer le mot de passe !", "Cofirmation mot de passe", JOptionPane.INFORMATION_MESSAGE);
            if (mo.equals(no)) {
                JOptionPane.showMessageDialog(null, "Votre compte a été bien modifié", "SUCCES", JOptionPane.INFORMATION_MESSAGE);

                try {
                    mdofifier_mot_passe(mo);
                } catch (IOException ex) {
                    Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(Page_enseignant3.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mot de passe mal confirmer", "ECHEC", JOptionPane.ERROR_MESSAGE);

            }
        }


    }//GEN-LAST:event_jMenu2MouseClicked

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        Curseur.startWaitCursor(this);

        System.out.println("j'ai essaye");
        String tsequence = "s1";
        memoire.decision = 0;
        if (tsequence.equals("s1")) {
            try {
                enregistrer_notess1();

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        } else if (tsequence.equals("s2")) {
            try {
                enregistrer_notess2();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tsequence.equals("s3")) {
            try {
                enregistrer_notess3();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tsequence.equals("s4")) {
            try {
                enregistrer_notess4();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tsequence.equals("s5")) {
            try {
                enregistrer_notess5();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (tsequence.equals("s6")) {
            try {
                enregistrer_notess6();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
            }
        }
        int tsequ = memoire.decision;
        System.out.print("reponse :" + tsequ);

        if (tsequ != 1) {
            JOptionPane.showMessageDialog(null, "Enregistrement réussie !");
            Curseur.stopWaitCursor(this);
        }
    }//GEN-LAST:event_saveActionPerformed

    private void annulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annulerActionPerformed

        int option = showConfirmDialog(null, "Voulez-vous quitter cette page ?", "Arrêt de la saisie des notes",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            JFrame Page_enseignant = new connexion();
            Page_enseignant.setVisible(true);
            dispose();

        }

// TODO add your handling code here:
    }//GEN-LAST:event_annulerActionPerformed

    private void disciplinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disciplinesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_disciplinesActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        JFrame autre = new autres();
        autre.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3MouseClicked

    private void impressionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_impressionActionPerformed
        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] lnaiss = new String[count];

        List<impressionmodel_11> eleve = new ArrayList<impressionmodel_11>();

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        int ip = 0;
        for (int i = 0; i < count; i++) {
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            lnaiss[i] = tableau_eleves.getValueAt(i, 3).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {

                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {
                    impressionmodel_11 ed = new impressionmodel_11();
                    co.add(code[i]);
                    ed.nom(nom[i]);
                    ed.date(naissance[i]);
                    ed.lnaissance(lnaiss[i]);
                    ed.sexe(note[i]);
                    eleve.add(ed);
                    ip = 1;
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);
                    ip = 0;
                }

            } catch (Exception ex) {
                memoire.decision = 1;
                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes sont toutes entieres ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }

        }
        if (ip == 1) {
            new imprimernotes(eleve, trait.getText());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_impressionActionPerformed

    public void mdofifier_mot_passe(String id) throws IOException, JSONException {

        PasswordAuthentication f = new PasswordAuthentication();

        String pass = f.hash(id);
        System.out.println("MOT de passe hashe : " + pass);

        String url = memoire.modifier_mot_passe;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "password=" + pass + "&id_user=" + memoire.id_user;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, test = null;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            test = inputLine.trim();

            System.out.println(inputLine);

        }
        Curseur.stopWaitCursor(this);

    }

    private void liste_eleve_par_classe1(String classe_choisie) throws IOException, JSONException {
        Curseur.startWaitCursor(this);

        String url = memoire.liste_eleve_par_classe;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + classe_choisie + "&cle=" + memoire.numero_sequence + "&matiere=" + disciplines.getSelectedItem().toString();

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }

        org.json.JSONObject bj = new JSONObject(inline2);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
        String address = jsonArray.getJSONObject(0).getString("nom");

        List<model_eleves> liste_eleve = new ArrayList<model_eleves>();
        effectif.setText("Effectif total : " + jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject s11 = jsonArray.getJSONObject(i);

            String code = s11.getString("id_eleve");

            String nom_eleve = s11.getString("nom");
            String classe = s11.getString("date_naissance");
            String note1_eleve = s11.getString(memoire.choix_sequence);
            String note2_eleve = s11.getString("lieu_naissance");
            System.out.println("Seq en cours de traitement  : " + note1_eleve);

            liste_eleve.add(new model_eleves(code, nom_eleve, classe, note2_eleve, note1_eleve));
            System.out.println(nom_eleve + " " + memoire.choix_sequence);

        }
        in.close();

        final JInternalFrame jInternalFrame1;
        jInternalFrame1 = new JInternalFrame("Internal Frame ", true, true, true, true);

        //final JFrame jInternalFrame2 = new JFrame("Liste des eleves");    
        String[] columnNames = {"CODE", "NOM ET PRENOMS", "Date de naissance", "Lieu de naissance", "Note", "", ""};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (final model_eleves detail : liste_eleve) {
            Vector<String> row = new Vector<>();
            row.add(detail.id());
            row.add(detail.getUsername());
            row.add(detail.getclasse());
            row.add(detail.getnote());
            row.add(detail.getnote2());

            //            row.add(detail.getnote());
            //            row.add(detail.getnote2());
            model.addRow(row);
        }

        tableau_eleves.setModel(model);

        JTable table = new JTable(model);
        //JScrollPane scrollPane = new JScrollPane( tableau_eleves );

        JLabel lblHeading = new JLabel("GESTION DES ELEVES ET COMPTE ANSEIGNANT");
        JButton enrregistrer = new JButton("ENRREGISTRER");
        JButton fermer = new JButton("ANNULER");

        lblHeading.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 16));
        enrregistrer.setPreferredSize(new Dimension(150, 40));
        enrregistrer.setBackground(Color.blue);
        fermer.setPreferredSize(new Dimension(150, 30));
        fermer.setBackground(Color.red);
        JPanel chiffre = new JPanel();

        chiffre.setPreferredSize(new Dimension(200, 50));
        chiffre.add(fermer);
        chiffre.add(enrregistrer);
        jInternalFrame1.getContentPane().add(lblHeading, BorderLayout.PAGE_START);
        //jInternalFrame1.getContentPane().add(scrollPane,BorderLayout.CENTER);
        jInternalFrame1.getContentPane().add(chiffre, BorderLayout.AFTER_LINE_ENDS);

        jInternalFrame1.setSize(900, 50);

        jInternalFrame1.setVisible(true);
        System.out.println(table);

        Curseur.stopWaitCursor(this);

    }

    private void liste_lasse_en_fonction_Ensiegnant(String classe_choisie) throws IOException, JSONException {

        Curseur.startWaitCursor(this);

        String url = memoire.liste_lasse_en_fonction_Ensiegnant;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//                
//         String nom =   detail.getUsername();
//         String note = detail.getclasse();
//         String pass = detail.getnote();

        String urlParameters = "enseignant=" + classe_choisie;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }
        try {
            org.json.JSONObject bj = new JSONObject(inline2);
            org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
            String address = jsonArray.getJSONObject(0).getString("classe");
            List<liste_classe> liste_classe = new ArrayList<liste_classe>();
            classes = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eleve = jsonArray.getJSONObject(i);

                String nom_eleve = eleve.getString("classe");
                classe.addItem(nom_eleve);

                classes[i] = nom_eleve;

            }
            List<String> liste_class = new ArrayList<String>();
            for (final liste_classe detail : liste_classe) {
                liste_class.add(detail.getliste_class());
            }
            Curseur.stopWaitCursor(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Désolé,mais il se pourrait que\nCet enseignant n'enseigne dans aucune classe");

            Curseur.stopWaitCursor(this);
        }

    }

    private void liste_matiere() throws IOException, JSONException {
        Curseur.startWaitCursor(this);

        String url = memoire.liste_matiere_enseignant;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + classe.getSelectedItem().toString() + "&enseignant=" + memoire.nom_user;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();

        }
        System.out.println(inline2);

        org.json.JSONObject bj = new JSONObject(inline2);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
        disciplines.setEnabled(true);
        effectif.setText("Vous avez  dans cette salle: " + jsonArray.length() + " Matiere(s) ");

        disciplines.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"          "}));
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject eleve = jsonArray.getJSONObject(i);

            String nom_eleve = eleve.getString("matiere");
            System.out.println(nom_eleve);

            disciplines.addItem(nom_eleve);

            in.close();
        }
        Curseur.stopWaitCursor(this);

    }

    private void recupererID_matiere() throws IOException, JSONException {

        Curseur.startWaitCursor(this);
        String url = memoire.id_matiere;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "classe=" + classe.getSelectedItem().toString() + "&matiere=" + disciplines.getSelectedItem().toString();

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();

        }
        System.out.println(inline2);

        org.json.JSONObject bj = new JSONObject(inline2);
        org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
        disciplines.setEnabled(true);

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject eleve = jsonArray.getJSONObject(i);

            String nom_eleve = eleve.getString("matiere");
            System.out.println(nom_eleve);

            disciplines.addItem(nom_eleve);

            in.close();
        }
        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess1() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();

            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {

                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }

            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes sont toutes entieres ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }

        }
        String url = memoire.erregistrer_note;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user + "&periode=" + memoire.choix_sequence;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        System.out.println(response);
        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess2() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {
                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        }
        String url = memoire.erregistrer_notes2;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        System.out.println(response);
        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess3() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {
                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        }
        String url = memoire.erregistrer_notes3;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        System.out.println(response);

        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess4() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {
                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        }
        String url = memoire.erregistrer_notes4;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user;

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        System.out.println(response);
        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess5() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {
                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        }
        String url = memoire.erregistrer_notes5;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        System.out.println(response);
        Curseur.stopWaitCursor(this);

    }

    public void enregistrer_notess6() throws IOException {
        Curseur.startWaitCursor(this);

        int count = tableau_eleves.getRowCount();
        int col = tableau_eleves.getColumnCount();
        String[] id = new String[count];
        String[] nom = new String[count];
        String[] naissance = new String[count];
        String[] code = new String[count];
        String[] note = new String[count];
        String[] recived_qty = new String[count];
        String[] rejected_qty = new String[count];

        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            //id[i] = tableau_eleves.getValueAt(i,0).toString();
            nom[i] = tableau_eleves.getValueAt(i, 1).toString();
            naissance[i] = tableau_eleves.getValueAt(i, 2).toString();
            code[i] = tableau_eleves.getValueAt(i, 0).toString();
            note[i] = tableau_eleves.getValueAt(i, 4).toString();
            try {
                if (Float.parseFloat(note[i]) <= 20 && Float.parseFloat(note[i]) >= 00) {

                    matie.add(code[i]);
                    co.add(note[i]);
                } else {
                    memoire.decision = 1;
                    JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

                }
            } catch (Exception ex) {
                memoire.decision = 1;

                JOptionPane.showMessageDialog(null, "Un élève se retrouve avec une note pas normale \nBien vouloir reverifier leurs notes ", " ATTENTION A LA SAISIE!", JOptionPane.ERROR_MESSAGE);

            }
        }
        String url = memoire.erregistrer_notes6;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "matiere=" + disciplines.getSelectedItem().toString() + "&nom=" + matie
                + "&note=" + co + "&classe=" + memoire.classe + "&enseignant=" + memoire.nom_user;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response);

        Curseur.stopWaitCursor(this);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Page_enseignant3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Page_enseignant3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Page_enseignant3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Page_enseignant3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Page_enseignant3().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annuler;
    private javax.swing.JComboBox<String> classe;
    private javax.swing.JComboBox<String> disciplines;
    private javax.swing.JLabel effectif;
    private javax.swing.JButton impression;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton save;
    private javax.swing.JTable tableau_eleves;
    private javax.swing.JLabel trait;
    // End of variables declaration//GEN-END:variables

    class JInternalFrameTest extends JFrame {

        JInternalFrameTest() {
            setTitle("JInternalFrame");
            setJInternalFrame();
            setSize(700, 300);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        void setJInternalFrame() {
            JInternalFrame jn = new JInternalFrame("InternalFrame", true, true, true);
            jn.setLayout(new FlowLayout());
            jn.add(new JButton("JButton"));
            jn.setVisible(true);
            add(jn);
        }
    }
}

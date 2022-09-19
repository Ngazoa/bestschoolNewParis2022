/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.pack.impression.impressionRecusScolarite;
import etab.inscrireEleve;
import etab.message;
import etab.moratoire;
import etablissement.memoire;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import org.json.JSONException;
import security.fonctinoratoire;
import security.fonctions;
import security.fonctionsParams;

/**
 *
 * @author ASSUS GAMER
 */
public class incriptioneleveEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String Nomtranche, nomcycle, prix;
    private String rang;
    private String idMatiere;
    private String NomMatiere;
    private JTable j;
    private String contact;
    private String ideleve;
    private String matricule;
    private String classe;
    private String sexe;
    private String nom;
    private String prenom;
    private String av;
    private String date;
    private boolean te;

    public incriptioneleveEditor(JCheckBox checkbox) {
        super(checkbox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        try {
            nom = table.getValueAt(row, 2).toString();
            prenom = table.getValueAt(row, 3).toString();
            av = table.getValueAt(row, 4).toString();
            ideleve = table.getValueAt(row, 1).toString();
            date = table.getValueAt(row, 5).toString();
            te = Boolean.valueOf(table.getValueAt(row, 5).toString());

            classe = table.getValueAt(row, 6).toString();
            matricule = table.getValueAt(row, 8).toString();
            contact = table.getValueAt(row, 7).toString();

        } catch (Exception e) {

        }
        switch (column) {
            case 9:
                label = "Edit";
                break;
            case 5:
                label = "impM";
                break;
            case 6:
                label = "payer";
                break;
            case 7:
                label = "imp";
                break;
            case 8:
                label = "moratoire";
                break;
            default:
                label = "payer";
                break;
        }

        memoire.nom = NomMatiere;
        j = table;
        memoire.id_absence = idMatiere;
        button.setText(label);
        isPushed = true;

        return button;

    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            switch (label) {
                case "Edit":
                    //new editcycle(j, idMatiere, NomMatiere).setVisible(true);
                    //new editerTranchePaiement(j, Nomtranche, nomcycle, prix, rang, idMatiere).setVisible(true);

                    new inscrireEleve(nom, prenom, date, av, classe, matricule, contact, ideleve).setVisible(true);
                    break;
                case "payer":
                    if (te) {
                        new message().info("Desole, mais vous ne pouvez payer a \nnouveau cette meme tranche");
                    } else {

                        boolean check = false;
                        try {
                            check = new fonctinoratoire().checkMoratoireEleveEXists(ideleve);
                        } catch (SQLException ex) {
                            Logger.getLogger(incriptioneleveEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (check) {
                            new message().error("Moratoire existant", ""
                                    + "Desole, mais vous ne pouvez inscrire directement cet eleve"
                                    + " \ncar un moratoire est deja en cours sur cette section \n"
                                    + "Veuillez terminer avec le reste a payer ");
                        } else {
                            int ok = new message().confirmation("Attention action irrevocable", " Souhaitez vous vraiment inscrire"
                                    + "\nPour " + nom + " ?");
                            if (ok == 0) {
                                try {
                                    System.err.println("TRANCHEEEE---" + ideleve + "****" + nom);
                                    new fonctions().validerPaiementTranche(nom, memoire.id_eleve, ideleve, memoire.classe, null);
                                    new fonctions().getEleveTranchesPaiement(j, memoire.classe, memoire.id_eleve);
                                } catch (SQLException ex) {
                                    Logger.getLogger(incriptioneleveEditor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                    break;
                case "imp":
                    if (!te) {
                        new message().warning("attention ", "Impossible de generer un recu de paiement\n"
                                + "Car cette periode ou tranche n'a  encore ete solde");
                    } else {
                        System.out.println(" >>> ||| " + memoire.nom);
                        new impressionRecusScolarite(memoire.nom, ideleve, av, memoire.classe, prenom);
                        new message().info("Action terminee avec succes");
                    }
                    break;
                case "impM":

                    new impressionRecusScolarite(memoire.nom, ideleve, av, memoire.classe, prenom);
                    new message().info("Action terminee avec succes");

                    System.out.println(" >>> ||| " + memoire.nom);
//                        new impressionRecusMoratoire(memoire.nom,ideleve,  av, nom, prenom);
//                        new message().info("Action terminee avec succes");

                    break;
                case "moratoire":
                    if (te) {
                        new message().warning("attention ", "Impossible de gerer le moratoire de cette\n periode de paiement\n"
                                + "Car cette periode ou tranche a deja ete solde");
                    } else {
                        new moratoire(nom, ideleve, av, j).setVisible(true);

                    }
                    break;
                default:
                    int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                            + "definitivement la tranche  | " + NomMatiere + " | de votre systeme souhaitez-vous \nvraiment continuer ?");
                    if (ep == 0) {
                        try {

                            boolean b = new fonctions().checkTrancheDelete(ideleve, nom, nomcycle, rang, prix);
                            if (!b) {
                                new fonctions().delete(idMatiere, "id_tranche", "tranche_scolarite", "etat_tranche");
                                new fonctionsParams().getTranchePaiement(j);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(classeEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
            }
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

}

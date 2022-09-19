/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.listeElevepayeTranche;
import etab.message;
import etablissement.memoire;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import security.fonctions;
import security.fonctionsParams;

/**
 *
 * @author ASSUS GAMER
 */
public class paiementPersonnelEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String Nomtranche, nomcycle, prix;
    private String rang;
    private String idMatiere;
    private String NomMatiere;
    private JTable j;

    public paiementPersonnelEditor(JCheckBox checkbox) {
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
        NomMatiere = table.getValueAt(row, 2).toString();
        nomcycle = table.getValueAt(row, 3).toString();
        Nomtranche = table.getValueAt(row, 2).toString();
        //prix = table.getValueAt(row, 4).toString();
        rang = table.getValueAt(row, 3).toString();
        idMatiere = table.getValueAt(row, 1).toString();
        switch (column) {
            case 4:
                label = "Edit";
                break;
            case 7:
                label = "voir";
                break;
            default:
                label = "Supp";
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
            //new editcycle(j, idMatiere, NomMatiere).setVisible(true);
            //new editerTranchePaiement(j, Nomtranche, nomcycle, prix, rang, idMatiere).setVisible(true);
                case "Edit":
                    break;
                case "voir":
                    new listeElevepayeTranche(idMatiere).setVisible(true);
                    break;
                default:
                    int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                            + "definitivement la Periode de paiement\n   | " + NomMatiere + " | \nde votre systeme souhaitez-vous \nvraiment continuer ?");
                    if (ep == 0) {
                        try {
                            
                            boolean b = new fonctions().checkPeriodpaiDelete(idMatiere);
                            if (!b) {
                                new fonctions().delete(idMatiere, "id_periode_paie", "periode_paie", "etat_periode");
                                new fonctionsParams().getPersonnelPeriodePaiement(j);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(classeEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }   break;
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

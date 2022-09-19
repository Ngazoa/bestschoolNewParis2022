/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.detailClasse;
import etab.editEnseignant;
import etab.message;
import etab.paiementAction;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import security.fonctions;

/**
 *
 * @author ASSUS GAMER
 */
public class enseignantEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String Nom, prenom, tel, sexe;
    private String idMatiere;
    private String idUser;
    private String NomUser;
    private JTable j;

    public enseignantEditor(JCheckBox checkbox) {
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
        idUser = table.getValueAt(row, 1).toString();
        NomUser = table.getValueAt(row, 2).toString();
        prenom = table.getValueAt(row, 2).toString();
        sexe = table.getValueAt(row, 3).toString();
        label = (column == 6) ? "Edit" : "Supp" + table.getValueAt(row, 1).toString();
        if (column == 4) {
            label = "paie";
        } else {
            tel = table.getValueAt(row, 4).toString();

        }
        button.setText(label);
        isPushed = true;
        j = table;
        return button;

    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if (label.equalsIgnoreCase("Edit")) {
                new editEnseignant(idUser, NomUser, prenom, sexe, tel).setVisible(true);
                try {
                    new fonctions().getEnseignantEtablissement(j);
                } catch (SQLException ex) {
                    Logger.getLogger(enseignantEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (label.equals("paie")) {
              new paiementAction(idUser, NomUser).setVisible(true);
            } else {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement le Personnel  | " + NomUser + " | de votre systeme souhaitez-vous \nvraiment continuer ?");
                if (ep == 0) {
                    try {
                        new fonctions().delete(idUser, "id_user", "users", "etat_user");
                        new fonctions().getEnseignantEtablissement(j);
                    } catch (SQLException ex) {
                        Logger.getLogger(classeEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // JOptionPane.showMessageDialog(button, label + ":En cours..." + label2);
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

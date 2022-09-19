/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.editcycle;
import etab.editionMatiereName;
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

/**
 *
 * @author ASSUS GAMER
 */
public class cycleEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String NomUser;
    private String idUser;
    private String idMatiere;
    private String NomMatiere;
    private JTable j;

    public cycleEditor(JCheckBox checkbox) {
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
        idMatiere = table.getValueAt(row, 1).toString();
        if (column == 3) {
            label = "Edit";
        
        } else {
            label = "Supp";
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
            if (label.equalsIgnoreCase("Edit")) {
                new editcycle(j,idMatiere,NomMatiere).setVisible(true);
            } else {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement la Matiere  | " + NomMatiere + " | de votre systeme souhaitez-vous \nvraiment continuer ?");
                if (ep == 0) {
                    try {
                        new fonctions().delete(idMatiere, "id_matiere", "matiere", "etat_matiere");
                        new fonctions().getMatiereEnseignantParClasseAnneeEncours(j, new fonctions().getIClasse(memoire.classe));

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

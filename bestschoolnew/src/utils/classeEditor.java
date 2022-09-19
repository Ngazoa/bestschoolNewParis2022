/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.detailClasse;
import etab.editionClasseName;
import etab.message;
import etablissement.memoire;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Properties;
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
public class classeEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private String label2;
    private String Nomclasse;
    private String idClasse;
    private boolean isPushed;
    Properties p = new Properties();
    JTable j;
    private String rangclasse;

    public classeEditor(JCheckBox checkbox) {
        super(checkbox);
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener((ActionEvent e) -> {
            fireEditingStopped();
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
        if (column == 5) {
            label = "voir";
        } else if (column == 6) {
            label = "Edit";
        } else {
            label = "Supp";
        }
        //label = (column == 5) ? "voir" : "Edit";
        label2 = table.getValueAt(row, 1).toString();
        memoire.id_absence = label2;
        memoire.bp = label2;
        memoire.classe = table.getValueAt(row, 2).toString();
        Nomclasse = table.getValueAt(row, 2).toString();
        rangclasse = table.getValueAt(row, 4).toString();
        memoire.nom = Nomclasse;
        j = table;
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            p.setProperty("id_classe", label2);

            if (label.equalsIgnoreCase("voir")) {
                new detailClasse().setVisible(true);

            } else if (label.equalsIgnoreCase("Edit")) {
                new editionClasseName(j, rangclasse, Nomclasse).setVisible(true);
            } else {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement la classe | " + Nomclasse + " | souhaitez-vous \nvraiment continuer ?");
                System.err.println(">>>>>>>>>" + ep);
                if (ep == 0) {
                    try {
                        new fonctions().delete(label2, "id_classe", "classe", "etat_classe");
                        new fonctions().getClasseEtablissement(j);
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

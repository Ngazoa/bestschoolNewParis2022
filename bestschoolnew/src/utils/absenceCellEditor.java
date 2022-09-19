/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.absenceJustification;
import etab.listeAnsencesIndividuelles;
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
public class absenceCellEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String NomUser, i, s;
    private String idUser;
    private String idMatiere;
    private String NomMatiere;
    private JTable j;

    public absenceCellEditor(JCheckBox checkbox) {
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
        i = table.getValueAt(row, 3).toString();
        s = table.getValueAt(row, 4).toString();
        if (column == 5) {
            label = "plus";

        } else {
            label = "just";
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
            if (label.equalsIgnoreCase("plus")) {
               new listeAnsencesIndividuelles(idMatiere,NomMatiere).setVisible(true);
            } else { 
               new absenceJustification(idMatiere,NomMatiere,s,i).setVisible(true);
                try {
                    new fonctions().getAllAbsencesEleves(idMatiere, j);
                } catch (SQLException ex) {
                    Logger.getLogger(absenceCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.editerClasseMatiereEnseignant;
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
public class matiereEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private String NomUser;
    private String idUser;
    private String idMatiere;
    private String NomMatiere;
    private JTable j;
    private String coef;
    private String gp;
    private String ensg;
    private String mat;
    private String hor;
    private String esg;

    public matiereEditor(JCheckBox checkbox) {
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
        mat = table.getValueAt(row, 2).toString();
        esg = table.getValueAt(row, 3).toString();

        switch (column) {
            case 7:
                label = "Edit1";
                gp = table.getValueAt(row, 4).toString();
                coef = table.getValueAt(row, 5).toString();
                hor = table.getValueAt(row, 6).toString();
                break;
            case 4:
                label = "Edit";
                break;
            case 8:
                gp = table.getValueAt(row, 4).toString();
                coef = table.getValueAt(row, 5).toString();
                hor = table.getValueAt(row, 6).toString();
                label = "supp1";
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
            if (label.equalsIgnoreCase("Edit")) {
                new editionMatiereName(j).setVisible(true);    
            } else if (label.equalsIgnoreCase("Edit1")) {
                new editerClasseMatiereEnseignant(j, mat, esg, gp, coef, hor).setVisible(true);
            } else if (label.equalsIgnoreCase("supp1")) {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement la Matiere  | " + NomMatiere + " | de la classe de -" + memoire.classe + "- souhaitez-vous \nvraiment continuer ?");
                if (ep == 0) {
                    try {
                        new fonctions().delete(idMatiere, "id_m_c", "matiere_classe_enseignant", "etat_mc");
                        new fonctions().getMatiereEnseignantParClasseAnneeEncours(j, new fonctions().getIClasse(memoire.bp));

                    } catch (SQLException ex) {
                        Logger.getLogger(classeEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement la Matiere  | " + NomMatiere + " | de votre systeme souhaitez-vous \nvraiment continuer ?");
                if (ep == 0) {
                    try {
                        new fonctions().delete(idMatiere, "id_matiere", "matiere", "etat_matiere");
                    new fonctions().getMatiereEtablissement(j);

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

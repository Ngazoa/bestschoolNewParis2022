/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import etab.editerEleve;
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
import model.modelStaticEleve;
import security.fonctions;

/**
 *
 * @author ASSUS GAMER
 */
public class eleveEditor extends DefaultCellEditor {

    modelStaticEleve mdl = new modelStaticEleve();
    protected JButton button;
    private String label;
    private String label2;
    private String Nomclasse;
    private String idClasse;
    private boolean isPushed;
    Properties p = new Properties();
    private JTable j;

    public eleveEditor(JCheckBox checkbox) {
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

        label = (column == 12) ? "Edit" : "supp";
        label2 = table.getValueAt(row, 1).toString();
        memoire.id_absence = label2;
        //memoire.classe = table.getValueAt(row, 2).toString();
        Nomclasse = table.getValueAt(row, 2).toString();
        memoire.nom = Nomclasse;
        j = table;
        try {
            table.getCellEditor().stopCellEditing();
        } catch (Exception ex) {

        }
        modelStaticEleve.setnomMatiere(table.getValueAt(row, 2).toString());
        modelStaticEleve.setprenom(table.getValueAt(row, 3).toString());
        modelStaticEleve.setsexe(table.getValueAt(row, 4).toString());
        modelStaticEleve.setlieu(table.getValueAt(row, 6).toString());
        modelStaticEleve.setdate(table.getValueAt(row, 5).toString());
        modelStaticEleve.settelephone(table.getValueAt(row, 7).toString());
        modelStaticEleve.setpere(table.getValueAt(row, 8).toString());
        modelStaticEleve.setmere(table.getValueAt(row, 9).toString());
        modelStaticEleve.setidMatiere(table.getValueAt(row, 1).toString());
        modelStaticEleve.setredoublant(table.getValueAt(row, 10).toString());

        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            //  p.setProperty("id_classe", label2);

            if (label.equalsIgnoreCase("Edit")) {
                new editerEleve(j).setVisible(true);

            } else {
                int ep = new message().confirmation("Attention !", "Vous etes sur le point de supprimer\n"
                        + "definitivement l'eleve | " + Nomclasse + " | souhaitez-vous \nvraiment continuer ?");
                System.err.println(">>>>>>>>>" + ep);

                if (ep == 0) {
                    try {
                        new fonctions().delete(label2, "id_eleve", "eleve_classe", "etat_ec");
                        new fonctions().getEleveparclasse(j, new fonctions().getIClasse(memoire.classe));
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

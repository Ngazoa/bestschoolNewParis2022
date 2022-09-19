/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class ButtonRenderer extends JButton implements TableCellRenderer{
@Override
public Component getTableCellRendererComponent( JTable table,Object value,boolean isSelected, boolean isFocus,int row, int col) {
//On écrit dans le bouton avec la valeur de la cellule
setText((value != null) ? value.toString() : "");
//on retourne notre bouton
return this;
}
}
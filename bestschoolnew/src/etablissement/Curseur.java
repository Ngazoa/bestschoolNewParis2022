/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etablissement;

import static etablissement.Cursors.DEFAULT_CURSOR;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;

/**
 *
 * @author Benito
 */
public class Curseur implements Cursors {
  public final static MouseAdapter mouseAdapter = 
    new MouseAdapter() {};

  public Curseur() {
  }
public static void startJDIALOGWaitCursor(JDialog component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
  public static void startWaitCursor(JFrame component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  } public static void startWaitCursor1(JFrame component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }public static void jtablestartWaitCursor(JInternalFrame component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
  public static void printCursor(Component component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }

  public static void stopWaitCursor(JFrame component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }public static void jtablestopWaitCursor(JInternalFrame component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }public static void jablestopWaitCursor(JInternalFrame component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
  public static void stopcomponent(Component component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }  
  public static void stoJMenuItem(JMenuItem component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
  public static void stoJDialogC(JDialog component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
public static void startJMenuItemWaitCursor(JMenuItem  component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }public static void stoJMenu(JMenu component) { 
    component.setCursor(DEFAULT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
public static void startJMenuWaitCursor(JMenu  component) { 
    component.setCursor(WAIT_CURSOR);
    component.addMouseListener(mouseAdapter);
    component.setVisible(true);
  }
    

    

}
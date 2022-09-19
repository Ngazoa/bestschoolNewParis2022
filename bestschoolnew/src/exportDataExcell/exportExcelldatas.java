/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exportDataExcell;

import etab.message;
import etablissement.Curseur;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

/**
 *
 * @author ASSUS GAMER
 */
public class exportExcelldatas {

    private static File image;

    public void writeToExcell(JTable table, JFrame jf, int k) throws IOException {
        //new WorkbookFactory();
        Curseur.startWaitCursor(jf);
        try {
            table.getCellEditor().stopCellEditing();
        } catch (Exception ex) {
        }
        String path;
        JFileChooser chooser;
        FileNameExtensionFilter filter;
        chooser = new JFileChooser();
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
        filter = new FileNameExtensionFilter("Fichiers xlsx,xls,csv", "xlsx", "xls", "csv");
        chooser.addChoosableFileFilter(filter);

        int i = chooser.showSaveDialog(jf);
        if (i == JFileChooser.APPROVE_OPTION) {
            image = chooser.getSelectedFile();
            path = image.getAbsolutePath();
            if (image != null) {
                if (!image.getName().toLowerCase().endsWith(".xls")) {
                    image = new File(image.getParentFile(), image.getName() + ".xls");
                }
                exportTable(table, image, k);
                //new  WorkbookFactory();
//                Workbook wb = new XSSFWorkbook();
//                //new XSSFWorkbook(); //Excell workbook
//                org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet(); //WorkSheet
//                org.apache.poi.ss.usermodel.Row row = sheet.createRow(2); //Row created at line 3
//                TableModel model = table.getModel(); //Table model
//
//                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0); //Create row at line 0
//                for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
//                    headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
//                }
//
//                for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
//                    for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
//                        row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
//                    }
//
//                    //Set the row to the next one in the sequence 
//                    row = sheet.createRow((rows + 3));
//                }
//                wb.write(new FileOutputStream(path));
            }
        }
        new message().info("Importation terminee !");
        Curseur.stopWaitCursor(jf);
    }

    public void writeToExcellJDialog(JTable table, JDialog jf, int k) throws IOException {
        //new WorkbookFactory();
        String path;
        Curseur.startJDIALOGWaitCursor(jf);
        JFileChooser chooser;
        FileNameExtensionFilter filter;
        chooser = new JFileChooser();
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
        filter = new FileNameExtensionFilter("Fichiers xlsx,xls,csv", "xlsx", "xls", "csv");
        chooser.addChoosableFileFilter(filter);

        int i = chooser.showSaveDialog(jf);
        if (i == JFileChooser.APPROVE_OPTION) {
            image = chooser.getSelectedFile();
            path = image.getAbsolutePath();
            if (image != null) {
                if (!image.getName().toLowerCase().endsWith(".xls")) {
                    image = new File(image.getParentFile(), image.getName() + ".xls");
                }
                exportTable(table, image, k);
                //new  WorkbookFactory();
//                Workbook wb = new XSSFWorkbook();
//                //new XSSFWorkbook(); //Excell workbook
//                org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet(); //WorkSheet
//                org.apache.poi.ss.usermodel.Row row = sheet.createRow(2); //Row created at line 3
//                TableModel model = table.getModel(); //Table model
//
//                org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0); //Create row at line 0
//                for (int headings = 0; headings < model.getColumnCount(); headings++) { //For each column
//                    headerRow.createCell(headings).setCellValue(model.getColumnName(headings));//Write column name
//                }
//
//                for (int rows = 0; rows < model.getRowCount(); rows++) { //For each table row
//                    for (int cols = 0; cols < table.getColumnCount(); cols++) { //For each table column
//                        row.createCell(cols).setCellValue(model.getValueAt(rows, cols).toString()); //Write value
//                    }
//
//                    //Set the row to the next one in the sequence 
//                    row = sheet.createRow((rows + 3));
//                }
//                wb.write(new FileOutputStream(path));
            }
        }
        new message().info("Importation terminee !");

        Curseur.stoJDialogC(jf);
    }

    public void exportTable(JTable jTable1, File file, int k) throws IOException {

        TableModel model = jTable1.getModel();
        FileWriter out = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(out);
        for (int i = 0; i < model.getColumnCount(); i++) {
            try {
                bw.write(model.getColumnName(i) + "\t");
            } catch (Exception e) {
                bw.write("  " + "\t");
                new message().error("erreur", "echec lors de l'exportation !");

            }
        }
        Object data;
        bw.write("\n");
        for (int i = 0; i < model.getRowCount(); i++) {
            System.err.println("//ll " + model.getRowCount() + "------" + i);
            for (int j = 0; j < model.getColumnCount() - k; j++) {
                if (k == 2 && j == 1) {
                    bw.write("--" + "\t");
                } else {
                    System.out.print("Write////" + (i + " + " + j));
                    data=jTable1.getValueAt(i, j);
                    if (data==null) {
                        bw.write("  - " + "\t");
                    } else {
                        bw.write(jTable1.getValueAt(i, j).toString() + "\t");

                    }
                }
            }
            bw.write("\n");
        }
        bw.close();
        System.out.print("Write out to" + file);

    }
}

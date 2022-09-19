package util;

import static java.awt.Dialog.DEFAULT_MODALITY_TYPE;
import javax.swing.*;
import util.print.MainPrintFrame;

public class PrintPreviewFrame extends JDialog {

    MainPrintFrame printPanel;

    JTable dataTable;
    JLabel titleLabel;
    String printTitle;

    public PrintPreviewFrame(JTable dataTable, JLabel title, String printTitle) {
        //super("Printing "+printTitle);
        this.setModalityType(DEFAULT_MODALITY_TYPE);
        super.setTitle("Printing " + printTitle);
        this.dataTable = dataTable;
        this.titleLabel = title;
        this.setResizable(true);
        this.printTitle = printTitle;
        init();
        printPanel.setPrintSize(600, 550);
        setVisible(true);

    }

    void init() {

        this.setSize(650, 465);
        this.setLocation(30, 30);

        printPanel = new MainPrintFrame(dataTable, titleLabel);
        printPanel.setTitle(printTitle);

        setContentPane(printPanel);

        printPanel.refresh(dataTable, titleLabel);

    }
}

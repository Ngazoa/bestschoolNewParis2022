package etablissement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showConfirmDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Michelle Wafo
 */
public class ajout_classe extends JDialog {

    private javax.swing.JScrollPane jScrollPane1;
    private final ZDialogInfo zInfo = new ZDialogInfo();
    private JLabel nomLabel, coeff, sexeLabel, cheveuxLabel, ageLabel,
            tailleLabel, taille2Label, icon;
    private JRadioButton tranche1, tranche2, tranche3, tranche4;
    private JComboBox sexe, matieres, coefficient, liste_des_classe, cycle;
    private JTextField nom, taille;
    private javax.swing.JTable jTable1;
    private javax.swing.JInternalFrame jInternalFrame2;
    int count = 1;
    private Object[][] data;
    String[] id;
    String[] name;
    String[] no;
    String[] cof;
    // JLabel trait = new JLabel("");

    /**
     * Constructeur
     *
     * @param parent
     * @param title
     * @param modal
     * @throws org.json.JSONException
     * @throws java.io.IOException
     */
    public ajout_classe(JFrame parent, String title, boolean modal) throws JSONException, IOException {
        super(parent, title, modal);
        listecycledefinie();

        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.initComponent();
    }

    public ZDialogInfo showZDialog() {
        this.setVisible(true);
        return this.zInfo;
    }

    private void initComponent() throws IOException, JSONException {
        jTable1 = new javax.swing.JTable();
        icon = new JLabel(new ImageIcon("error.png"));
        JPanel panIcon = new JPanel();
        panIcon.setLayout(new BorderLayout());
        panIcon.add(icon);

        jScrollPane1 = new javax.swing.JScrollPane();

//Le nom
        JPanel panNom = new JPanel();
        panNom.setPreferredSize(new Dimension(490, 590));
        nom = new JTextField();
        nom.setText(memoire.nom_conserve);
        nom.setPreferredSize(new Dimension(150, 25));
        panNom.setBorder(BorderFactory.createTitledBorder("Caracteristiques de la classe "));
        nomLabel = new JLabel("");

        JButton ajou_classe = new JButton("++");
        JButton retrait = new JButton("- -");

        panNom.add(nomLabel);
        panNom.add(cycle);

        panNom.add(nom);
        panNom.add(ajou_classe);
        panNom.add(retrait);

        Object supp = null;

        Object[][] data = {{null, null, supp},
        {null, null, supp},
        {null, null, supp},
        {null, null, supp}
        };

        String[] columnNames = {"", "Matiere", "Coeff", "Numro Groupe"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        jTable1.setModel(model);
        jTable1.setRowHeight(20);
        //jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
//        ZModel zModel = new ZModel(data, columnNames);
//        jTable1 = new JTable(zModel);
        jScrollPane1.setViewportView(jTable1);

        ajou_classe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                Object[] donnee = new Object[]{count + ") Nouvelle Matiere", null, null, null};
                count += 1;
                ((DefaultTableModel) jTable1.getModel()).addRow(donnee);
            }
        });
        retrait.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                ((DefaultTableModel) jTable1.getModel()).removeRow(count - 2);
                count -= 1;
            }
        });

        panNom.add(jScrollPane1);

        JPanel content = new JPanel();
        content.add(panNom);

        JPanel control = new JPanel();
        control.setBackground(Color.darkGray);

        JButton okBouton = new JButton("Enregistrer");
        okBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (!cycle.getSelectedItem().toString().equals("Entrer le niveau/cycle/classe")) {

                        ajouter_classe();

                    } else {
                        showConfirmDialog(null, "Veuillez selectionner votre Entrer le niveau/cycle/classe\nPour pouvoir enregistrer", "Erreur de creation",
                                JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);

                    }
                } catch (IOException | JSONException ex) {
                }

            }

        });
        JButton cancelBouton = new JButton("Annuler");
        cancelBouton.addActionListener((ActionEvent arg0) -> {
            dispose();

        });

        control.add(okBouton);
        control.add(cancelBouton);
        this.getContentPane().add(panIcon, BorderLayout.WEST);
        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);

    }

    class ZModel extends AbstractTableModel {

        private Object[][] data;
        private final String[] title;

        /**
         * Constructeur
         *
         * @param data
         * @param title
         */
        public ZModel(Object[][] data, String[] title) {
            this.data = data;
            this.title = title;
        }

        public String getColumnName(int col) {
            return this.title[col];
        }

        @Override
        public int getRowCount() {
            return this.data.length;
        }

        @Override

        public int getColumnCount() {
            return this.title.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            return this.data[row][col];
        }

        public void addRow(Object[] data) {
            int indice = 0, nbRow = this.getRowCount(), nbCol
                    = this.getColumnCount();
            Object[][] temp = this.data;
            this.data = new Object[nbRow + 1][nbCol];
            for (Object[] value : temp) {
                this.data[indice++] = value;
            }
            this.data[indice] = data;
            temp = null;
//Cette méthode permet d'avertir le tableau que les données ontété modifiées
//Ce qui permet une mise à jours complète du tableau
            this.fireTableDataChanged();
        }

        public void removeRow(int position) {
            int indice = 0, indice2 = 0, nbRow = this.getRowCount() - 1, nbCol
                    = this.getColumnCount();
            Object[][] temp = new Object[nbRow][nbCol];
            for (Object[] value : this.data) {
                if (indice != position) {
                    temp[indice2++] = value;
                }
                System.out.println("Indice = " + indice);
                indice++;
            }
            this.data = temp;
            temp = null;
//Cette méthode permet d'avertir le tableau que les données ontété modifiées
//Ce qui permet une mise à jours complète du tableau
            this.fireTableDataChanged();
        }
    }

    public void ajouter_classe() throws IOException, JSONException {

        memoire.tel_conserve = nom.getText();

        int count = jTable1.getRowCount();
        int col = jTable1.getColumnCount();
        id = new String[count];
        name = new String[count];
        String[] naissance = new String[count];
        String[] sexe = new String[count];
        no = new String[count];
        cof = new String[count];
        String[] gpes = new String[count];
        ArrayList<String> matie = new ArrayList<String>();
        ArrayList<String> co = new ArrayList<String>();
        ArrayList<String> gpe = new ArrayList<String>();

        try {
            for (int i = 0; i < count; i++) {

                if (jTable1.getValueAt(i, 1).toString() != "" && jTable1.getValueAt(i, 2).toString() != "" && jTable1.getValueAt(i, 3).toString() != "") {

                    name[i] = jTable1.getValueAt(i, 1).toString();
                    cof[i] = jTable1.getValueAt(i, 2).toString();
                    gpes[i] = jTable1.getValueAt(i, 3).toString();

                    matie.add(name[i]);
                    co.add(cof[i]);
                    gpe.add(gpes[i]);
                }

            }
            System.out.println(matie
                    + "Liste : " + co);

            String url = memoire.ajout_classe;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("POST");
            //on.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String urlParameters = "classe=" + memoire.tel_conserve + "&matiere=" + matie
                    + "&coeff=" + co + "&createur=" + memoire.nom_user + "&gpe=" + gpe + "&cycle=" + cycle.getSelectedItem().toString();
            con.setDoOutput(true);
            try {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            } catch (IOException ex) {
                showConfirmDialog(null, "Erreur inattendue"
                        + "\nImpossible de creer une nouvelle salle de classe avec des matieres \nayant des propriétés vides! Bien vouloire remplir \ncorrectement les données", "Erreur de creation",
                        JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
            }

            con.setConnectTimeout(250000);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));

            System.out.println(urlParameters);

            String inputLine;
            StringBuffer response = new StringBuffer();
            String kay = null;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                kay = inputLine.trim();
            }
            System.out.println("Voici ton affichage : " + kay.trim());

            org.json.JSONObject bj = new JSONObject(kay.trim());
            // org.json.JSONObject jsonArray = bj.getJSONObject("erreur");
            org.json.JSONObject erreur = bj.getJSONObject("erreur");
            String ereur = erreur.getString("erreur");
            System.out.println(ereur);

            if (ereur.equals("oui")) {
                JOptionPane.showMessageDialog(null, "Cette classe existe deja");

            } else {

                JOptionPane.showMessageDialog(null, "Votre classe a été creé avec succès ");
            }
            in.close();

            System.out.println(response);

        } catch (Exception e) {

            showConfirmDialog(null, "Impossible de créer une nouvelle salle de classe avec des matieres \nayant des propriétés vides! Bien vouloir remplir \ncorrectement les données ou les champs vides", "Erreur de creation",
                    JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
            supprimer_eleve();
        }

    }

    public void supprimer_eleve() throws IOException, JSONException {

        String url = memoire.supprimer_classes;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        //con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//                
//         String nom =   detail.getUsername();
//         String note = detail.getclasse();
//         String pass = detail.getnote();

        String urlParameters = "classe=" + memoire.nom_conserve;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        System.out.println(urlParameters);

        String inputLine, inline2 = null;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine.trim());
            inline2 = inputLine.trim();
            System.out.println("Response Code : " + response.append(inputLine));

        }

    }

    private void listecycledefinie() throws IOException, JSONException {
        String url = memoire.liste_cycles;
        try {
            URL urln = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urln.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                Scanner sc = new Scanner(urln.openStream());
                String inline = null;

                while (sc.hasNext()) {
                    inline = sc.nextLine().trim();
                }

                org.json.JSONObject bj = new JSONObject(inline);
                org.json.JSONArray jsonArray = bj.getJSONArray("victoire");
                String[] cy = {"Entrer le niveau/cycle/classe"};

                cycle = new JComboBox(cy);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject eleve = jsonArray.getJSONObject(i);

                    String nom_eleve = eleve.getString("nom_cycle");
                    cycle.addItem(nom_eleve);

                    sc.close();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erreur survenue (" + e + ") Aucun niveau de paiement detecté \nVeuillez vous rassurez d'avoir au préalable definie \nles niveaux ou cycles de paiement ");

        }
    }
}

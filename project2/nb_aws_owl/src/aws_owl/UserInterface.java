/*
 * UserInterface.java
 *
 * Created on May 9, 2008, 10:31 PM
 */

package aws_owl;

import com.hp.hpl.jena.query.QueryParseException;
import java.awt.FileDialog;
import javax.swing.JOptionPane;
import javax.swing.text.Caret;
import org.w3c.dom.*;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;

/**
 *
 * @author  phelps
 */
public class UserInterface extends javax.swing.JFrame {
    
    AwsHandler awsHandler = new AwsHandler();		
    TripleLoader tripleLoader = new TripleLoader();
    boolean adding = true;
    
    /** Creates new form UserInterface */
    public UserInterface() {
        initComponents();
        awsSearchType.addItem(new AWSSearchType(AWSSearchType.BOOK));
        awsSearchType.addItem(new AWSSearchType(AWSSearchType.DVD));
        awsSearchType.addItem(new AWSSearchType(AWSSearchType.MUSIC));
        awsSearchTypeLabel.setText(((AWSSearchType)awsSearchType.getSelectedItem()).getSearchSubject());
        awsPropertyList.removeAllItems();
        awsPropertyList.addItem("aws:hasName");
        awsPropertyList.addItem("aws:hasTitle");
        awsPropertyList.addItem("aws:isAuthorOf");
        awsPropertyList.addItem("aws:isPerformerIn");
        awsPropertyList.addItem("aws:isWrittenBy");
        awsPropertyList.addItem("aws:isPerformedBy");
        awsPropertyList.addItem("aws:isConnectedTo");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        awsSearchType = new javax.swing.JComboBox();
        awsSearchTextBox = new javax.swing.JTextField();
        awsAddButton = new javax.swing.JButton();
        awsSearchTypeLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        SPARQLQueryButton = new javax.swing.JButton();
        awsAgentList = new javax.swing.JComboBox();
        awsProductList = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        SPARQLQueryTextArea = new javax.swing.JTextArea();
        awsPropertyList = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileMenuExitItem = new javax.swing.JMenuItem();
        modelMenu = new javax.swing.JMenu();
        modelValidateMenuItem = new javax.swing.JMenuItem();
        modelSaveMenuItem = new javax.swing.JMenuItem();
        modelClearMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Justin Choi & Phelps Williams CS431 Project 2");
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("AWS Query"));
        awsSearchType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                awsSearchTypeActionPerformed(evt);
            }
        });

        awsAddButton.setText("Add");
        awsAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                awsAddButtonActionPerformed(evt);
            }
        });

        awsSearchTypeLabel.setText(" ");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(awsSearchTextBox, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(awsSearchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(awsAddButton)
                        .addContainerGap())
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(awsSearchTypeLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                        .add(422, 422, 422))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(awsSearchTypeLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(awsAddButton)
                    .add(awsSearchType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(awsSearchTextBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("SPARQL Query"));
        SPARQLQueryButton.setText("Query");
        SPARQLQueryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SPARQLQueryButtonActionPerformed(evt);
            }
        });

        awsAgentList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                awsAgentListActionPerformed(evt);
            }
        });

        awsProductList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                awsProductListActionPerformed(evt);
            }
        });

        SPARQLQueryTextArea.setColumns(20);
        SPARQLQueryTextArea.setRows(5);
        SPARQLQueryTextArea.setText("PREFIX aws: <http://www.owl-ontologies.com/Ontology1209425965.owl#>\nSELECT DISTINCT ?x \nWHERE {?x aws:hasName 'Sid Haig'.}");
        jScrollPane1.setViewportView(SPARQLQueryTextArea);

        awsPropertyList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                awsPropertyListActionPerformed(evt);
            }
        });

        jLabel1.setText("Agent URI's");

        jLabel2.setText("Properties");

        jLabel3.setText("Product URI's");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(17, 17, 17)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(SPARQLQueryButton)
                        .add(jPanel2Layout.createSequentialGroup()
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .add(awsAgentList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 146, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(21, 21, 21))
                                .add(jPanel2Layout.createSequentialGroup()
                                    .add(jLabel1)
                                    .add(137, 137, 137)))
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(awsPropertyList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 178, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel2))
                            .add(24, 24, 24)
                            .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(jLabel3)
                                .add(awsProductList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 173, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 569, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(21, 21, 21))
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {awsAgentList, awsProductList}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(175, Short.MAX_VALUE)
                .add(SPARQLQueryButton))
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(jLabel3))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(awsAgentList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(awsProductList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(awsPropertyList, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        jScrollPane2.setViewportView(outputTextArea);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
        );

        fileMenu.setText("File");
        fileMenuExitItem.setText("Exit");
        fileMenuExitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuExitItemActionPerformed(evt);
            }
        });

        fileMenu.add(fileMenuExitItem);

        jMenuBar1.add(fileMenu);

        modelMenu.setText("Model");
        modelValidateMenuItem.setLabel("Validate");
        modelValidateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelValidateMenuItemActionPerformed(evt);
            }
        });

        modelMenu.add(modelValidateMenuItem);

        modelSaveMenuItem.setText("Save");
        modelSaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelSaveMenuItemActionPerformed(evt);
            }
        });

        modelMenu.add(modelSaveMenuItem);

        modelClearMenuItem.setText("Clear");
        modelClearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelClearMenuItemActionPerformed(evt);
            }
        });

        modelMenu.add(modelClearMenuItem);

        jMenuBar1.add(modelMenu);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(23, 23, 23)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void awsPropertyListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_awsPropertyListActionPerformed
// TODO add your handling code here:
        try{
        Caret pos = SPARQLQueryTextArea.getCaret();
        if(!adding)
            SPARQLQueryTextArea.getDocument().insertString(pos.getDot(), (String)awsPropertyList.getSelectedItem(), null);
        }
        catch(BadLocationException e) {}
    }//GEN-LAST:event_awsPropertyListActionPerformed

    private void awsProductListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_awsProductListActionPerformed
// TODO add your handling code here:
        try{
        Caret pos = SPARQLQueryTextArea.getCaret();
        if(!adding)
            SPARQLQueryTextArea.getDocument().insertString(pos.getDot(), "<"+TripleLoader.aws + Utility.forURL((String)awsProductList.getSelectedItem()) + ">", null);
        }
        catch(BadLocationException e) {}
    }//GEN-LAST:event_awsProductListActionPerformed

    private void awsAgentListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_awsAgentListActionPerformed
        try{
        Caret pos = SPARQLQueryTextArea.getCaret();
        if(!adding)
            SPARQLQueryTextArea.getDocument().insertString(pos.getDot(), "<"+TripleLoader.aws + Utility.forURL((String)awsAgentList.getSelectedItem()) + ">", null);
        }
        catch(BadLocationException e) {}
    }//GEN-LAST:event_awsAgentListActionPerformed


    
    private void modelClearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelClearMenuItemActionPerformed
        tripleLoader.initializeModel();
    }//GEN-LAST:event_modelClearMenuItemActionPerformed

    private void modelSaveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelSaveMenuItemActionPerformed
        String filename = getSaveFileLocation();
        if(filename == null)
            return;
        tripleLoader.writeToFile(filename);
    }//GEN-LAST:event_modelSaveMenuItemActionPerformed

    private void modelValidateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelValidateMenuItemActionPerformed
        outputTextArea.setText(tripleLoader.validateOntology());
    }//GEN-LAST:event_modelValidateMenuItemActionPerformed

    private void awsSearchTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_awsSearchTypeActionPerformed
        awsSearchTypeLabel.setText(((AWSSearchType)awsSearchType.getSelectedItem()).getSearchSubject());
    }//GEN-LAST:event_awsSearchTypeActionPerformed

    private void SPARQLQueryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SPARQLQueryButtonActionPerformed
        String result;
        if(SPARQLQueryTextArea.getText() == null)
            return;
        try
        {
            result = tripleLoader.runQuery(SPARQLQueryTextArea.getText());
        } 
        catch (QueryParseException qpe)
        {
            result = "Query Parse Exception: The syntax of your search string has errors.\n"+qpe.getMessage();
            
        }
        outputTextArea.setText(result);
    }//GEN-LAST:event_SPARQLQueryButtonActionPerformed

    private void awsAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_awsAddButtonActionPerformed
        Document doc = null;
        
        try {
            doc = awsHandler.query(
                    awsSearchTextBox.getText(), 
                    (AWSSearchType)awsSearchType.getSelectedItem());
        } 
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error in AWS query");
        }
        
        if(doc == null)
        {
            JOptionPane.showMessageDialog(null, "Error in AWS query");
            return;
        }
        
        try
        {
            tripleLoader.load(doc, (AWSSearchType)awsSearchType.getSelectedItem());
        } 
        catch (TripleLoader.InvalidAWSResponseException e)
        {
            JOptionPane.showMessageDialog(null, "Error in AWS response");
        }
        
        // Populate the dropdowns
        ArrayList<String> agents = tripleLoader.populate("agent");
        ArrayList<String> products = tripleLoader.populate("product");
        int i;
        adding = true;
        awsAgentList.removeAllItems();
        awsProductList.removeAllItems();
        
        for(i = 0; i < Math.max(agents.size(), products.size()); i++)
        {
            if(i < agents.size()) {
                awsAgentList.addItem(agents.get(i));
                System.out.println("Agent "+agents.get(i));
            }
            if(i < products.size()) {
                awsProductList.addItem(products.get(i));
                System.out.println("Product "+products.get(i));
            }
        }
        adding = false;
    }//GEN-LAST:event_awsAddButtonActionPerformed

    private void fileMenuExitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuExitItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_fileMenuExitItemActionPerformed
    
    private String getSaveFileLocation()
    {
        FileDialog fd = new FileDialog(new javax.swing.JFrame(), "Save Model Location", FileDialog.SAVE);
        fd.setFile("out.xml");
        fd.setDirectory(System.getProperty("user.dir"));
        fd.setVisible(true);
        return fd.getFile();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInterface().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SPARQLQueryButton;
    private javax.swing.JTextArea SPARQLQueryTextArea;
    private javax.swing.JButton awsAddButton;
    private javax.swing.JComboBox awsAgentList;
    private javax.swing.JComboBox awsProductList;
    private javax.swing.JComboBox awsPropertyList;
    private javax.swing.JTextField awsSearchTextBox;
    private javax.swing.JComboBox awsSearchType;
    private javax.swing.JLabel awsSearchTypeLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMenuExitItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem modelClearMenuItem;
    private javax.swing.JMenu modelMenu;
    private javax.swing.JMenuItem modelSaveMenuItem;
    private javax.swing.JMenuItem modelValidateMenuItem;
    private javax.swing.JTextArea outputTextArea;
    // End of variables declaration//GEN-END:variables
    
}

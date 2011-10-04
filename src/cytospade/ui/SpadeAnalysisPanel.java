/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SpadeAnalysisPanel.java
 *
 * Created on Sep 22, 2011, 7:27:18 AM
 */

package cytospade.ui;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.actions.LoadNetworkTask;
import cytoscape.data.SelectEventListener;
import cytoscape.logger.CyLogger;
import cytoscape.view.CyNetworkView;
import cytoscape.visual.GlobalAppearanceCalculator;
import cytoscape.visual.NodeAppearanceCalculator;
import cytoscape.visual.VisualMappingManager;
import cytoscape.visual.VisualPropertyDependency;
import cytoscape.visual.VisualPropertyType;
import cytoscape.visual.VisualStyle;
import cytospade.CytoSpade;
import cytospade.FCSOperations;
import cytospade.SpadeContext;
import cytospade.SpadeContext.NormalizationKind;
import cytospade.SpadeController;
import cytospade.VisualMapping;
import cytospade.WorkflowWizard;
import cytospade.WorkflowWizardPanels;
import giny.model.GraphPerspective;
import giny.view.NodeView;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

/**
 *
 * @author mlinderm
 */
public class SpadeAnalysisPanel extends javax.swing.JPanel {

    private SpadeContext     spadeCxt;
    private VisualMapping    visualMapping;
    private FCSOperations    fcsOperations;
    private ScatterPlotPanel scatterPlot;
    private ReentrantLock    panelLock;

    /** Creates new form SpadeAnalysisPanel */
    public SpadeAnalysisPanel(SpadeContext spadeCxt) {
        this.spadeCxt = spadeCxt;

        // Find the global_boundaries.table file it exists, and create appropiate visual mapping
        File[] boundaryFiles = spadeCxt.getPath().listFiles(new FilenameFilter() {
            public boolean accept(File f, String name) {
                return (name.matches("global_boundaries.table"));
            }
        });

        if (boundaryFiles.length == 1) {
            this.visualMapping = new VisualMapping(boundaryFiles[0]);
        } else if (boundaryFiles.length == 0) {
            this.visualMapping = new VisualMapping();
        } else {
            JOptionPane.showMessageDialog(null, "Error: Found more than one global_boundaries.table file.");
            return;
        }

        panelLock = new ReentrantLock();

        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FilenameSelect = new javax.swing.JComboBox(spadeCxt.getFCSFiles());
        FilenameLabel = new javax.swing.JLabel();
        ColoringSelect = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        RangeSelect = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        PDFButton = new javax.swing.JButton();
        TableButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        NumberEventsLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        StatisticTextArea = new javax.swing.JTextArea();
        PlotContainer = new javax.swing.JScrollPane();

        setMinimumSize(new java.awt.Dimension(420, 720));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(440, 720));

        FilenameSelect.setMaximumRowCount(20);
        FilenameSelect.setSelectedIndex(-1);
        FilenameSelect.setRenderer(this.FilenameSelectRenderer());
        FilenameSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FilenameSelectActionPerformed(evt);
            }
        });

        FilenameLabel.setLabelFor(FilenameSelect);
        FilenameLabel.setText("File");

        ColoringSelect.setToolTipText("Select attribute for coloring nodes");
        ColoringSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColoringSelectActionPerformed(evt);
            }
        });

        jLabel1.setLabelFor(ColoringSelect);
        jLabel1.setText("Coloring Attribute");

        RangeSelect.setMaximumRowCount(20);
        RangeSelect.setModel(RangeSelectModel());
        RangeSelect.setToolTipText("Global sets colorscale using min/max across all files, local uses min/max of selected file");
        RangeSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RangeSelectActionPerformed(evt);
            }
        });

        jLabel2.setText("Coloring Range");

        PDFButton.setText("Produce PDFs");
        PDFButton.setToolTipText("Generate PDF tree plots using current Cytoscape layout");
        PDFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PDFButtonActionPerformed(evt);
            }
        });

        TableButton.setText("Produce Tables");
        TableButton.setToolTipText("Generate CSV tables for each attributes with columns for all files");
        TableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TableButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close SPADE");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        NumberEventsLabel.setText("Select a file to display network and bi-axial plot");

        StatisticTextArea.setColumns(20);
        StatisticTextArea.setEditable(false);
        StatisticTextArea.setRows(5);
        StatisticTextArea.setText("Select nodes to compute T-statistics...");
        jScrollPane1.setViewportView(StatisticTextArea);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(PDFButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(TableButton))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, CloseButton)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, NumberEventsLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator1)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, PlotContainer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, FilenameLabel)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator2)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel1)
                                    .add(jLabel2))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(RangeSelect, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(ColoringSelect, 0, 253, Short.MAX_VALUE)))
                            .add(FilenameSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 331, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(FilenameLabel)
                    .add(FilenameSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(ColoringSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(RangeSelect, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(2, 2, 2)
                .add(NumberEventsLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(PlotContainer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(PDFButton)
                    .add(TableButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(CloseButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void FilenameSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FilenameSelectActionPerformed
        CyNetworkView cnv = Cytoscape.getCurrentNetworkView();
        GraphPerspective network = (GraphPerspective) Cytoscape.getCurrentNetwork();

        // Close the current network, saving the X and Y coords for reuse
        //   This is a hackerish way to tell if no network is loaded. For some reason,
        //   Cytoscape.getCurrentNetwork[View]() always returns something.
        if (!network.nodesList().isEmpty()) {
            this.saveMetadata(true);
        }

        //Open the new network, applying the X and Y coords if available
        if (FilenameSelect.getSelectedIndex() >= 0) {
            LoadNetworkTask.loadFile(spadeCxt.getGMLFiles()[FilenameSelect.getSelectedIndex()], true);

            //Find the layout.table file if it exists
            File[] layoutFiles = spadeCxt.getPath().listFiles(new FilenameFilter() {
                public boolean accept(File f, String name) {
                    return (name.matches("layout.table"));
                }
            });
            if (layoutFiles.length == 1) {
                loadMetadata(layoutFiles[0]);
            } else if (layoutFiles.length > 1) {
                JOptionPane.showMessageDialog(null, "Error: Found more than one layout.table file");
                return;
            }

            // Network Interactions
            //
            Cytoscape.getCurrentNetworkView().addNodeContextMenuListener(new NodeContextMenu());
            Cytoscape.getCurrentNetworkView().fitContent();
            
            VisualMapping.populateNumericAttributeComboBox(ColoringSelect);  // Update the parameter combo box
            ColoringSelect.setSelectedIndex(0);

            RangeSelect.setSelectedIndex(0);
            spadeCxt.setNormalizationKind((NormalizationKind) RangeSelect.getSelectedItem());

            updateNodeSizeAndColors();

            // FCS file interactions
            //
            NumberEventsLabel.setText("Select a file to display network and bi-axial plot");
            StatisticTextArea.setText("Select nodes to compute T-statistics...");

            this.PlotContainer.setViewportView(null);
            this.scatterPlot   = null;
            this.fcsOperations = null;
            
            try {
                this.fcsOperations = new FCSOperations((File) FilenameSelect.getSelectedItem());
                this.scatterPlot   = new ScatterPlotPanel(this.fcsOperations);
                this.PlotContainer.setViewportView(this.scatterPlot);
                Cytoscape.getCurrentNetwork().addSelectEventListener(new HandleSelect());
                updateFCSConsumers();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "FCS file not found: "+FilenameSelect.getSelectedItem());
                return;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error reading FCS file: "+FilenameSelect.getSelectedItem());
                return;
            }           
        }
    }//GEN-LAST:event_FilenameSelectActionPerformed

    private void ColoringSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColoringSelectActionPerformed
        updateNodeSizeAndColors();
        Cytoscape.getCurrentNetworkView().redrawGraph(true, true);
    }//GEN-LAST:event_ColoringSelectActionPerformed

    private void RangeSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RangeSelectActionPerformed
        spadeCxt.setNormalizationKind((SpadeContext.NormalizationKind) RangeSelect.getSelectedItem());
        updateNodeSizeAndColors();
        Cytoscape.getCurrentNetworkView().redrawGraph(true, true);
    }//GEN-LAST:event_RangeSelectActionPerformed

    private void TableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TableButtonActionPerformed
        try {
            spadeCxt.authorMakePivot("pivotSPADE.R");
            SpadeController ctl = new SpadeController(spadeCxt.getPath(), "pivotSPADE.R");
            ctl.exec();
        } catch (IOException ex) {
            CyLogger.getLogger(CytoSpade.class.getName()).error(null, ex);
        }

    }//GEN-LAST:event_TableButtonActionPerformed

    private void PDFButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PDFButtonActionPerformed
        // Save current landscaping before generating PDFs
        saveMetadata(false);

        // Create the workflow wizard to walk user through setting up PDF generation
        WorkflowWizard wf = new WorkflowWizard(Cytoscape.getDesktop());

        WorkflowWizard.PanelDescriptor genPDFs = new WorkflowWizardPanels.GeneratePDFs(spadeCxt);
        wf.registerWizardPanel(WorkflowWizardPanels.GeneratePDFs.IDENTIFIER, genPDFs);

        wf.setCurrentPanel(WorkflowWizardPanels.GeneratePDFs.IDENTIFIER);
        int showModalDialog = wf.showModalDialog();

        if (showModalDialog == WorkflowWizard.CANCEL_RETURN_CODE) {
            return;
        } else if (showModalDialog != WorkflowWizard.FINISH_RETURN_CODE) {
            JOptionPane.showMessageDialog(null, "Error occured in workflow wizard.");
        }

    }//GEN-LAST:event_PDFButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        int returnvalue = JOptionPane.showConfirmDialog(null, "Close SPADE plug-in?", "Confirm close", JOptionPane.OK_CANCEL_OPTION);
        if (returnvalue == JOptionPane.OK_OPTION) {
            saveMetadata(true);
            //FIXME This will fail if the user loads another plug-in after loading SPADE
            Cytoscape.getDesktop().getCytoPanel(SwingConstants.WEST).remove(Cytoscape.getDesktop().getCytoPanel(SwingConstants.WEST).getCytoPanelComponentCount() - 1);
            return;
        } else {
            return;
        }
    }//GEN-LAST:event_CloseButtonActionPerformed

    private javax.swing.ListCellRenderer FilenameSelectRenderer() {
        return (new javax.swing.ListCellRenderer() {
            // Render FCS files as just File name (no path information, or long extensions)
            public Component getListCellRendererComponent(javax.swing.JList jlist, Object o, int idx, boolean isSelected, boolean bln1) {
                String name = "";
                if (o != null) {
                    name = ((File) o).getName();
                    name = name.substring(0, name.lastIndexOf(".density.fcs.cluster.fcs"));
                }
                javax.swing.JLabel label = new javax.swing.JLabel(name);

                label.setBackground(isSelected ? jlist.getSelectionBackground() : jlist.getBackground());
                label.setForeground(isSelected ? jlist.getSelectionForeground() : jlist.getForeground());
                label.setEnabled(jlist.isEnabled());
                label.setFont(jlist.getFont());
                label.setOpaque(true);

                return label;
            }
        });   
    }

    private javax.swing.DefaultComboBoxModel RangeSelectModel() {
        return new javax.swing.DefaultComboBoxModel(
            visualMapping.globalRangeAvailable()
                ? new SpadeContext.NormalizationKind[]{SpadeContext.NormalizationKind.GLOBAL, SpadeContext.NormalizationKind.LOCAL}
                : new SpadeContext.NormalizationKind[]{SpadeContext.NormalizationKind.LOCAL});
    }

    public void onExit() {
        this.saveMetadata(true);
    }

    //<editor-fold desc="Node Graph Controls" defaultstate="collapsed">
    /**
     * Handles node selection events
     */
    public class HandleSelect implements SelectEventListener {
        public void onSelectEvent(cytoscape.data.SelectEvent e) {
            updateFCSConsumers();
        }
    }

    private void updateFCSConsumers() {     
        (new SwingWorker<Integer,Void>() {
            @Override
            protected Integer doInBackground() throws Exception {

                fcsOperations.updateSelectedNodes();

                scatterPlot.updatePlot();

                if (fcsOperations.getSelectedNodesCount() == 0) {
                    panelLock.lock();
                    try {
                        NumberEventsLabel.setText("Displaying all " + fcsOperations.getEventCount() + " events...");
                        StatisticTextArea.setText("Select nodes to compute T-statistics...");
                    } finally {
                        panelLock.unlock();
                    }
                } else {
                    List<FCSOperations.AttributeValuePair> stats = fcsOperations.computeTStat();
                    StringBuilder sb = new StringBuilder(500);
                    for (int i = 0; i < Math.min(stats.size(), 5); i++) {
                        sb.append("T-statistic for ")
                          .append(stats.get(i).attribute)
                          .append(": ")
                          .append(stats.get(i).value)
                          .append("\n");
                    }

                    panelLock.lock();
                    try {
                        NumberEventsLabel.setText(
                            "Displaying " +
                            fcsOperations.getSelectedEventCount() +
                            " of " +
                            fcsOperations.getEventCount() +
                            " events..."
                            );
                        StatisticTextArea.setText(sb.toString());
                    } finally {
                        panelLock.unlock();
                    }
                }
                return 0;
            }
            
        }).execute();   
    }
    
    /**
     * Applies sizes and colors to the network view
     */
    private void updateNodeSizeAndColors() {
        // Skip mapping if no file is specified
        if ((FilenameSelect.getSelectedIndex() < 0) || (ColoringSelect.getSelectedIndex() < 0)) {
            return;
        }

        try {
            visualMapping.setCurrentMarkersAndRangeKind(
                    "percenttotal",
                    ColoringSelect.getSelectedItem().toString(),
                    spadeCxt.getNormalizationKind());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid choice of mapping parameters: " + e);
            return;
        }

        VisualMappingManager cyVMM = Cytoscape.getVisualMappingManager();

        try {

            VisualStyle spadeVS = cyVMM.getCalculatorCatalog().getVisualStyle("SPADEVisualStyle");
            if (spadeVS != null) {
                // Overwrite visual style, only way to get Cytoscape to reliably update
                cyVMM.getCalculatorCatalog().removeVisualStyle("SPADEVisualStyle");
            }
            spadeVS = new VisualStyle("SPADEVisualStyle");

            // Update with new calculators
            GlobalAppearanceCalculator globalAppCalc = new GlobalAppearanceCalculator();
            globalAppCalc.setDefaultNodeSelectionColor(Color.MAGENTA);
            globalAppCalc.setDefaultEdgeSelectionColor(Color.MAGENTA);
            spadeVS.setGlobalAppearanceCalculator(globalAppCalc);

            NodeAppearanceCalculator nodeAppCalc = new NodeAppearanceCalculator();
            nodeAppCalc.setCalculator(visualMapping.createColorCalculator());
            nodeAppCalc.setCalculator(visualMapping.createSizeCalculator());
            spadeVS.setNodeAppearanceCalculator(nodeAppCalc);

            // Set a few defaults now that we have overwritten the calculators
            VisualPropertyType.NODE_SHAPE.setDefault(spadeVS, cytoscape.visual.NodeShape.ELLIPSE);
            VisualPropertyType.NODE_FILL_COLOR.setDefault(spadeVS, Color.LIGHT_GRAY);
            spadeVS.getDependency().set(VisualPropertyDependency.Definition.NODE_SIZE_LOCKED, true);

            cyVMM.getCalculatorCatalog().addVisualStyle(spadeVS);
            cyVMM.setVisualStyle(spadeVS);
            Cytoscape.getCurrentNetworkView().setVisualStyle(spadeVS.getName());

        } catch (RuntimeException ex) {
            CyLogger.getLogger().error("Error Visual Mapping", ex);
        }
    }

    // <editor-fold desc="Utility Methods" defaultstate="collapsed">
    /**
     * Saves the user-defined network landscaping to a flat file
     * @param closeNetwork - whether or not to close the network after saving it.
     */
    private void saveMetadata(Boolean closeNetwork) {
        CyNetwork currentNetwork = Cytoscape.getCurrentNetwork();
        CyNetworkView currentNetworkView = Cytoscape.getCurrentNetworkView();

        try {
            FileWriter nstream = new FileWriter(new File(spadeCxt.getPath(), "nested.txt").getAbsolutePath());
            BufferedWriter nout = new BufferedWriter(nstream);
            for (CyNode node : (List<CyNode>) currentNetwork.nodesList()) {
                // Write out membership in nested networks
                GraphPerspective nestedNetwork = node.getNestedNetwork();
                if (nestedNetwork != null) {
                    for (CyNode nn : (List<CyNode>) nestedNetwork.nodesList()) {
                        if (!NodeContextMenuItems.MakeNestedNetwork.isNested(nn)) {
                            continue;
                        }

                        int id = Integer.parseInt(nn.getIdentifier());
                        nout.write((id + 1) + " ");
                    }
                    nout.write("\n");

                    // Restore original node structure
                    NodeContextMenuItems.UndoNestedNetwork.undoNestedNode(node);
                }
            }
            nout.close();
        } catch (IOException ex) {
            CyLogger.getLogger().error("Error read layout.table", ex);
            return;
        }

        // Save network layout
        try {
            FileWriter lstream = new FileWriter(new File(spadeCxt.getPath(), "layout.table").getAbsolutePath());
            BufferedWriter lout = new BufferedWriter(lstream);


            int nodeCount = currentNetwork.getNodeCount();
            double[][] pos = new double[nodeCount][2];

            for (CyNode node : (List<CyNode>) currentNetwork.nodesList()) {
                NodeView nodeView = currentNetworkView.getNodeView(node);

                int id;
                try {
                    id = Integer.parseInt(node.getIdentifier());
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (id > nodeCount) {
                    continue;
                }

                if (nodeView != null) {
                    pos[id][0] = nodeView.getXPosition();
                    pos[id][1] = -1.0 * nodeView.getYPosition();
                }
            }

            for (int i = 0; i < nodeCount; i++) {
                lout.write(pos[i][0] + " " + pos[i][1] + "\n");
            }

            lout.close();
        } catch (IOException ex) {
            CyLogger.getLogger().error("Error read layout.table", ex);
            return;
        }

        if (closeNetwork) {
            //Close the network that the user just left
            Cytoscape.destroyNetwork(currentNetwork);
            //This is the only way to clear the nodeAttributes. I don't really
            //know what it does though; found it by trial-and-error:
            Cytoscape.createNewSession();
            //(It's necessary to clear the nodeAttributes for the sake of
            //mapColor's functionality.
        }
    }

    /**
     * Reads and applies the user-defined network landscaping from a flat file
     * @param layoutFile
     */
    private void loadMetadata(File layoutFile) {
        CyNetwork currentNetwork = Cytoscape.getCurrentNetwork();
        CyNetworkView currentNetworkView = Cytoscape.getCurrentNetworkView();

        try {
            int curNode = 0, nodeCount = currentNetwork.getNodeCount();
            double[][] pos = new double[currentNetwork.getNodeCount()][2];

            Scanner scanner = new Scanner(layoutFile);
            while (scanner.hasNextLine()) {
                if (curNode >= nodeCount) {
                    break;
                }
                pos[curNode][0] = scanner.nextDouble();
                pos[curNode][1] = -1.0 * scanner.nextDouble();
                curNode++;
            }

            for (CyNode node : (List<CyNode>) currentNetwork.nodesList()) {
                int id;
                try {
                    id = Integer.parseInt(node.getIdentifier());
                } catch (NumberFormatException ex) {
                    continue;
                }
                if (id > nodeCount) {
                    continue;
                }

                NodeView nodeView = currentNetworkView.getNodeView(node);
                nodeView.setXPosition(pos[id][0]);
                nodeView.setYPosition(pos[id][1]);
            }


        } catch (FileNotFoundException ex) {
            CyLogger.getLogger().error("Error read layout.table", ex);
            return;
        }

        // Apply nesting loaded from nested.txt metadata
        try {
            Scanner scanner = new Scanner(new File(spadeCxt.getPath(), "nested.txt"));
            while (scanner.hasNextLine()) {
                Set nodes = new HashSet();
                for (String id : scanner.nextLine().split(" ")) {
                    try {
                        // Convert back to 0-indexed nodes
                        nodes.add(Cytoscape.getCyNode(Integer.toString(Integer.parseInt(id) - 1)));
                    } catch (NumberFormatException ex) {
                        CyLogger.getLogger().error("Invalid entry in nested.txt", ex);
                    }
                }
                // Apply nesting
                NodeContextMenuItems.MakeNestedNetwork.makeNestedNode(nodes);
            }

        } catch (FileNotFoundException ex) {
            CyLogger.getLogger().debug("Error reading nested.txt", ex);
            return;
        }
    }

    /**
     * Gets the OS
     */
    private String getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("win") >= 0) {
            return "windows";
        } else if (os.indexOf("mac") >= 0) {
            return "macintosh";
        } else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
            return "unix";
        } else {
            JOptionPane.showMessageDialog(null, "Invalid OS detection");
            return "error";
        }
    }
    // </editor-fold>

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JComboBox ColoringSelect;
    private javax.swing.JLabel FilenameLabel;
    private javax.swing.JComboBox FilenameSelect;
    private javax.swing.JLabel NumberEventsLabel;
    private javax.swing.JButton PDFButton;
    private javax.swing.JScrollPane PlotContainer;
    private javax.swing.JComboBox RangeSelect;
    private javax.swing.JTextArea StatisticTextArea;
    private javax.swing.JButton TableButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables

}

package pl.softwaremill.idea.pastieplugin.history;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Wrapping class for panel shown as a tool window with pasties history
 *
 * @author Tomasz Dziurko
 */
public class HistoryPanel {
    private JPanel rootPanel;
    private JTable historyTable;
    private JScrollPane historyTableScrollPane;
    private JPanel historyTablePanel;
    private JButton clearHistoryButton;
    private HistoryTableModel tableModel;

    public HistoryPanel() {
        this.tableModel = new HistoryTableModel();
        $$$setupUI$$$();

        clearHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                tableModel.clearModel();
                historyTablePanel.setVisible(false);
                rootPanel.repaint();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }


    private void createUIComponents() {
        historyTable = new JTable(tableModel);
        customizeHistoryTable(historyTable);
    }

    private void customizeHistoryTable(final JTable historyTable) {
        historyTable.setRowHeight(20);
        historyTable.getColumnModel().getColumn(0).setMaxWidth(50);
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(800);
        historyTable.getColumnModel().getColumn(2).setMinWidth(150);

        historyTable.getColumnModel().getColumn(3).setMinWidth(60);

        historyTable.getColumn("Action").setCellRenderer(new TableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, final Object buttonObject,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                return (JLabel) buttonObject;
            }
        });

        historyTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int column = historyTable.getColumnModel().getColumnIndexAtX(mouseEvent.getX());
                int row = mouseEvent.getY() / historyTable.getRowHeight();

                if (row < historyTable.getRowCount() && row >= 0 && column < historyTable.getColumnCount() && column >= 0) {
                    Object value = historyTable.getValueAt(row, column);
                    if (value instanceof JLabel) {
                        ((JLabel) value).getMouseListeners()[0].mouseClicked(mouseEvent);
                    }
                }
            }
        });
    }


    public void addNewItemToModel(HistoryItem historyItem) {
        tableModel.addItem(historyItem);
        if (tableModel.getRowCount() > 0) {
            historyTablePanel.setVisible(true);
            rootPanel.repaint();
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 1, new Insets(2, 2, 2, 2), -1, -1));
        rootPanel.setAlignmentY(0.5f);
        rootPanel.setAutoscrolls(true);
        rootPanel.setFocusable(false);
        rootPanel.setVisible(true);
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), label1.getFont().getSize()));
        label1.setText("Recent pasties");
        rootPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        historyTablePanel = new JPanel();
        historyTablePanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        historyTablePanel.setEnabled(true);
        historyTablePanel.setVisible(false);
        rootPanel.add(historyTablePanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        historyTableScrollPane = new JScrollPane();
        historyTableScrollPane.setVisible(true);
        historyTablePanel.add(historyTableScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        historyTable.setAutoResizeMode(4);
        historyTable.setBackground(new Color(-1118998));
        historyTable.setCellSelectionEnabled(true);
        historyTable.setFillsViewportHeight(true);
        historyTable.setFocusCycleRoot(true);
        historyTable.setIntercellSpacing(new Dimension(0, 0));
        historyTable.setRequestFocusEnabled(false);
        historyTable.setRowMargin(0);
        historyTable.setRowSelectionAllowed(true);
        historyTableScrollPane.setViewportView(historyTable);
        clearHistoryButton = new JButton();
        clearHistoryButton.setFont(new Font(clearHistoryButton.getFont().getName(), clearHistoryButton.getFont().getStyle(), clearHistoryButton.getFont().getSize()));
        clearHistoryButton.setText("Clear history");
        historyTablePanel.add(clearHistoryButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}

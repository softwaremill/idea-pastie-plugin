package pl.softwaremill.idea.pastieplugin.history;

import com.intellij.openapi.ide.CopyPasteManager;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Model for swing table showing pasties history
 *
 * @author Tomasz Dziurko
 */
public class HistoryTableModel extends AbstractTableModel {

    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

    private String[] headerNames = {"Time", "Shared code beginning", "Url to Pastie.org", "Action"};

    private List<HistoryItem> historyItems = new LinkedList<HistoryItem>();

    public void addItem(HistoryItem historyItem) {
        historyItems.add(0, historyItem);
        fireTableDataChanged();
    }

    public void clearModel() {
        historyItems.clear();
        fireTableDataChanged();
    }

    public int getRowCount() {
        return historyItems.size();
    }

    public int getColumnCount() {
        return headerNames.length;
    }

    public Object getValueAt(int row, int column) {
        final HistoryItem item = historyItems.get(row);

        Object columnValueObject = null;
        switch (column) {
            case 0:
                columnValueObject = formatter.format(item.getDate());
                break;
            case 1:
                columnValueObject = item.getSharedCodeSubstring();
                break;
            case 2:
                columnValueObject = item.getUrl();
                break;
            case 3:
                JLabel copyToClipboardLabel = createClickableJLabelWithPastieLink(item);
                columnValueObject = copyToClipboardLabel;
                break;
        }

        return columnValueObject;
    }

    private JLabel createClickableJLabelWithPastieLink(final HistoryItem item) {
        JLabel copyToClipboardLabel = new JLabel("  Copy link to clipboard ");
        copyToClipboardLabel.setForeground(new Color(0, 70, 255));
        copyToClipboardLabel.setFont(new Font(copyToClipboardLabel.getFont().getName(),
                Font.BOLD | Font.ITALIC, copyToClipboardLabel.getFont().getSize()));
        copyToClipboardLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                CopyPasteManager.getInstance().setContents(new StringSelection(item.getUrl()));
            }
        });
        return copyToClipboardLabel;
    }


    @Override
    public String getColumnName(int column) {
        return headerNames[column];
    }


}

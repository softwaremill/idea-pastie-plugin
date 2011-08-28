package pl.softwaremill.idea.pastieplugin.history;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.util.Date;

/**
 * Class responsible for populating history of shared code fragments and
 * associated links
 *
 * @author Tomasz Dziurko
 */
public class PastieHistory implements ProjectComponent {

    private Project project;
    private HistoryPanel historyPanel;

    public PastieHistory(Project project) {
        this.project = project;
    }

    public String getComponentName() {
        return "Share with Pastie";
    }

    private void initHistoryWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);

        historyPanel = new HistoryPanel();
        JPanel rootPanel = historyPanel.getRootPanel();

        ToolWindow myToolWindow = toolWindowManager.registerToolWindow("Share with Pastie", false, ToolWindowAnchor.BOTTOM);
        Content content = ContentFactory.SERVICE.getInstance().createContent(rootPanel, "", false);

        myToolWindow.getContentManager().addContent(content);
    }

    public void addItem(String codeFragment, String url) {
        historyPanel.addNewItemToModel(new HistoryItem(new Date(), formatCodeFragment(codeFragment), url));
    }

    private String formatCodeFragment(String codeFragment) {
        return codeFragment.trim().replace("\n", " ").replaceAll("\t","").replaceAll("    ", "");
    }

    public void projectOpened() {
        initHistoryWindow();
    }

    public void projectClosed() {
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }


}

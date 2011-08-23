package pl.tomaszdziurko.idea.pastieplugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class doing the main job. Sending selected content to pastie.org website.
 *
 * @author Tomasz Dziurko
 */
public class SendToPastieAction extends AnAction {

    public static String PASTIE_BASE_URL = "http://pastie.org/private/";

    private Pattern pattern = Pattern.compile("a href=\"http://pastie\\.org/\\d+?/wrap\\?key=(.+?)\">");


    @Override
    public void actionPerformed(AnActionEvent actionEvent) {

        String selection = extractSelectedText(actionEvent);
        int languageDropdownId = getCorrectLanguageDropdownId(actionEvent);

        if (selection == null || selection.trim().length() == 0) {
            Messages.showMessageDialog("There is nothing to share.", "Empty selection", Messages.getWarningIcon());
            return;
        }
        try {
            String pastedCodeFragmentUniqueKey = shareWithPastie(selection, languageDropdownId);
            Messages.showMessageDialog("Code fragment was shared successfully. Url to pastie.org is in your clipboard", "Paste successful", Messages.getInformationIcon());

            CopyPasteManager.getInstance().setContents(new StringSelection(PASTIE_BASE_URL + pastedCodeFragmentUniqueKey));
        }
        catch (Exception e) {
            Messages.showMessageDialog(
                    "Something went wrong and, exception name: " + e.getClass().getName() +"\n\n" +
                    "Problem message: \n" + e.getMessage(), "Error", Messages.getErrorIcon());
            e.printStackTrace();
        }
    }

    private String extractSelectedText(AnActionEvent actionEvent) {
        Editor editor = DataKeys.EDITOR.getData(actionEvent.getDataContext());

        return editor.getSelectionModel().getSelectedText();
    }

    private String extractFileExtension(AnActionEvent actionEvent) {
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(actionEvent.getDataContext());

        return file == null ? "" : file.getExtension();
    }

    private int getCorrectLanguageDropdownId(AnActionEvent actionEvent) {
         String fileExtension = extractFileExtension(actionEvent);

         LanguageMap languageMap = new LanguageMap();

         return languageMap.getLanguageDropdownIdFor(fileExtension);
    }

    private String shareWithPastie(String selection, int languageDropdownId) throws Exception {

        String response = shareAndGetResponse(selection, languageDropdownId);
        String pastedCodeUniqueKey = extractKeyFrom(response);

        return pastedCodeUniqueKey;
    }
    

    private String shareAndGetResponse(String selection, int languageDropdownId) throws IOException {
        URL url = new URL("http://pastie.org/pastes/create");
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

        String data = "paste[parser_id]=" + languageDropdownId +"&paste[authorization]=burger&paste[restricted]=1&paste[body]=" + convertNewLineCharacters(selection);
        writer.write(data);
        writer.flush();
        writer.close();

        StringBuffer answer = loadResponse(conn);
        
        return answer.toString();
    }

    private StringBuffer loadResponse(URLConnection conn) throws IOException {
        StringBuffer answer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            answer.append(line);
        }

        reader.close();
        return answer;
    }

    private String convertNewLineCharacters(String text) throws UnsupportedEncodingException {
        String encodedString = URLEncoder.encode(text, "UTF-8");
        return encodedString;
    }

    private String extractKeyFrom(String response) {
        Matcher matcher = pattern.matcher(response);

        if(matcher.find()) {
            String key = matcher.group(1);
            return key;
        }

        throw new RuntimeException("Sorry, I wasn't able to extract url to pasted code fragment. Please contact with author of this plugin.");
    }

}
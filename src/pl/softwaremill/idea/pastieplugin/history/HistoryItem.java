package pl.softwaremill.idea.pastieplugin.history;

import java.util.Date;

/**
 * Class storing information about single shared with Pastie code fragment
 *
 * @author Tomasz Dziurko
 */
public class HistoryItem {

    private Date date;
    private String sharedCodeSubstring;
    private String url;


    public HistoryItem(Date date, String sharedCodeSubstring, String url) {
        this.date = date;
        this.sharedCodeSubstring = sharedCodeSubstring;
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public String getSharedCodeSubstring() {
        return sharedCodeSubstring;
    }

    public String getUrl() {
        return url;
    }
}

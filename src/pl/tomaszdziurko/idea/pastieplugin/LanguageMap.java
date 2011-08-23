package pl.tomaszdziurko.idea.pastieplugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Class mapping file extensions with LanguageEnum
 *
 * @author Tomasz Dziurko
 */
public class LanguageMap {

    private Map<String, LanguageEnum> fileExtensionToLanguage = new HashMap<String, LanguageEnum>();

    public LanguageMap() {

        fileExtensionToLanguage.put("java", LanguageEnum.JAVA);
        fileExtensionToLanguage.put("js", LanguageEnum.JAVASCRIPT);
        fileExtensionToLanguage.put("sql", LanguageEnum.SQL);
        fileExtensionToLanguage.put("scala", LanguageEnum.SCALA);
        fileExtensionToLanguage.put("html", LanguageEnum.HTML_XML);
        fileExtensionToLanguage.put("htm", LanguageEnum.HTML_XML);
        fileExtensionToLanguage.put("xhtml", LanguageEnum.HTML_XML);
        fileExtensionToLanguage.put("xml", LanguageEnum.HTML_XML);
        fileExtensionToLanguage.put("css", LanguageEnum.CSS);

        fileExtensionToLanguage.put("properties", LanguageEnum.PLAIN_TEXT);
        fileExtensionToLanguage.put("php", LanguageEnum.PHP);
        fileExtensionToLanguage.put("sh", LanguageEnum.BASH_SHELL);
        fileExtensionToLanguage.put("py", LanguageEnum.PYTHON);
        fileExtensionToLanguage.put("c", LanguageEnum.C_C_PLUS_PLUS);


        //TODO add more file extensions mapped to correct languages
    }

    public int getLanguageDropdownIdFor(String fileExtension) {
        LanguageEnum languageEnum = fileExtensionToLanguage.get(fileExtension.toLowerCase());

        if(languageEnum == null) {
            return LanguageEnum.PLAIN_TEXT.getDropdownId();
        }
        else {
            return languageEnum.getDropdownId();
        }
    }
}

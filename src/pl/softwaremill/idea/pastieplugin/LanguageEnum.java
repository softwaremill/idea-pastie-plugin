package pl.softwaremill.idea.pastieplugin;

/**
 * Enum mapping language with id of dropdown specifying type of code highlights in http://pastie.org
 *
 * @author Tomasz Dziurko
 */

public enum LanguageEnum {

    OBJECTIVE_C_C_PLUS_PLUS(1),
    ACTION_SCRIPT(2),
    RUBY(3),
    RUBY_ON_RAILS(4),
    DIFF(5),
    PLAIN_TEXT(6),
    C_C_PLUS_PLUS(7),
    CSS(8),
    JAVA(9),
    JAVASCRIPT(10),
    HTML_XML(11),
    HTML_ERB_RAILS(12),
    BASH_SHELL(13),
    SQL(14),
    PHP(15),
    PYTHON(16),

    PERL(18),
    YAML(19),
    C_SHARP(20),
    GO(21),


    SCALA(32),
    CLOJURE(38),

    ;
    //TODO some languages still need to be added

    private LanguageEnum(int dropdownId) {
        this.dropdownId = dropdownId;
    }

    private int dropdownId;

    public int getDropdownId() {
        return dropdownId;
    }
}

package org.genericHpBuilder.Model;

/**
 * Possible types of text files
 */
public enum TextFileType implements EnumValue {

    HTML("html"), CSS("css");

    private static TextFileType[] allValues = {
            HTML, CSS
    };

    private static final String[] valueNames = {
            "html", "css"
    };


    private String type;

    TextFileType(String type) {
        this.type = type;
    }

    @Override
    public String asString() {
        return type;
    }

    @Override
    public EnumValue[] getAllValues() {
        return allValues;
    }

    public static TextFileType getFromString(String textFileType) {
        assert textFileType != null;
        assert !"".equals(textFileType);

        for (TextFileType gender : TextFileType.values()) {
            if (gender.asString().equals(textFileType)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("invalid text file type: " + textFileType);
    }
}

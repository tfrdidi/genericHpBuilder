package org.genericHpBuilder.Model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one text file of a certain type with several textFileVersions
 */
@Entity
public class TextFile extends DatastoreEntity {

    public static final int UNKNOWN = -1;
    public static String PROPERTY_TEXT_FILE_NAME = "textFileName";
    private static final int FIRST_VERSION = 0;

    private TextFileType textFileType;
    @Index
    private String textFileName;
    private List<TextFileVersion> textFileVersions;
    private int currentTextFileVersionNumber;


    public TextFile() {
    }

    public TextFile(String textFileName, TextFileType textFileType) {
        init(textFileName, textFileType);
    }


    private void init(String textFileName, TextFileType textFileType) {
        this.textFileType = textFileType;
        this.textFileName = textFileName;
        textFileVersions = new ArrayList<>();
        currentTextFileVersionNumber = UNKNOWN;
        incWriteCount();
    }

    public TextFileType getTextFileType() {
        return textFileType;
    }

    public void setTextFileType(TextFileType textFileType) {
        this.textFileType = textFileType;
        incWriteCount();
    }

    public String getTextFileName() {
        return textFileName;
    }

    /**
     * Adds a new version of the text file.
     * If the current version is not the latest, all newer versions are removed!
     * Afterwards the current version is the newTextFileVersion
     */
    public void addNewTextFileVersion(TextFileVersion newTextFileVersion) {

        assert newTextFileVersion != null;

        if(currentTextFileVersionNumber == UNKNOWN) {
            currentTextFileVersionNumber = FIRST_VERSION;
        }
        else {
            if (currentTextFileVersionNumber != getLatestTextFileVersion()) {
                textFileVersions.subList(currentTextFileVersionNumber, getLatestTextFileVersion()).clear();
            }
            currentTextFileVersionNumber++;
        }
        textFileVersions.add(newTextFileVersion);
        incWriteCount();
    }

    public void clearAllTextFileVersions() {
        init(textFileName, textFileType);
    }

    public List<TextFileVersion> getTextFileVersions() {
        return textFileVersions;
    }

    public int getCurrentTextFileVersionNumber() {
        return currentTextFileVersionNumber;
    }

    public TextFileVersion getCurrentTextFileversion() {
        return textFileVersions.get(getCurrentTextFileVersionNumber());
    }

    public void setCurrentTextFileVersionNumber(int currentTextFileVersionNumber) {
        assert currentTextFileVersionNumber >= 0;
        assert currentTextFileVersionNumber <= getLatestTextFileVersion();
        this.currentTextFileVersionNumber = currentTextFileVersionNumber;
        incWriteCount();
    }

    public int getLatestTextFileVersion() {
        return textFileVersions.size() - 1;
    }
}

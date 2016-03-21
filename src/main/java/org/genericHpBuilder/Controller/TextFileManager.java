package org.genericHpBuilder.Controller;

import org.genericHpBuilder.Model.TextFile;


/**
 * Manages adding, modifying and deleting of text files
 */
public class TextFileManager extends ObjectManager{

    /**
     * Adds or modifies a text file
     */
    public void updateTextFile(TextFile newTextFile) {
        deleteObjects(TextFile.class, "textFileName", newTextFile.getTextFileName());
        writeObject(newTextFile);
    }

    /**
     * Returns a specific text file or null if it does not exist or was deleted.
     */
    public TextFile getTextFile(String textFileName) {
        TextFile result =  readObject(TextFile.class, TextFile.PROPERTY_TEXT_FILE_NAME, textFileName);
        if(result != null && !result.isDeleted()) {
            return result;
        }
        else {
            return null;
        }
    }

    public void deleteTextFile(String textFileName) {
        TextFile textFile = getTextFile(textFileName);
        if (textFile == null)
            throw new IllegalArgumentException("No such text file known.");

        textFile.setDeleted(true);
        updateTextFile(textFile);
    }

}

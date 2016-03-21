package org.genericHpBuilder.Controller;

import org.genericHpBuilder.Model.TextFile;

import java.util.ArrayList;
import java.util.Collection;


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

    /**
     * Returns a collection of all text files either with deleted files or without.
     * If no text file is there an empty collection is returned.
     */
    public Collection<TextFile> getAllTextFiles(boolean includingDeletedTextFiles) {
        Collection<TextFile> result = new ArrayList<>();
        if(includingDeletedTextFiles) {
            readObjects(result, TextFile.class);
        }
        else {
            readObjects(result, TextFile.class, "deleted", false);
        }
        return result;
    }

    /**
     * Marks a text file as deleted. This does not deletes it from the datastore.
     */
    public void deleteTextFile(String textFileName) {
        TextFile textFile = getTextFile(textFileName);
        if (textFile == null)
            throw new IllegalArgumentException("No such text file known.");

        textFile.setDeleted(true);
        updateTextFile(textFile);
    }

}

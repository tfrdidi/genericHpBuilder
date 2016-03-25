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
        return doGetTextFile(textFileName, false);
    }

    public TextFile getTextFile(String textFileName, boolean includingDeletedTextFile) {
        return doGetTextFile(textFileName, includingDeletedTextFile);
    }

    private TextFile doGetTextFile(String textFileName, boolean includingDeletedTextFile) {
        TextFile result =  readObject(TextFile.class, TextFile.PROPERTY_TEXT_FILE_NAME, textFileName);
        if(result != null) {
            if(includingDeletedTextFile || !result.isDeleted()) {
                return result;
            }
        }
        return null;
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
        setTextFileDeleted(textFileName, true);
    }

    /**
     * Marks a text file undeleted that already has been marked as deleted
     */
    public void undeleteTextFile(String textFileName) {
        setTextFileDeleted(textFileName, false);
    }

    /**
     * Worker method to actually change the delete status of a text file
     */
    private void setTextFileDeleted(String textFileName, boolean deleted) {
        TextFile textFile = getTextFile(textFileName, true);
        if (textFile == null)
            throw new IllegalArgumentException("No such text file known.");

        textFile.setDeleted(deleted);
        updateTextFile(textFile);
    }

    /**
     * Either marks a text file as deleted or completely deletes it from the datastore.
     */
    public void deleteTextFile(String textFileName, boolean deletePhysically) {
        if(deletePhysically) {
            deleteObjects(TextFile.class, "textFileName", textFileName);
        }
        else {
            deleteTextFile(textFileName);
        }
    }

    /**
     * Deletes all text files, either physically or marks them only as deleted
     */
    public void deleteAllTextFiles(boolean deletePhysically) {
        if(deletePhysically) {
            deleteAllObjects(TextFile.class);
        }
        else {
            Collection<TextFile> allUndeletedTextFiles = getAllTextFiles(false);
            for (TextFile textFile: allUndeletedTextFiles) {
                textFile.setDeleted(true);
            }
            updateObjects(allUndeletedTextFiles);
        }
    }
}

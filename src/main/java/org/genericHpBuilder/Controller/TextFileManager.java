package org.genericHpBuilder.Controller;

import org.genericHpBuilder.Model.TextFile;


/**
 * Manages adding, modifying and deleting of text files
 */
public class TextFileManager extends ObjectManager{

    public void addTextFile(TextFile newTextFile) {
        writeObject(newTextFile);
    }

    public TextFile getTextFile(String textFileName) {
        return readObject(TextFile.class, TextFile.PROPERTY_TEXT_FILE_NAME, textFileName);
    }

}

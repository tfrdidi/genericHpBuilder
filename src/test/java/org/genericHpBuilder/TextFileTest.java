package org.genericHpBuilder;

import org.genericHpBuilder.Model.Author;
import org.genericHpBuilder.Model.TextFile;
import org.genericHpBuilder.Model.TextFileType;
import org.genericHpBuilder.Model.TextFileVersion;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for TextFile
 */
public class TextFileTest {

    private TextFile testTextFile;
    private TextFileVersion versionA;
    private TextFileVersion versionB;
    private Author authorA;
    private Date dateA;
    private Author authorB;
    private Date dateB;

    @Before
    public void setUp() {
        testTextFile = new TextFile("test", TextFileType.HTML);
        versionA = new TextFileVersion();
        authorA = new Author("A");
        versionA.setAuthor(authorA);
        versionA.setDate(dateA);

        versionB = new TextFileVersion();
        authorB = new Author("B");
        versionB.setAuthor(authorB);
        versionB.setDate(dateB);
    }

    @Test
    public void testAddVersions() {
        String content = "testcontent";
        versionA.setContent(content);
        assertEquals(TextFile.UNKNOWN, testTextFile.getCurrentTextFileVersionNumber());
        testTextFile.addNewTextFileVersion(versionA);
        assertEquals(0, testTextFile.getLatestTextFileVersion());
        assertEquals(0, testTextFile.getCurrentTextFileVersionNumber());

        testTextFile.addNewTextFileVersion(versionB);
        assertEquals(1, testTextFile.getLatestTextFileVersion());
        assertEquals(1, testTextFile.getCurrentTextFileVersionNumber());

        testTextFile.addNewTextFileVersion(versionA);
        assertEquals(2, testTextFile.getLatestTextFileVersion());
        assertEquals(2, testTextFile.getCurrentTextFileVersionNumber());

        testTextFile.setCurrentTextFileVersionNumber(0);
        assertEquals(2, testTextFile.getLatestTextFileVersion());
        assertEquals(0, testTextFile.getCurrentTextFileVersionNumber());
        assertTrue(authorA.getName().equals(testTextFile.getCurrentTextFileversion().getAuthor().getName()));

        testTextFile.addNewTextFileVersion(versionA);
        assertEquals(1, testTextFile.getLatestTextFileVersion());
        assertEquals(1, testTextFile.getCurrentTextFileVersionNumber());
        assertTrue(authorA.getName().equals(testTextFile.getCurrentTextFileversion().getAuthor().getName()));
    }

    @Test
    public void testDeleteAll() {
        testTextFile.addNewTextFileVersion(versionA);
        testTextFile.addNewTextFileVersion(versionB);
        testTextFile.clearAllTextFileVersions();
        assertEquals(TextFile.UNKNOWN, testTextFile.getLatestTextFileVersion());
        assertEquals(TextFile.UNKNOWN, testTextFile.getCurrentTextFileVersionNumber());
    }
}

package org.genericHpBuilder;

import org.genericHpBuilder.Controller.TextFileManager;
import org.genericHpBuilder.Model.TextFile;
import org.genericHpBuilder.Model.TextFileType;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static org.junit.Assert.*;

/**
 * Test class for TextFileManager
 */
public class TextFileManagerTest {

    private TextFileManager textFileManager;
    private TextFile textFile;
    private String textFileName1 = "t";
    private String textFileName2 = "n";

    @Rule
    public TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
    @Before
    public void before() {
        textFileManager = new TextFileManager();
        textFile = new TextFile(textFileName1, TextFileType.HTML);
    }

    @After
    public void after() {
        textFileManager.deleteAllTextFiles(true);
    }

    @Test
    public void testAddAndGet() {
        textFileManager.updateTextFile(textFile);
        TextFile result = textFileManager.getTextFile(textFileName1);

        assertNotNull(result);
        assertTrue(textFileName1.equals(result.getTextFileName()));
        assertEquals(TextFileType.HTML, result.getTextFileType());
    }

    @Test
    public void testDeleted() {
        textFileManager.updateTextFile(textFile);
        TextFile testTextFile = textFileManager.getTextFile(textFile.getTextFileName());
        assertNotNull(testTextFile);

        textFileManager.deleteTextFile(textFile.getTextFileName());
        testTextFile = textFileManager.getTextFile(textFile.getTextFileName());
        assertNull(testTextFile);

        textFile = new TextFile(textFileName1, TextFileType.HTML);
        textFileManager.updateTextFile(textFile);
        testTextFile = textFileManager.getTextFile(textFile.getTextFileName());
        assertNotNull(testTextFile);
    }

    @Test
    public void testTextFileWorkflow() {
        assertEquals(0, textFileManager.getAllTextFiles(true).size());
        assertEquals(0, textFileManager.getAllTextFiles(false).size());

        textFileManager.updateTextFile(textFile);
        assertEquals(1, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());

        textFileManager.deleteTextFile(textFile.getTextFileName());
        assertEquals(1, textFileManager.getAllTextFiles(true).size());
        assertEquals(0, textFileManager.getAllTextFiles(false).size());

        TextFile secondTextFile = new TextFile(textFileName2, TextFileType.HTML);
        textFileManager.updateTextFile(secondTextFile);
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());

        textFileManager.deleteAllTextFiles(false);
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(0, textFileManager.getAllTextFiles(false).size());

        textFileManager.undeleteTextFile(textFileName2);
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());

        textFileManager.deleteTextFile(textFileName1, true);
        assertEquals(1, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUndeleteUnexistingTextfile() {
        textFileManager.undeleteTextFile(textFileName1);
    }

    @Test
    public void testGetUnexistingTextfile() {
        assertNull(textFileManager.getTextFile(textFileName1));
    }
}

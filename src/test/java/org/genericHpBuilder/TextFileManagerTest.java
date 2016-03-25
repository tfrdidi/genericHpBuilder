package org.genericHpBuilder;

import org.genericHpBuilder.Controller.TextFileManager;
import org.genericHpBuilder.Model.TextFile;
import org.genericHpBuilder.Model.TextFileType;
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

    @Rule
    public TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
    @Before
    public void before() {
        textFileManager = new TextFileManager();
        textFile = new TextFile("t", TextFileType.HTML);
    }

    @Test
    public void testAddAndGet() {
        textFileManager.updateTextFile(textFile);
        TextFile result = textFileManager.getTextFile("t");

        assertNotNull(result);
        assertTrue("t".equals(result.getTextFileName()));
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

        textFile = new TextFile("t", TextFileType.HTML);
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

        TextFile secondTextFile = new TextFile("n", TextFileType.HTML);
        textFileManager.updateTextFile(secondTextFile);
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());

        textFileManager.deleteAllTextFiles(false);
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(0, textFileManager.getAllTextFiles(false).size());

        textFileManager.undeleteTextFile("n");
        assertEquals(2, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());

        textFileManager.deleteTextFile("t", true);
        assertEquals(1, textFileManager.getAllTextFiles(true).size());
        assertEquals(1, textFileManager.getAllTextFiles(false).size());
    }
}

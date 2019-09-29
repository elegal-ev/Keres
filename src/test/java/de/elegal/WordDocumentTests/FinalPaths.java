package de.elegal.WordDocumentTests;

// TODO: Comment that this is cool to keep the hierarchy low
public abstract class FinalPaths {
    public static final String TEST_PATH = System.getProperty("user.dir") + "/src/test/resources/docx/";
    public static final String EMPTY_FILE = TEST_PATH + "Empty.docx";
    public static final String TESTING_FILE = TEST_PATH + "Testing.docx";
    public static final String XLSX_FILE = TEST_PATH + "ActuallyNotADocX.xlsx";
    public static final String TEMP_FILE = TEST_PATH + "temp.docx";
}

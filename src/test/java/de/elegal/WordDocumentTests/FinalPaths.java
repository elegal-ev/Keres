package de.elegal.WordDocumentTests;

// TODO: Comment that this is cool to keep the hierarchy low
abstract class FinalPaths {
    static final String TEST_PATH = System.getProperty("user.dir") + "/src/test/resources/docx/";
    static final String EMPTY_FILE = TEST_PATH + "Empty.docx";
    static final String TESTING_FILE = TEST_PATH + "Testing.docx";
    static final String XLSX_FILE = TEST_PATH + "ActuallyNotADocX.xlsx";
}

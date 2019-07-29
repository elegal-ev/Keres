package de.elegal;

import java.io.*;

import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Test {
  public static void main(String[] args) throws Exception{
    XWPFDocument document = DocumentUtils.openFile("example.docx");
    /*
    for (XWPFParagraph p : document.getParagraphs()) {
      System.out.println("Paragraph:" + p);
      for (XWPFRun r : p.getRuns()) {
        System.out.println("Run: " + r);
      }
    }
     */
    DocumentUtils.replaceAllTags(document, "Test", "lol");
    DocumentUtils.writeFile(document, "example2.docx");
    System.out.println("Done");
  }
}

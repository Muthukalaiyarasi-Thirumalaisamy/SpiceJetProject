package com.qa.ReadPdf;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ReadPDF {

    @Test
    public void TestPdf() throws IOException {
        // URL of the PDF file
        URL url = new URL("https://www.shcollege.ac.in/wp-content/uploads/2019/10/sample-1.pdf");

        // Open the URL stream directly
        try (InputStream is = url.openStream();
             PDDocument document = PDDocument.load(is)) {

            // Extract text from the PDF
            String pdfContent = new PDFTextStripper().getText(document);
            System.out.println("PDF Content:");
            System.out.println(pdfContent);
            Assert.assertTrue(true, "Sample PDF file ");
        }
    }
}

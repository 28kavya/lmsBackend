package com.learnhub.service;

import com.learnhub.entity.Certificate;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PDFGeneratorService {

    public byte[] generateCertificate(Certificate certificate) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, output);

            document.open();

            Font title =
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD,24);

            document.add(new Paragraph("CERTIFICATE OF COMPLETION", title));

            document.add(new Paragraph(" "));

            document.add(new Paragraph(
                    "This is to certify that"));

            document.add(new Paragraph(
                    certificate.getStudent().getName()));

            document.add(new Paragraph(
                    "has successfully completed"));

            document.add(new Paragraph(
                    certificate.getCourse().getTitle()));

            document.add(new Paragraph(""));

            document.add(new Paragraph(
                    "Issued Date : " +
                            certificate.getIssuedDate()));

            document.add(new Paragraph(
                    "Certificate No : " +
                            certificate.getCertificateNumber()));

            document.close();

        }
        catch(Exception e){

            throw new RuntimeException(e);

        }

        return output.toByteArray();

    }

}

package com.info5059.exercises.pdfexample;

import com.info5059.exercises.employee.Employee;
import com.info5059.exercises.employee.EmployeeRepository;
import com.info5059.exercises.expense.Expense;
import com.info5059.exercises.expense.ExpenseRepository;
import com.info5059.exercises.pdfexample.Generator;
import com.info5059.exercises.report.Report;
import com.info5059.exercises.report.ReportDAO;
import com.info5059.exercises.report.ReportItem;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ReportPDFGenerator - a class for creating dynamic expense report output in PDF
 * format using the iText 7 library
 *
 * @author Evan
 */
public abstract class ReportPDFGenerator extends AbstractPdfView {
    public static ByteArrayInputStream generateReport(String repid,
                                                      ReportDAO repDAO,
                                                      EmployeeRepository employeeRepository,
                                                      ExpenseRepository expenseRepository) throws IOException {
        URL imageUrl = Generator.class.getResource("/static/assets/Expenses.png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        // Initialize PDF document to be written to a stream not a file
        PdfDocument pdf = new PdfDocument(writer);
        // Document is the main object
        Document document = new Document(pdf);
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        // add the image to the document
        Image img = new Image(ImageDataFactory.create(imageUrl))
                .scaleAbsolute(120, 40)
                .setFixedPosition(80, 710);
        document.add(img);
        // now let's add a big heading
        document.add(new Paragraph("\n\n"));
        //money unitu
        Locale locale = new Locale("en", "US");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        try {
            Report report = repDAO.findOne(Long.parseLong(repid));
            document.add(new Paragraph(String.format("Expenses"))
                    .setFont(font)
                    .setFontSize(24)
                    .setMarginRight(75)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold());
            document.add(new Paragraph("Report#:" + repid)
                    .setFont(font)
                    .setFontSize(16)
                    .setBold()
                    .setMarginRight(90)
                    .setMarginTop(-10)
                    .setTextAlignment(TextAlignment.RIGHT));
            document.add(new Paragraph("\n\n"));
            Optional<Employee> opt = employeeRepository.findById(report.getEmployeeid());

            if (opt.isPresent()) {
                Employee employee = opt.get();
                Table employeeTable = new Table(1);
                employeeTable.setWidth(new UnitValue(UnitValue.PERCENT, 30));
                employeeTable.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                Cell cell = new Cell().add(new Paragraph(employee.getFirstname())
                        .setBorder(Border.NO_BORDER)
                        .setFont(font)
                        .setFontSize(12)
                        .setBold())
                        .setTextAlignment(TextAlignment.CENTER);
                employeeTable.addCell(cell);
                cell = new Cell().add(new Paragraph(employee.getLastname())
                        .setBorder(Border.NO_BORDER)
                        .setFont(font)
                        .setFontSize(12)
                        .setBold())
                        .setTextAlignment(TextAlignment.CENTER);
                employeeTable.addCell(cell);
                cell = new Cell().add(new Paragraph(employee.getEmail())
                        .setBorder(Border.NO_BORDER)
                        .setFont(font)
                        .setFontSize(12)
                        .setBold())
                        .setTextAlignment(TextAlignment.CENTER);
                employeeTable.addCell(cell);
                document.add(new Paragraph("Employee:"));
                document.add(employeeTable);
                document.add(new Paragraph("\n\n"));
            }


            //start create table in the pdf
            Table expenseTable = new Table(4);
            expenseTable.setWidth(new UnitValue(UnitValue.PERCENT, 100));
            // row info
            Cell cell = new Cell().add(new Paragraph("Id")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold())
                    .setTextAlignment(TextAlignment.CENTER);
            expenseTable.addCell(cell);
            cell = new Cell().add(new Paragraph("Date Incurred")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold())
                    .setTextAlignment(TextAlignment.CENTER);
            expenseTable.addCell(cell);
            cell = new Cell().add(new Paragraph("Description")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold())
                    .setTextAlignment(TextAlignment.CENTER);
            expenseTable.addCell(cell);
            cell = new Cell().add(new Paragraph("Amount")
                    .setFont(font)
                    .setFontSize(12)
                    .setBold())
                    .setTextAlignment(TextAlignment.CENTER);
            expenseTable.addCell(cell);
            // report total
            BigDecimal tot = new BigDecimal(0.0);
            // dump out the line items
            for (ReportItem line : report.getItems()) {
                Optional<Expense> optx = expenseRepository.findById(line.getExpenseid());
                if (optx.isPresent()) {
                    Expense expense = optx.get();
                    //display row info
                    cell = new Cell().add(new Paragraph(expense.getId().toString()))
//                            .setBorder(Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.CENTER);
                    expenseTable.addCell(cell);
                    cell = new Cell().add(new Paragraph(expense.getDateincurred()))
//                            .setBorder(Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.CENTER);
                    expenseTable.addCell(cell);
                    cell = new Cell().add(new Paragraph(expense.getDescription()))
//                            .setBorder(Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.CENTER);
                    expenseTable.addCell(cell);
                    cell = new Cell().add(new Paragraph(formatter.format(expense.getAmount())))
//                            .setBorder(Border.NO_BORDER)
                            .setTextAlignment(TextAlignment.RIGHT);
                    expenseTable.addCell(cell);
                    tot = tot.add(expense.getAmount(), new MathContext(8, RoundingMode.UP));
                }
            }

            cell = new Cell(1, 3).add(new Paragraph("Report Total:"))
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT.RIGHT);
            expenseTable.addCell(cell);
            cell = new Cell().add(new Paragraph(formatter.format(tot)))
                    .setTextAlignment(TextAlignment.RIGHT.RIGHT)
                    .setBackgroundColor(ColorConstants.YELLOW);
            expenseTable.addCell(cell);
            document.add(expenseTable);
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph(String.valueOf(new Date()))
                    .setTextAlignment(TextAlignment.CENTER));
            document.close();
        } catch (Exception ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }
}

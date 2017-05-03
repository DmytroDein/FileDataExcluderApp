package ua.training.lab.savers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.training.lab.entity.Subscriber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

public class XSLSubscriberSaver implements SubscriberSaver {
    @Override
    public void save(Collection<Subscriber> uniqueSubscribers,
                     Collection<Subscriber> unSubscribedInSubscribers,
                     File outputFile) {
        try(XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet filteredSheet = workbook.createSheet("Filtered");
            XSSFSheet doubleSheet = workbook.createSheet("Double");

            fillTable(uniqueSubscribers, filteredSheet);
            fillTable(unSubscribedInSubscribers, doubleSheet);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable(Collection<Subscriber> subscribers, XSSFSheet sheet) {
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        String[] headers = new String[]{"city", "email", "employer", "firstName", "lastName"};
        Cell cell;
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
        for (Subscriber subscriber : subscribers) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(subscriber.getCity());
            cell = row.createCell(1);
            cell.setCellValue(subscriber.getEmail());
            cell = row.createCell(2);
            cell.setCellValue(subscriber.getEmployer());
            cell = row.createCell(3);
            cell.setCellValue(subscriber.getFirstName());
            cell = row.createCell(4);
            cell.setCellValue(subscriber.getLastName());
        }
    }
}

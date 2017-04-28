package ua.training.lab.parsers;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.training.lab.entity.Subscriber;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class XLSSubscriberParser implements SubscriberParser {
    private InputStream inputStream;

    public XLSSubscriberParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public Set<Subscriber> getSubscribers() {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet datatypeSheet = workbook.getSheetAt(0);

        Set<Subscriber> subscribers = new HashSet<>();
        for (Row currentRow : datatypeSheet) {
            Subscriber subscriber = new Subscriber();
            subscriber.setCity(currentRow.getCell(0).getStringCellValue());
            subscriber.setEmail(currentRow.getCell(1).getStringCellValue());
            subscriber.setEmployer(currentRow.getCell(2).getStringCellValue());
            subscriber.setFirstName(currentRow.getCell(3).getStringCellValue());
            subscriber.setLastName(currentRow.getCell(4).getStringCellValue());
            subscribers.add(subscriber);
        }
        return subscribers;
    }
}

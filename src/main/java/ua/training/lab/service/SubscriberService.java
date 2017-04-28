package ua.training.lab.service;

import org.apache.commons.io.FilenameUtils;
import ua.training.lab.entity.Subscriber;
import ua.training.lab.parsers.CSVSubscriberParser;
import ua.training.lab.parsers.SubscriberParser;
import ua.training.lab.parsers.XLSSubscriberParser;
import ua.training.lab.savers.CSVSubscriberSaver;
import ua.training.lab.savers.SubscriberSaver;
import ua.training.lab.savers.XSLSubscriberSaver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.UnknownFormatConversionException;

public class SubscriberService {
    public static final String XLS_EXTENSIONS = "xlsx?";
    public static final String CSV_EXTENSIONS = "csv";

    public void handle(File subscribersInputFile,
                       File unSubscribedInputFile,
                       File outputFile) {
        Set<Subscriber> subscribers = selectSubscriberParser(subscribersInputFile).getSubscribers();
        Set<Subscriber> unSubscribed = selectSubscriberParser(unSubscribedInputFile).getSubscribers();

        Set<Subscriber> unSubscribedInSubscribers = new HashSet<>(subscribers);
        unSubscribedInSubscribers.retainAll(unSubscribed);

        Set<Subscriber> uniqueSubscribers = new HashSet<>(subscribers);
        uniqueSubscribers.removeAll(unSubscribed);

        selectSubscriberSaver(outputFile)
                .save(uniqueSubscribers, unSubscribedInSubscribers, outputFile);
    }

    private SubscriberParser selectSubscriberParser(File inputFile) {
        SubscriberParser chosenParser;
        if (FilenameUtils.getExtension(inputFile.getAbsolutePath()).matches(XLS_EXTENSIONS)) {
            chosenParser = new XLSSubscriberParser(openInputStreamFor(inputFile));
        } else {
            chosenParser = new CSVSubscriberParser(openInputStreamFor(inputFile));
        }
        return chosenParser;
    }

    private InputStream openInputStreamFor(File file) {
        InputStream resultStream = null;
        String fileExtension = FilenameUtils.getExtension(file.getAbsolutePath());
        try {
            if (fileExtension.matches(XLS_EXTENSIONS)) {
                resultStream = new FileInputStream(file);
            } else if (fileExtension.matches(CSV_EXTENSIONS)) {
                resultStream = new FileInputStream(file);
            } else {
                throw new UnknownFormatConversionException("Incorrect file type chosen" + file.toString());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return resultStream;
    }

    private SubscriberSaver selectSubscriberSaver(File outputFile) {
        SubscriberSaver saver;
        if (FilenameUtils.getExtension(outputFile.getName()).matches("xlsx?")) {
            saver = new XSLSubscriberSaver();
        } else {
            saver = new CSVSubscriberSaver();
        }
        return saver;
    }
}
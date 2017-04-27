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
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class SubscriberService {
    private SubscriberParser subscribersParser;
    private SubscriberParser unSubscribedParser;
    private SubscriberSaver subscriberSaver;

    public void handle(InputStream subscribersInputStream,
                 InputStream unSubscribedInputStream,
                 File outputFile) {
//        subscribersParser = new XLSSubscriberParser(subscribersInputStream);
//        unSubscribedParser = new CSVSubscriberParser(unSubscribedInputStream);
        subscribersParser = new CSVSubscriberParser(subscribersInputStream);
        unSubscribedParser = new XLSSubscriberParser(unSubscribedInputStream);

        Set<Subscriber> subscribers = subscribersParser.getSubscribers();
        Set<Subscriber> unSubscribed = unSubscribedParser.getSubscribers();

        Set<Subscriber> unSubscribedInSubscribers = new HashSet<>(subscribers);
        unSubscribedInSubscribers.retainAll(unSubscribed);

        Set<Subscriber> uniqueSubscribers = new HashSet<>(subscribers);
        uniqueSubscribers.removeAll(unSubscribed);

        if (FilenameUtils.getExtension(outputFile.getName()).matches("xlsx?")) {
            subscriberSaver = new XSLSubscriberSaver();
        } else {
            subscriberSaver = new CSVSubscriberSaver(outputFile);
        }
        subscriberSaver.save(uniqueSubscribers, unSubscribedInSubscribers, outputFile);
    }
}

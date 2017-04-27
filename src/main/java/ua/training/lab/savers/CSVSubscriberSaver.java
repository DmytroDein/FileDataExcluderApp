package ua.training.lab.savers;

import ua.training.lab.entity.Subscriber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;


public class CSVSubscriberSaver implements SubscriberSaver{

    private static final String DELIMITER = ",";
    private static final String FILE_HEADER = "Email Address,First Name,Last Name\n";
    private BufferedWriter bufferedWriter;

    public CSVSubscriberSaver(File outputFile) {
        try {
            this.bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Collection<Subscriber> uniqueSubscribers, Collection<Subscriber> unSubscribedInSubscribers, File outputFile) {
        List<String> uniqueSubscribersList = prepareStrings(uniqueSubscribers);
        writeDataToFile(uniqueSubscribersList);
    }

    private void writeDataToFile(List<String> resultArr) {
        try {
            for (String str : resultArr) {
                bufferedWriter.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> prepareStrings(Collection<Subscriber> uniqueSubscribers) {
        List<String> result = new ArrayList<>();
        result.add(FILE_HEADER);
        result.addAll(uniqueSubscribers.stream()
                .map(e -> new StringJoiner(",", "", "\n")
                        .add(e.getEmail())
                        .add(e.getFirstName())
                        .add(e.getLastName())
                        .toString())
                .collect(Collectors.toList()));
        return result;
    }

}

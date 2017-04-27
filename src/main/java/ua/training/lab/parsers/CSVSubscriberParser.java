package ua.training.lab.parsers;




import ua.training.lab.entity.Subscriber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CSVSubscriberParser implements SubscriberParser{

    public static final String SPLITT_SYMBOL = ",";
    private BufferedReader csvBuffReader;
    private String line;

    public CSVSubscriberParser(InputStream csvStream) {
        csvBuffReader = new BufferedReader(new InputStreamReader(csvStream));
    }


    @Override
    public Set<Subscriber> getSubscribers() {
        final Set<Subscriber> parsedSubscribers = new HashSet<>();
        int counter = 0;
        try {
            while((line = csvBuffReader.readLine())!= null){
                if (counter > 0) {
                    String[] dataValues = line.split(SPLITT_SYMBOL);
//                    System.out.println(Arrays.toString(dataValues));
                    Subscriber subscriber = parseDataSubscriber(dataValues);
                    parsedSubscribers.add(subscriber);
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvBuffReader != null) {
                try {
                    csvBuffReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return parsedSubscribers;
    }

    private Subscriber parseDataSubscriber(String[] dataValues) {
        Subscriber subscriber = new Subscriber();
        int valuesSize = dataValues.length;
        subscriber.setEmail(dataValues[0]);
        subscriber.setFirstName(valuesSize > 1? dataValues[1] : "");
        subscriber.setLastName((valuesSize > 2)? dataValues[2] : "");
        return subscriber;
    }
}

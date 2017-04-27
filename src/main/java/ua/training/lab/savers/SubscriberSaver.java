package ua.training.lab.savers;


import ua.training.lab.entity.Subscriber;

import java.io.File;
import java.util.Collection;

public interface SubscriberSaver {
    void save(Collection<Subscriber> uniqueSubscribers,
              Collection<Subscriber> unSubscribedInSubscribers,
              File outputFile);
}

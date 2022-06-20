package services;

import model.GameConfiguration;

public interface IObserver {
    void handleUpdate(Object config);
}

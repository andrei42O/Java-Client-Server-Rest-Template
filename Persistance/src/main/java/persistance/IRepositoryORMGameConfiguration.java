package persistance;

import model.GameConfiguration;

public interface IRepositoryORMGameConfiguration extends IRepository<Long, GameConfiguration> {
    GameConfiguration getRandomConfiguration();
}

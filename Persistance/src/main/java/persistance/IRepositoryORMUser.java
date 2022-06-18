package persistance;

import model.User;

public interface IRepositoryORMUser extends IRepository<Long, User>{
    User findByUsername(String username);
}

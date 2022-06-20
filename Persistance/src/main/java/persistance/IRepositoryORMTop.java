package persistance;

import model.Result;
import model.User;

import java.util.Collection;

public interface IRepositoryORMTop extends IRepository<Long, Result> {
    Collection<Result> findByUser(User user);
}

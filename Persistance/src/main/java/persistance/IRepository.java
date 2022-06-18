package persistance;

import model.Entity;

import java.util.Collection;

public interface IRepository<Tid, T extends Entity<Tid>> {
    /**
     * The function saves an Object
     * @param elem - T
     * @return elem -> Object saved successfully
     *         null -> Object couldn't be saved
     */
    T add(T elem);
    /**
     * The function deletes an Object
     * @param elem - T
     * @return elem -> Object deleted successfully
     *         null -> Object couldn't be deleted
     */
    T delete(T elem);
    /**
     * The function updates an Object
     * @param elem - T
     * @param id - Tid (Object unique identifier)
     * @return elem -> Object updated successfully
     *         null -> Object couldn't be saved
     */
    T update(T elem, Tid id);

    /**
     * The function finds the entity based on it's ID
     * @param id - Tid
     * @return T
     */
    T findById(Tid id);

    /**
     * The functions provides all elements
     * @return Iterable<T>
     */
    Iterable<T> findAll();
    /**
     * The functions provides all elements
     * @return Collection<T>
     */
    Collection<T> getAll();
}

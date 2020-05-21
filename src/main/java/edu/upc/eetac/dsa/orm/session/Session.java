package edu.upc.eetac.dsa.orm.session;

import edu.upc.eetac.dsa.exceptions.EmptyUserListException;
import edu.upc.eetac.dsa.exceptions.UserAlreadyExistsException;
import edu.upc.eetac.dsa.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;

public interface Session<E> {

    //OPERACIONES CRUD
    void save(Object entity); //Añade un nuevo objeto en la tabla correspondiente
    List<Object> findAll(Class theClass); //Lee una tabla y devuelve todos los objetos
    Object findByID(Class theClass, String id); //Lee una tabla y devuelve el objecto con el ID especificado
    Object findByNameOrMail(Class theClass, String name);
    void update(Object object); //Actualiza un objeto de la tabla
    void delete(Object object); //Elimina un objeto de la tabla

    void close();
}


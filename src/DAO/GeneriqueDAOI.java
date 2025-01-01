package DAO;

import java.io.IOException;
import java.util.List;

import Model.Employee;

public interface GeneriqueDAOI<T> {
    public List<T> afficher();
    public boolean ajouter(T t);
    public void modifier(T t,int id);
    public void supprimer(int id);
    public Employee findById(int EmployeeId);
	void importData(String filePath) throws IOException;
}
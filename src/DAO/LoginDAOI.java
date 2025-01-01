package DAO;

import Model.Employee;

public interface LoginDAOI {
    public int Login(String username, String password);
    public Employee FindById(int id);
}
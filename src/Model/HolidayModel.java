package Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import DAO.HolidayDAOImpl;
import View.HolidayView;

public class HolidayModel {
    private static HolidayDAOImpl dao;
        public HolidayModel(HolidayDAOImpl dao) {
            this.dao = dao;
        }
        public List<Employee> afficherEmployee() {
            return dao.afficherEmployee();
        }
        public List<Holiday> afficher() {
            return dao.afficher();
        }
        private boolean isValidDate(String date) {
            try {
                LocalDate.parse(date);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        public void ajouterHoliday(Holiday holiday, Employee employee) {
            if (!isValidDate(holiday.getStart()) || !isValidDate(holiday.getEnd())) {
                HolidayView.fail("Les dates doivent être des chiffres au format YYYY-MM-DD.");
                return;
            }        
            int days = calculateHolidayTime(holiday.getStart(), holiday.getEnd());
            if (startCheck(holiday.getStart())) {
                HolidayView.fail("La date de début doit venir avant aujourd'hui.");
                return;
            }
            if (days <= 0) {
                HolidayView.fail("La date de fin doit venir après la date de début.");
                return;
            }
            List<Holiday> employeeHolidays = dao.afficher().stream()
                .filter(h -> h.getIdEmployee() == employee.getId())
                .sorted(Comparator.comparing(Holiday::getStart))
                .collect(Collectors.toList());
    
            for (int i = 0; i < employeeHolidays.size(); i++) {
                Holiday currentHoliday = employeeHolidays.get(i);
                LocalDate currentStart = LocalDate.parse(currentHoliday.getStart());
                LocalDate currentEnd = LocalDate.parse(currentHoliday.getEnd());
                LocalDate newStart = LocalDate.parse(holiday.getStart());
                LocalDate newEnd = LocalDate.parse(holiday.getEnd());
    
                if ((newStart.isAfter(currentEnd) || newStart.isEqual(currentEnd)) &&
                    (i + 1 == employeeHolidays.size() || newEnd.isBefore(LocalDate.parse(employeeHolidays.get(i + 1).getStart())))) {
                    continue;
                } else {
                    HolidayView.fail("L'employé a déjà un congé pendant cette période.");
                    return;
                }
            }
            if (employee.getHolidayBalance() >= days) {
                employee.setHolidayBalance(employee.getHolidayBalance() - days);
                dao.ajouter(holiday);
                dao.modifierEmployeeBalance(employee, employee.getId());
            } else {
                HolidayView.fail("Le nombre de jours de congés disponibles est insuffisant.");
            }
        }
    
        public boolean startCheck(String startDateString) {
            LocalDate startDate = LocalDate.parse(startDateString);
            return startDate.isBefore(LocalDate.now());
        }
    
        public int calculateHolidayTime(String startDateString, String endDateString) {
            LocalDate startDate = LocalDate.parse(startDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return (int) ChronoUnit.DAYS.between(startDate, endDate);
        }
    
        public Employee FindById(int EmployeeId) {
            return dao.findById(EmployeeId);
        }
        public void ModifierHoliday(Holiday updatedHoliday, Holiday oldHoliday) {
            if (!isValidDate(updatedHoliday.getStart()) || !isValidDate(updatedHoliday.getEnd())) {
                HolidayView.fail("Les dates doivent être des chiffres au format YYYY-MM-DD.");
                return;
            }  
            int newDays = calculateHolidayTime(updatedHoliday.getStart(), updatedHoliday.getEnd());
            int oldDays = calculateHolidayTime(oldHoliday.getStart(), oldHoliday.getEnd());
            if (startCheck(updatedHoliday.getStart())) {
                HolidayView.fail("La date de début doit venir apres aujourd'hui.");
                return;
            }
            if (newDays <= 0) {
                HolidayView.fail("La date de fin doit venir après la date de début.");
                return;
            }
            Employee employee = FindById(updatedHoliday.getIdEmployee());
            if (employee.getHolidayBalance() + oldDays >= newDays) {
                employee.setHolidayBalance(employee.getHolidayBalance() + oldDays - newDays);
                dao.modifierEmployeeBalance(employee, employee.getId());
                dao.modifier(updatedHoliday, updatedHoliday.getId());
            } else {
                HolidayView.fail("Le nombre de jours de congés disponibles est insuffisant.");
                return;
            }
        }
        
        public void modifierEmployeeBalanceRecover(int days,int EmployeeId) {
            Employee employee = this.FindById(EmployeeId);
            employee.setHolidayBalance(employee.getHolidayBalance() + days);
            dao.modifierEmployeeBalance(employee, EmployeeId);
        }
        public Holiday FindHolidayById(int holidayId) {
            return dao.FindHolidayById(holidayId);
        }
        public void supprimerHoliday(Holiday oldHoliday) {
            int holidayId = oldHoliday.getId();
            int employeeId = oldHoliday.getIdEmployee();
            int oldDays = calculateHolidayTime(oldHoliday.getStart(), oldHoliday.getEnd());
            Employee oldEmployee = FindById(employeeId);
            oldEmployee.setHolidayBalance(oldEmployee.getHolidayBalance() + oldDays);
            dao.modifierEmployeeBalance(oldEmployee, oldEmployee.getId());
            dao.supprimer(holidayId);
        }   
        public static List<Holiday> afficherHolidaysLogged(int idEmployee) {
            return dao.findByIdLoggedHoliday(idEmployee);
    }

}
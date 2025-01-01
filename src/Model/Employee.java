package Model;

public class Employee {
    private int id;
    private String nom;
    private String prenom;
    private double salaire;
    private String email;
    private String phone;
    private Role role;
    private Poste poste;
    private int holidayBalance;

    public Employee(int id, String nom, String prenom, double salaire, String email, String phone, Role role, Poste poste, int holidayBalance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.salaire = salaire;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.poste = poste;
        this.holidayBalance = holidayBalance;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public double getSalaire() { return salaire; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Role getRole() { return role; }
    public Poste getPoste() { return poste; }
    public int getHolidayBalance() { return holidayBalance; }

	public void setRole(Role role2) {
		// TODO Auto-generated method stub
		
	}

	public void setPoste(Poste poste2) {
		// TODO Auto-generated method stub
		
	}

	public void setPhone(String phone2) {
		// TODO Auto-generated method stub
		
	}

	public void setSalaire(double salaire2) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}

	public void setPrenom(String prenom2) {
		// TODO Auto-generated method stub
		
	}

	public void setNom(String nom2) {
		// TODO Auto-generated method stub
		
	}

	public void setHolidayBalance(int i) {
		// TODO Auto-generated method stub
		
	}
}

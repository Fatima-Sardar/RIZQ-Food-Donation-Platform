package rizq.model;
public class Donor extends User {
    public Donor(int id,String name,String email,String phone){super(id,name,email,phone,"DONOR");}
    public void showMenu(){System.out.println("Donor: create and manage donation posts.");}
}

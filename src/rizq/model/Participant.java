package rizq.model;
public class Participant extends User {
    private String organizationName;
    public Participant(int id,String name,String email,String phone,String role,String organizationName){
        super(id,name,email,phone,role); this.organizationName=organizationName;
    }
    public String getOrganizationName(){return organizationName;}
    public void showMenu(){System.out.println(role+": browse, accept and track donations.");}
}

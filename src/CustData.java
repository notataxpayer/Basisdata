public class CustData {
    private int CustomerID;
    private String Nama;
    private String Alamat;
    private int NoHp;
    private String Email;
    public int getCustomerID() {
        return CustomerID;
    }
    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
    public String getNama() {
        return Nama;
    }
    public void setNama(String nama) {
        Nama = nama;
    }
    public String getAlamat() {
        return Alamat;
    }
    public void setAlamat(String alamat) {
        Alamat = alamat;
    }
    public int getNoHp() {
        return NoHp;
    }
    public void setNoHp(int noHp) {
        NoHp = noHp;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public CustData(int CustomerID, String Nama, String Alamat, int NoHp, String Email){
        this.CustomerID = CustomerID;
        this.Nama = Nama;
        this.Alamat = Alamat;
        this.NoHp = NoHp;
        this.Email = Email;
        
    }
}

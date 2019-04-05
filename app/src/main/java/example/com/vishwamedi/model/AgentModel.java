package example.com.vishwamedi.model;

public class AgentModel {

    private String Address,District,Email,Landmark,Name,PhoneNo,State;

    public AgentModel() {
    }

    public AgentModel(String address, String district, String email, String landmark, String name, String phoneNo, String state) {
        Address = address;
        District = district;
        Email = email;
        Landmark = landmark;
        Name = name;
        PhoneNo = phoneNo;
        State = state;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}

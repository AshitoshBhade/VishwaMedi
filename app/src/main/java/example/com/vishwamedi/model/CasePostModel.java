package example.com.vishwamedi.model;

public class CasePostModel {

    private String TrackId,CompanyName,PatientName,HospitalName,PhoneNo,TreatingDoc,Address,Landmark,State,DiseaseName,
                    District,Pincode,Vendor,Remark,Verifier,CaseDate,DateOfAdmission,CaseStatus,DateTime,Status;

    public CasePostModel() {
    }

    public String getDiseaseName() {
        return DiseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        DiseaseName = diseaseName;
    }

    public String getCaseStatus() {
        return CaseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        CaseStatus = caseStatus;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public CasePostModel(String caseDate, String dateOfAdmission) {
        CaseDate = caseDate;
        DateOfAdmission = dateOfAdmission;
    }


    public CasePostModel(String diseaseName, String caseStatus, String dateTime, String status) {
        DiseaseName = diseaseName;
        CaseStatus = caseStatus;
        DateTime = dateTime;
        Status = status;
    }

    public CasePostModel(String trackId, String companyName, String patientName, String hospitalName, String phoneNo, String treatingDoc, String address, String landmark, String state, String district, String pincode, String vendor, String remark, String verifier) {
        TrackId = trackId;
        CompanyName = companyName;
        PatientName = patientName;
        HospitalName = hospitalName;
        PhoneNo = phoneNo;
        TreatingDoc = treatingDoc;
        Address = address;
        Landmark = landmark;
        State = state;
        District = district;
        Pincode = pincode;
        Vendor = vendor;
        Remark = remark;
        Verifier = verifier;
    }

    public String getCaseDate() {
        return CaseDate;
    }

    public void setCaseDate(String caseDate) {
        CaseDate = caseDate;
    }

    public String getDateOfAdmission() {
        return DateOfAdmission;
    }

    public void setDateOfAdmission(String dateOfAdmission) {
        DateOfAdmission = dateOfAdmission;
    }

    public String getTrackId() {
        return TrackId;
    }

    public void setTrackId(String trackId) {
        TrackId = trackId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getTreatingDoc() {
        return TreatingDoc;
    }

    public void setTreatingDoc(String treatingDoc) {
        TreatingDoc = treatingDoc;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLandmark() {
        return Landmark;
    }

    public void setLandmark(String landmark) {
        Landmark = landmark;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getVerifier() {
        return Verifier;
    }

    public void setVerifier(String verifier) {
        Verifier = verifier;
    }
}

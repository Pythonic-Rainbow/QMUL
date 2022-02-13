public class CorporateClient extends Client{
    private int companyRegistrationNumber;

    public CorporateClient(String name, int crn) {
        super(name);
        companyRegistrationNumber = crn;
    }

    public int getCompanyRegistrationNumber(){
	return companyRegistrationNumber;
    }
}

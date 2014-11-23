package gaongil.safereturnhome.model;
public class ContactInfo {
      
    private String contactName;
    private String contactNo;
    private boolean selected;
 
    public String getName() {
        return contactName;
    }
 
    public void setName(String contactName) {
        this.contactName = contactName;
    }
 
    public String getNumber() {
        return contactNo;
    }
 
    public void setNumber(String contactNo) {
        this.contactNo = contactNo;
    }
 
    public boolean isSelected() {
        return selected;
    }
 
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
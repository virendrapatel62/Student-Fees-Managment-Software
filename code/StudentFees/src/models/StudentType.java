
package models;

public enum StudentType {
      ADMITTED(1) , ENQUIRY(2);
      private Integer id;
      
    private StudentType(Integer id){
        this.id = id;
    }

    public static StudentType getADMITTED() {
        return ADMITTED;
    }

    public static StudentType getENQUIRY() {
        return ENQUIRY;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public static StudentType getStudentType(Integer id){
        switch(id){
            case 1:
                return ADMITTED;
            case 2:
                return ENQUIRY;
            default: return null;
        }
    }
      
}

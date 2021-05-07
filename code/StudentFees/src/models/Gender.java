package models;

public enum Gender {
    male(1) , female(2) , notSpecified(3);
    
    private Integer id;
    
    Gender(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    
    
    public static Gender getGender(int id){
        switch(id){
            case 1:
                return male;
            case 2:
                return female;
            case 3:
                return notSpecified;
            default:
                return null;
        }
    }
}

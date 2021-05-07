
package models;

public enum Month{
    january(1 , 31) , february(2, 28) , march(3 , 31) ,
    april(4,30) , may(5 , 31) , june(6 , 30) ,
    july(7 , 31) , august(8 , 31) , september(9 ,30) 
    , october(10,31) , november(11,30) , december(12,31);
  
    private final Integer index;
    private final Integer numberOfDays;
    
    Month(Integer index , Integer nod){
        this.index = index;
        this.numberOfDays = nod;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }
    
    public static Month getMonth(Integer index){
        switch(index){
            case 1:
                return january;
            case 2:
                return february;
            case 3:
                return march;
            case 4:
                return april;
            case 5:
                return may;
            case 6:
                return june;
            case 7:
                return july;
            case 8:
                return august;
            case 9:
                return september;
            case 10:
                return october;
            case 11:
                return november;
            case 12:
                return december;
            default:
                return null;
        }
        
    }
    
    
}

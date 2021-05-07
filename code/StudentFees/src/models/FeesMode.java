
package models;

public enum FeesMode {
    instalment(1) , monthly(2);
    private final Integer index;
    
    FeesMode(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public static FeesMode getInstalment() {
        return instalment;
    }

    public static FeesMode getMonthly() {
        return monthly;
    }
    
    public static FeesMode getFeesMode(Integer index){
        switch(index){
            case 1:
                return instalment;
            case 2:
                return monthly;
            default:
                return null;
        }
    }
    
}

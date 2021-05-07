package models;

public class Entry {
    private String key;
    private Object value;

    public Entry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("models.Entry.finalize()" +this);
    }
    
    
    
    
}

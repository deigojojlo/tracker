package deigojojlo.tracker.ignore;

public class IgnoreJson {
    private String text;
    private boolean enabled;

    public String getText(){
        return text;
    }

    public boolean getEnabled(){
        return enabled;
    }

    public void setText(String text){
        this.text = text ;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}

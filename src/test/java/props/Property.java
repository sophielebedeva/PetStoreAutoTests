package props;

public enum Property {
    URI("uri");

    public final String value;
    Property(String value) {
        this.value=Configuration.getConfiguration().getProperty(value);
    }
}

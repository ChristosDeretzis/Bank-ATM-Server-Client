public class Reply {
    private String data;
    private int statusCode;

    public Reply(String data, int statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

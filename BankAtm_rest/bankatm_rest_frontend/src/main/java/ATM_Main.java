import org.json.JSONException;

import java.io.IOException;

public class ATM_Main {

    public static void main(String[] args) {
        AtmInterface atmInterface = new AtmInterface();
        try {
            atmInterface.run_client();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

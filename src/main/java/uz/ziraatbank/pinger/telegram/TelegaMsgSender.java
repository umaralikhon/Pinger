package uz.ziraatbank.pinger.telegram;

import lombok.Data;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Data
public class TelegaMsgSender {
    private String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    private String token = "2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
    private String chatId = "-402458147";
    private String text;
    private URL url = null;
    private URLConnection conn = null;
    private StringBuilder sb = new StringBuilder();
    private InputStream is = null;
    private BufferedReader br = null;
    private String inputLine = "";

    public void setUrl(String text) throws IOException {
        urlString = String.format(urlString, token, chatId, text);
        this.text = text;
        url = new URL(urlString);
        conn = url.openConnection();
        is = new BufferedInputStream(conn.getInputStream());
        br = new BufferedReader(new InputStreamReader(is));

        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }

        String response = sb.toString();
        System.out.println(response);
    }
}

package uz.ziraatbank.pinger.telegram;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Data
@NoArgsConstructor
public class TelegaMsgSender {
    private String token = "2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
    private String[] chatId;
    private String text;
    private URL url;
    private URLConnection conn;
    private StringBuilder sb;
    private InputStream is;
    private BufferedReader br;
    private String inputLine;

    public void setUrl(String text) throws IOException {
        chatId = new String[]{"-402458147", "1878896080", "1642507256"};
        this.text = text;

        for (int i = 0; i < chatId.length; i++) {
            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

            urlString = String.format(urlString, token, chatId[i], text);
            url = new URL(urlString);
            conn = url.openConnection();
            sb = new StringBuilder();
            is = new BufferedInputStream(conn.getInputStream());
            br = new BufferedReader(new InputStreamReader(is));
            inputLine = br.readLine();
            sb.append(inputLine);

        }




//        String response = sb.toString();
//        System.out.println(response);
    }
}

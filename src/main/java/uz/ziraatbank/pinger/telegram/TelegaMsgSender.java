package uz.ziraatbank.pinger.telegram;

import lombok.Data;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.ziraatbank.pinger.telnet.Ping;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Data
public class TelegaMsgSender {
    private String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
    private String token = "2012688061:AAGAKCuvyjWB3J2LG4WS8zpN8FAhI4KzVwU";
        private String chatId = "-402458147";
    private String text;

    public void setUrlFormat() {
        urlString = String.format(urlString, token, chatId, text);
    }

    URL url = null;
    URLConnection conn = null;
    StringBuilder sb = new StringBuilder();
    InputStream is = null;
    BufferedReader br = null;
    String inputLine = "";

    public void setUrl() throws IOException {
        this.text = text;
        url = new URL(urlString);
        conn = url.openConnection();
        is = new BufferedInputStream(conn.getInputStream());
        br = new BufferedReader(new InputStreamReader(is));

        while((inputLine = br.readLine())!= null){
            sb.append(inputLine);
        }

        String response = sb.toString();
        System.out.println(response);
    }
}

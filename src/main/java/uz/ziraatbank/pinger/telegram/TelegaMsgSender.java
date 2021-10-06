package uz.ziraatbank.pinger.telegram;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.ziraatbank.pinger.entity.Users;
import uz.ziraatbank.pinger.service.UsersService;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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
    private List<Users> usersList;

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
    }

//    @Autowired
//    private UsersService usersService;
//
//    public void setUrl(String text) throws IOException {
//        this.text = text;
//        usersList = usersService.getAllUsers();
//        chatId = new String[usersList.size()];
//
//        for(int i = 0; i < usersList.size(); i++) {
//            chatId[i] = usersList.get(i).getChatId();
//        }
//
//        for (int i = 0; i < chatId.length; i++) {
//            String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
//
//            urlString = String.format(urlString, token, chatId[i], text);
//            url = new URL(urlString);
//            conn = url.openConnection();
//            sb = new StringBuilder();
//            is = new BufferedInputStream(conn.getInputStream());
//            br = new BufferedReader(new InputStreamReader(is));
//            inputLine = br.readLine();
//            sb.append(inputLine);
//
//        }
//    }
}

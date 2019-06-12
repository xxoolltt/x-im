package x.im.client;

import x.im.common.entity.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        Client client = new Client();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input;
            do {
                input = reader.readLine();
                switch (input) {
                    case "1":
                        System.out.println("send msg please input To ");
                        input = reader.readLine();
                        Message message = new Message();
                        long to = Long.valueOf(input);
                        message.setTo(to);
                        System.out.println("send msg please input context");
                        message.setContent(reader.readLine());
                        client.sendMsg(message);
                        break;
                    case "2":
                        System.out.println("login please input user_id");
                        long user_id = Long.valueOf(reader.readLine());
                        System.out.println("login please input pwd");
                        String pwd = reader.readLine();
                        client.login(user_id, pwd);
                        break;
                    case "3":
                        client.connect();
                        break;
                }
            } while (!input.equals("q"));
            System.out.println("close client");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}

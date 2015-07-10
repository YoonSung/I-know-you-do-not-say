package gaongil.dto.cloud.client;

import gaongil.dto.cloud.ClientStrategy;

/**
 * Created by yoon on 15. 7. 10..
 */
public class PlainText implements ClientDTO {

    private String text;

    private PlainText(){}

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PlainText{" +
                "text='" + text + '\'' +
                '}';
    }

    @Override
    public void process(ClientStrategy strategy) {
        System.out.println(strategy);
        System.out.println("PlainText");
    }
}

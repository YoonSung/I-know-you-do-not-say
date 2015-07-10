package gaongil.dto.cloud_refactoring.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import gaongil.dto.cloud_refactoring.ClientStrategy;

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
        System.out.println("PlainText");
    }
}

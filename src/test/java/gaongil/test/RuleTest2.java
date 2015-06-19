package gaongil.test;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by yoon on 15. 6. 19..
 */
public class RuleTest2 {

    @Test
    public void test1() {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("phoneNumber", "01099258547");
        map.add("regId", "testRegId");
        map.add("uuid", "testUuid");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user", map, String.class);

        HttpHeaders headers = response.getHeaders();

        String cookies = headers.get(HttpHeaders.SET_COOKIE).get(0);

        String[] cookieEntries = cookies.split(";");

        for (String cookieEntry : cookieEntries) {
            cookieEntry = cookieEntry.trim();
            if (cookieEntry.startsWith("WITH_AUTH")) {
                System.out.println(cookieEntry.replace("WITH_AUTH=",""));
            }
        }
    }

    @Test
    public void test2() {
        System.out.println("RuleTest2 - test2");
    }

    @Test
    public void test3() {
        System.out.println("RuleTest2 - test3");
    }
}

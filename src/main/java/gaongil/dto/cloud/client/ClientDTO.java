package gaongil.dto.cloud.client;

import gaongil.dto.cloud.ClientStrategy;

/**
 * Created by yoon on 15. 7. 10..
 */
public interface ClientDTO {
    void process(ClientStrategy strategy);
}

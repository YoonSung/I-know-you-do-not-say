package gaongil.dto.cloud_refactoring.client;

import gaongil.dto.cloud_refactoring.ClientStrategy;

/**
 * Created by yoon on 15. 7. 10..
 */
public interface ClientDTO {
    void process(ClientStrategy strategy);
}

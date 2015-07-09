package gaongil.dto.cloud_refactoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import gaongil.dto.cloud_refactoring.client.ClientDTO;
import org.springframework.util.Assert;

/**
 * Created by yoon on 15. 7. 7..
 */
public class CloudMessage {

    @JsonProperty("strategy")
    private int strategyCode;

    @JsonProperty("type")
    private int subTypeCode;
    private ClientDTO data;

    public CloudMessage(ClientStrategy strategyCode, ClientDTO data) {
        Assert.notNull(strategyCode);
        Assert.notNull(data);

        this.strategyCode = strategyCode.getStrategyCode();
        this.subTypeCode = strategyCode.getSubCode();

        this.data = data;
    }
}
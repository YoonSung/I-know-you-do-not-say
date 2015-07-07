package gaongil.dto.cloud;

import org.springframework.util.Assert;

/**
 * Created by yoon on 15. 7. 7..
 */
public class CloudMessage {

    private int processType;
    private CloudMessageData data;

    private CloudMessage(CloudMessageData type){
        this.processType = type.intValue();
        this.data = type;
    }

    public static CloudMessage createType1(Type1.SubType subType, Object data) {
        Assert.notNull(subType);
        Assert.notNull(data);

        return new CloudMessage(new Type1(subType, data));
    }

    // TODO
    public static CloudMessage createType2(Type2.SubType subType) {
        Assert.notNull(subType);

        return new CloudMessage(new Type2(subType));
    }

    // TODO
    public static CloudMessage createType3(Type3.SubType subType) {
        Assert.notNull(subType);

        return new CloudMessage(new Type3(subType));
    }

    // TODO
    public static CloudMessage createType4(Type4.SubType subType, Object data) {
        Assert.notNull(subType);
        Assert.notNull(data);

        return new CloudMessage(new Type4(subType, data));
    }
}
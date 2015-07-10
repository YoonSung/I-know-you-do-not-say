package gaongil.dto.cloud_refactoring;

import org.springframework.util.Assert;

/**
 * Created by yoon on 15. 7. 10..
 */
public interface ClientStrategy {
    int getStrategyCode();
    int getSubTypeCode();
    Class getDTO();
}
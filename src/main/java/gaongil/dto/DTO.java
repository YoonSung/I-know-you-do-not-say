package gaongil.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by yoon on 15. 7. 1..
 */
public interface DTO<T> {
    @JsonIgnore
    T getDomain();
}
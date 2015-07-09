package gaongil.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by yoon on 15. 7. 1..
 */
interface ConvertableToDomain<T> {
    @JsonIgnore
    T toDomain();
}
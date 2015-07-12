package gaongil.support.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yoon on 15. 7. 1..
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class WithPermissionException extends RuntimeException {
}

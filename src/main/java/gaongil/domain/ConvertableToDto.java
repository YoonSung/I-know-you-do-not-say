package gaongil.domain;

/**
 * Created by yoon on 15. 7. 9..
 */
interface ConvertableToDto<T> {
    T toDTOExcludeReferenceData();
    T toDTOWithReferenceData();
}
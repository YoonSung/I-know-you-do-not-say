package gaongil.dto.cloud_refactoring;

/**
 * Created by yoon on 15. 7. 10..
 */
public enum Strategy4 implements ClientStrategy {
    GROUP_INVITATION(1);

    private int subCode;

    Strategy4(int subCode) {
        this.subCode = subCode;
    }

    @Override
    public int getStrategyCode() {
        return 4;
    }

    @Override
    public int getSubCode() {
        return this.subCode;
    }
}

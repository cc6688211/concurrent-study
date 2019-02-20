package pers.cc.cas;

import java.util.function.LongBinaryOperator;

public class TestLongBinaryOperator implements LongBinaryOperator {

    @Override
    public long applyAsLong(long left, long right) {

        return left * right;
    }

}

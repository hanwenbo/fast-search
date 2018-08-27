package com.github.search.enums;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description: 集群状态
 * @time: 2018年06月21日
 * @modifytime:
 */
public enum HealthStatusEnum {

    GREEN((byte) 0),
    YELLOW((byte) 1),
    RED((byte) 2);

    private byte value;

    HealthStatusEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

}

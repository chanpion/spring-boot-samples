package com.chenpp.docker.output;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

/**
 * @author April.Chen
 * @date 2023/10/8 5:55 下午
 **/
public abstract class BaseConsumer<SELF extends BaseConsumer<SELF>> implements Consumer<OutputFrame> {

    @Getter
    @Setter
    private boolean removeColorCodes = true;

    public SELF withRemoveAnsiCodes(boolean removeAnsiCodes) {
        this.removeColorCodes = removeAnsiCodes;
        return (SELF) this;
    }
}
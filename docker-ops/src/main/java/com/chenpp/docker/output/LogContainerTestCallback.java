package com.chenpp.docker.output;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * @author April.Chen
 * @date 2023/10/8 6:20 下午
 **/
public class LogContainerTestCallback extends ResultCallback.Adapter<Frame> {
    protected final StringBuffer log = new StringBuffer();

    List<Frame> collectedFrames = new ArrayList<>();

    boolean collectFrames = false;

    public LogContainerTestCallback() {
        this(false);
    }

    public LogContainerTestCallback(boolean collectFrames) {
        this.collectFrames = collectFrames;
    }

    @Override
    public void onNext(Frame frame) {
        if (collectFrames) collectedFrames.add(frame);
        log.append(new String(frame.getPayload()));
    }

    @Override
    public String toString() {
        return log.toString();
    }


    public List<Frame> getCollectedFrames() {
        return collectedFrames;
    }
}
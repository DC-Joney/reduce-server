package org.wroker.reduce;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Setter
@Getter
@Accessors(chain = true)
public class HotKeyNoticeEvent {

    /**
     * appName
     */
    private String appName;

    /**
     * 热点key
     */
    private String hotKey;

    /**
     * 总的请求次数
     */
    private long count;

    /**
     * 缓存的时间
     */
    private long intervalMs;

    /**
     * 当前时间
     */
    private long currentTime;



    public void reset() {
        appName = null;
        count = 0;
        currentTime = 0;
        hotKey = null;
    }

}

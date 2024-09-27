package org.wroker;

import com.netflix.hystrix.metric.HystrixRequestEventsStream;
import com.netflix.hystrix.metric.HystrixThreadPoolCompletionStream;
import io.reactivex.Maybe;
import org.apache.poi.ss.formula.functions.T;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.TopicProcessor;
import reactor.core.publisher.UnicastProcessor;
import rx.Single;
import rx.subjects.PublishSubject;

public class ReactorTest {

    public static void main(String[] args) {
        UnicastProcessor<Object> objects = UnicastProcessor.create();


    }
}

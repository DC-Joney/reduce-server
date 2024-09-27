package org.wroker;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixCommandExample {

    public static void main(String[] args) {

        HystrixCommandGroupKey hystrixCommandGroupKey = HystrixCommandGroupKey.Factory.asKey("");

        HystrixCommand<String> hystrixCommand = new HystrixCommand<String>(hystrixCommandGroupKey) {
            @Override
            protected String run() throws Exception {
                return null;
            }
        };

        hystrixCommand.execute();
    }

}

package org.wroker;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReactorDemo {

    public static void main(String[] args) {
        Flux.range(1,10)
                .concatMap(Mono::just)
                .doOnNext(System.out::println)
                .subscribe();
    }

}

package io.nenodias.github.krabby.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
@Slf4j
public class KafkaConfiguration {

    @Bean
    public Sinks.Many<String> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<String>> messageProducer(Sinks.Many<String> many) {
        return () -> many.asFlux()
                .doOnNext(m -> log.info("Manually sending message {}", m))
                .doOnError(t -> log.error("Error encountered", t));
    }

    @Bean
    public Function<Flux<String>, Flux<String>> messageProcessor(){
        log.info("messageProcessor");
        return stringFlux -> stringFlux
                .map(this::doWork)
                .log();
    }

    private String doWork(String message){
        return "Olá " + message;
    }

    @Bean
    public Consumer<String> messageConsumer(){
        return (value) -> log.info("Consumer Received : " + value);
    }
}

package io.nenodias.github.krabby.events;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
@Slf4j
public class KafkaConfiguration {

    @Bean
    public Sinks.Many<Message<EventMessage>> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<EventMessage>>> messageProducer(Sinks.Many<Message<EventMessage>> many) {
        return () -> many.asFlux().doOnNext(m -> log.info("Manually sending message {}", m)).doOnError(t -> log.error("Error encountered", t));
    }

    @Bean
    public Function<Flux<Message<EventMessage>>, Flux<Message<EventMessage>>> messageProcessor() {
        log.info("messageProcessor");
        return stringFlux -> stringFlux.map(this::doWork).log();
    }

    private Message<EventMessage> doWork(Message<EventMessage> message) {
        return MessageBuilder.withPayload(new EventMessage("Olá " + message.getPayload().getMessage()))
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build();
    }

    @Bean
    public Consumer<Message<EventMessage>> messageConsumer() {
        return value -> log.info("Consumer Received : " + value.getPayload());
    }
}

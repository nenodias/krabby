package io.nenodias.github.krabby.endpoints;

import io.nenodias.github.krabby.events.EventMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.UUID;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
@RequestMapping("/")
public class EventController {

    @Autowired
    private Sinks.Many<Message<EventMessage>> many;

    @GetMapping
    public String input(@RequestParam final String message) {
        var msg = MessageBuilder.withPayload(new EventMessage(message))
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .build();
        many.emitNext(msg, Sinks.EmitFailureHandler.FAIL_FAST);
        var response = MessageFormat.format("Message: {0} received", message);
        log.info(response);
        return response;
    }
}

package io.nenodias.github.krabby.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@RestController
@RequestMapping("/")
public class EventController {

    @Autowired
    private Sinks.Many<String> many;

    @GetMapping
    public String input(@RequestParam final String message){
        many.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        var response = MessageFormat.format("Message: {0} received", message);
        log.info(response);
        return response;
    }
}

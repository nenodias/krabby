package io.nenodias.github.krabby.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class EventMessage implements Serializable {
    private String message;

    public EventMessage(String message) {
        this.message = message;
    }

}

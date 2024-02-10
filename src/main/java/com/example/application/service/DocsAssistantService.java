package com.example.application.service;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;

@Service
@BrowserCallable
@AnonymousAllowed
public class DocsAssistantService {
    private final Koda koda;

    public DocsAssistantService(Koda koda) {
        this.koda = koda;
    }

    public Flux<String> getCompletionStream(String chatId, String framework, String message) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        koda.getStream(chatId, framework, message)
                .onNext(sink::tryEmitNext)
                .onComplete(e -> sink.tryEmitComplete())
                .onError(sink::tryEmitError)
                .start();

        return sink.asFlux();
    }

    public List<Framework> getSupportedFrameworks() {
        return List.of(
                new Framework("Flow", "flow"),
                new Framework("Hilla with React", "hilla-react"),
                new Framework("Hilla with work Lit", "hilla-lit")
        );
    }
}

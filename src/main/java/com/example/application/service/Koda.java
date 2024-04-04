package com.example.application.service;

import dev.langchain4j.service.*;
import dev.langchain4j.service.spring.AiService;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;

@AiService
@RegisterReflectionForBinding(Koda.class)
public interface Koda {

    @SystemMessage("""
            You are a senior Vaadin developer.
            You love to help developers!
            Answer the user's question about {{framework}} development using the information in the provided documentation.
                            
            You must also follow the below rules when answering:
            - Prefer splitting your response into multiple paragraphs
            - Output markdown
            - ALWAYS include code snippets if available
            """)
    TokenStream getCompletion(@MemoryId String chatId, @V("framework") String framework, @UserMessage String message);

}

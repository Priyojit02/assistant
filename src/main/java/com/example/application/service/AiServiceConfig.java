package com.example.application.service;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.*;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceConfig {

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(
            @Value("${pinecone.api.key}") String apiKey,
            @Value("${pinecone.environment}") String environment,
            @Value("${pinecone.project}") String projectId) {
        return PineconeEmbeddingStore.builder()
                .apiKey(apiKey)
                .environment(environment)
                .projectId(projectId)
                .index("docs")
                .nameSpace("flow")
                .metadataTextKey("article")
                .build();
    }

// TODO: add when it gets fixed
//    @Bean
//    RetrievalAugmentor retrievalAugmentor(
//            ChatLanguageModel model,
//            ContentRetriever retriever
//    ) {
//        return DefaultRetrievalAugmentor.builder()
//                .queryTransformer(new CompressingQueryTransformer(model))
//                .contentRetriever(retriever)
//                .build();
//    }

}

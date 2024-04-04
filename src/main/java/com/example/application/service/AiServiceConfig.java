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
    EmbeddingStore<TextSegment> embeddingStore(@Value("${pinecone.api.key}") String apiKey) {
        return PineconeEmbeddingStore.builder()
                .apiKey(apiKey)
                .environment("aped-4627-b74a")
                .projectId("9gyeph2")
                .index("docs")
                .nameSpace("flow")
                .metadataTextKey("article")
                .build();
    }

//    @Bean
//    RetrievalAugmentor retrievalAugmentor(
//            EmbeddingStore<TextSegment> embeddingStore,
//            EmbeddingModel embeddingModel,
//            ChatLanguageModel model
//    ) {
//
//        var retriever = EmbeddingStoreContentRetriever.builder()
//                .embeddingStore(embeddingStore)
//                .embeddingModel(embeddingModel)
//                .maxResults(5)
//                .minScore(0.6)
//                .build();
//
//        return DefaultRetrievalAugmentor.builder()
//                .queryTransformer(new CompressingQueryTransformer(model))
//                .contentRetriever(retriever)
//                .build();
//    }

}

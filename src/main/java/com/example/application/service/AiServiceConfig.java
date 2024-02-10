package com.example.application.service;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.*;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiServiceConfig {

    @Bean
    StreamingChatLanguageModel model(@Value("${openai.api.key}") String apiKey) {
        return OpenAiStreamingChatModel.builder()
                .modelName(OpenAiChatModelName.GPT_4_TURBO_PREVIEW)
                .apiKey(apiKey)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    @Bean
    Tokenizer tokenizer() {
        return new OpenAiTokenizer(OpenAiChatModelName.GPT_4_TURBO_PREVIEW.toString());
    }

    @Bean
    EmbeddingModel embeddingModel(@Value("${openai.api.key}") String apiKey) {
        return OpenAiEmbeddingModel.builder()
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002)
                .apiKey(apiKey)
                .build();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(@Value("${pinecone.api.key}") String apiKey) {
        return PineconeEmbeddingStore.builder()
                .apiKey(apiKey)
                .environment("eu-west4-gcp")
                .projectId("64ac3f2")
                .index("docs")
                .nameSpace("flow")
                .metadataTextKey("text")
                .build();
    }

    @Bean
    ContentRetriever contentRetriever(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5)
                .minScore(0.5)
                .build();
    }

    @Bean
    Koda koda(
            StreamingChatLanguageModel model,
            ContentRetriever contentRetriever,
            Tokenizer tokenizer
    ) {
        return AiServices.builder(Koda.class)
                .streamingChatLanguageModel(model)
                .chatMemoryProvider(chatId -> TokenWindowChatMemory.builder()
                        .id(chatId)
                        .maxTokens(1000, tokenizer)
                        .build())
                .contentRetriever(contentRetriever)
                .build();
    }
}

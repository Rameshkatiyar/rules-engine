package com.tech.languageResolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

@Configuration
public class SpelParserConfig {
    @Bean
    public StandardEvaluationContext standardEvaluationContext() {
        return new StandardEvaluationContext();
    }

    @Bean
    public SpelExpressionParser spelExpressionParser() {
        return new SpelExpressionParser();
    }
}

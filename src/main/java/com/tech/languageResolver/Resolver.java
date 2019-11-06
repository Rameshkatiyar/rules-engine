package com.tech.languageResolver;

public interface Resolver<I,O> {
    String EMPTY = "";
    String getResolverKeyword();
    Object resolveValue(String keyword, I inputData, O outputResult);
}

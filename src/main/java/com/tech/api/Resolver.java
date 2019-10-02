package com.tech.api;

public interface Resolver<I> {
    String EMPTY = "";
    String getResolverKeyword();
    Object resolveValue(String keyword, I inputData, Object setValue);
}

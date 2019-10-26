package com.tech.languageResolver;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
public class KeywordResolver {
    Map<String, Resolver> keywordResolverList;

    @Autowired
    public KeywordResolver(List<Resolver> resolverList) {
        keywordResolverList = resolverList.stream()
                .collect(Collectors.toMap(Resolver::getResolverKeyword, Function.identity()));
    }

    public Map<String, Resolver> getKeywordResolverList(){
        return keywordResolverList;
    }

    public Optional<Resolver> getResolver(String keyword) {
        return Optional.ofNullable(keywordResolverList.get(keyword));
    }
}

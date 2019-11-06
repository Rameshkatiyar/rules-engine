package com.tech.dslResolver;

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
public class DSLKeywordResolver {
    Map<String, DSLResolver> dslKeywordResolverList;

    @Autowired
    public DSLKeywordResolver(List<DSLResolver> resolverList) {
        dslKeywordResolverList = resolverList.stream()
                .collect(Collectors.toMap(DSLResolver::getResolverKeyword, Function.identity()));
    }

    public Map<String, DSLResolver> getDslKeywordResolverList(){
        return dslKeywordResolverList;
    }

    public Optional<DSLResolver> getResolver(String keyword) {
        return Optional.ofNullable(dslKeywordResolverList.get(keyword));
    }
}

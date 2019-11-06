package com.tech.languageResolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class DSLKeywordResolver {
    private static final Pattern GET_FROM_INPUT_PATTERN = Pattern.compile("\\$\\(input((\\.\\w+))\\)");
    private static final Pattern SET_TO_RESULT_PATTERN = Pattern.compile("\\$\\((\\w+)((\\.\\w+)?)((=\\w+)?)\\)");



    private static final Pattern SET_PATTERN = Pattern.compile("#\\((\\w+)((=\\w+))\\)");


    private static final Pattern DSL_PATTERN = Pattern.compile("\\$\\((\\w+)((\\.\\w+)?)((=\\w+)?)\\)");

    private static final String DOT = ".";
    private static final String SET_ASSIGNMENT = "=";
    private static final String INPUT_DATA_RESOLVER = "input";
    private static final String RESULT_DATA_RESOLVER = "result";
}

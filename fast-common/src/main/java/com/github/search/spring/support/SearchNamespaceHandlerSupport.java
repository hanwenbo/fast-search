package com.github.search.spring.support;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年05月25日
 * @modifytime:
 */
public class SearchNamespaceHandlerSupport extends NamespaceHandlerSupport {

    public SearchNamespaceHandlerSupport() {
    }

    @Override
    public void init() {
        SearchNamespaceParserSupport helper = new SearchNamespaceParserSupport();
        this.registerBeanDefinitionParser("client",new SearchDefinitionParser(helper));
    }
}

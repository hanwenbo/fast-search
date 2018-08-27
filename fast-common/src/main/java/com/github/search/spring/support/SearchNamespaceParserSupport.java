package com.github.search.spring.support;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Conventions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年05月26日
 * @modifytime:
 */
public class SearchNamespaceParserSupport {



    public void parseAttributes(Element element, BeanDefinitionBuilder builder) {
        NamedNodeMap attributes = element.getAttributes();

        for(int x = 0; x < attributes.getLength(); ++x) {
            Attr attribute = (Attr)attributes.item(x);
            if(this.isEligibleAttribute(attribute)) {
                String propertyName = attribute.getLocalName();
                propertyName = Conventions.attributeNameToPropertyName(propertyName);
                Assert.state(StringUtils.hasText(propertyName), "Illegal property name returned from \'extractPropertyName(String)\': cannot be null or empty.");
                builder.addPropertyValue(propertyName, attribute.getValue());
            }
        }

    }


    public boolean isEligibleAttribute(String attributeName) {
        return !attributeName.equals("xmlns") && !attributeName.startsWith("xmlns:") && !"id".equals(attributeName) && !"name".equals(attributeName);
    }

    public boolean isEligibleAttribute(Attr attribute) {
        return this.isEligibleAttribute(attribute.getName());
    }


    public BeanComponentDefinition registerBeanDefinition(BeanDefinitionBuilder builder, String id, String[] aliases, ParserContext parserContext) {
        BeanDefinitionHolder holder = new BeanDefinitionHolder(builder.getBeanDefinition(), id, aliases);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, parserContext.getRegistry());
        BeanComponentDefinition componentDefinition = new BeanComponentDefinition(holder);
        parserContext.registerComponent(componentDefinition);
        return componentDefinition;
    }

    public String getId(Element element, BeanDefinitionBuilder builder, ParserContext parserContext) {
        String id = element != null?element.getAttribute("id"):null;
        if(!StringUtils.hasText(id)) {
            id = this.generateId(builder, parserContext);
        }

        return id;
    }

    public String generateId(BeanDefinitionBuilder builder, ParserContext parserContext) {
        return parserContext.getReaderContext().generateBeanName(builder.getRawBeanDefinition());
    }
}

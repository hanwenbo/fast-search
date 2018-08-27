package com.github.search.spring.support;

import com.github.search.commons.ESConnect;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.Conventions;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年05月26日
 * @modifytime:
 */
public class SearchDefinitionParser implements BeanDefinitionParser {

    // private static final String INIT_METHOD =  "initTransportClient";

    private final SearchNamespaceParserSupport helper;

    public SearchDefinitionParser(SearchNamespaceParserSupport helper) {
        this.helper = helper;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        builder.getRawBeanDefinition().setBeanClass(ESConnect.class);
        builder.getRawBeanDefinition().setSource(parserContext.extractSource(element));
        if(parserContext.isNested()) {
            builder.setScope(parserContext.getContainingBeanDefinition().getScope());
        }
        if(parserContext.isDefaultLazyInit()) {
            builder.setLazyInit(true);
        }
        this.helper.parseAttributes(element,builder);
        this.parseChildElements(element,builder,parserContext);
        String id = this.helper.getId(element, builder, parserContext);
        this.helper.registerBeanDefinition(builder,id,new String[]{},parserContext);
        // builder.setInitMethodName(INIT_METHOD);
        return builder.getBeanDefinition();
    }


    private void parseChildElements(Element element, BeanDefinitionBuilder builder, ParserContext parserContext) {
        if(element.hasChildNodes()) {
            List childElts = DomUtils.getChildElements(element);
            Iterator var8 = childElts.iterator();
            List<String> list = null;
            while(var8.hasNext()) {
                Element elt = (Element)var8.next();
                if("cluster-servers".equals(elt.getLocalName())) {
                    NamedNodeMap attributes = elt.getAttributes();
                    for(int x = 0; x < attributes.getLength(); ++x) {
                        Attr attribute = (Attr)attributes.item(x);
                        if(this.helper.isEligibleAttribute(attribute)) {
                            String propertyName = attribute.getLocalName();
                            propertyName = Conventions.attributeNameToPropertyName(propertyName);
                            Assert.state(StringUtils.hasText(propertyName), "Illegal property name returned from \'extractPropertyName(String)\': cannot be null or empty.");
                            builder.addPropertyValue(propertyName, attribute.getValue());
                        }
                        parseChildElements(elt,builder,parserContext);
                    }
                }
                if("node-address".equals(elt.getLocalName())){
                    String value = elt.getAttribute("value");
                    Assert.state(StringUtils.hasText(value), "Illegal property value returned from \'extractPropertyName(String)\': cannot be null or empty.");
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    list.add(value);
                }
            }
            if(list != null){
                builder.addPropertyValue("address",list);
            }
        }
    }




}
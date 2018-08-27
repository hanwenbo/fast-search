# Search
Search项目是基于ElasticSearch搜索服务提供的简易API调用，支持以下功能
---------------
## 数据索引
* [添加索引数据](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/BaseInsert.java)
* [更新索引数据(UpdateById/UpdateByQuery)](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/BaseUpdate.java)
* [数据批量操作(批量添加/删除/修改)](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/BulkOperation.java)
* [路由(routing)](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/RoutingOperation.java)
* [建议查询(Suggest)](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/SuggestSearch.java)
* [Mustache模板查询](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/TemplateSearch.java)
* [索引数据删除(DeleteById/DeleteByQuery/DeleteByType)](https://github.com/ZhuBaker/fast-search/tree/master/fast-common/src/main/java/com/github/search/index/BaseDelete.java)
---------------
## 检索功能
### 检索功能,支持基本逻辑查询：

1. “& | ! ” 查询
2. “ 嵌套 & | ! ”查询 
3. EQL查询(自定义SQL) 
4. 聚合查询方式

### 每个简单数据类型查询单元支持

1. {term:中国西域}  --- 精确查询
2. {match:中国西域}  --- 匹配(支持分词)
3. {prefix:中国西域}  --- 前缀
4. {wildcard:W?F*HW}  --- 通配符
5. {regexp:W[0-9].+}  --- 正则

---------------

## 类SQL查询规则
### 概念：
> 搜索单元 
> * 概念:用于搜索的“一个”逻辑单元,小括号括起,并且每个括号是一个field的比较
> * 书写形式:(field:value)
> * Note:(a:1)、(1&lt;a&lt;10) 、(a&gt;5&a&lt;10) 都是单个field的比较

---------------

> 值函数
> * 概念:函数解析使用{}括起(未使用函数解析的均认为是term解析查询)
> * 书写形式:{term:中国西域}
> * 函数穷举:
>   * {term:中国西域}           ——词条查询
>   * {match:中国西域}          ——匹配(支持分词)
>   * {prefix:中国西域}         ——前缀
>   * {wildcard:W?F*HW}         ——通配符
>   * {regexp:W[0-9].+}         ——正则
> * Node:(supplierName:{wildcard:北京商贸*}) 查询已北京商贸开头的相关信息

---------------

> 范围查询
> * 概念:范围查询针对集合区间在后台处理做了抽象,支持任意的开闭原则及范围区间查询 支持基本数据类型范围查询及日期类型范围查询
> * 书写形式:(value1&lt;field&lt;=value2)、(field&lt;v2)、(field&lt;v2)
> * Node:
>   * 闭区间查询保证字段在值的中间如: v1&lt;field&lt;v2
>   * 开区间查询保证字段在值的左边如: field&gt;v1 或者 field&lt;v2
>   * 日期范围查询日期格式为 “yyyy-MM-dd HH:mm:ss”

---------------

> 取反查询运算
> * 概念:满足条件的反向,只能用于查询语句的前面 用!表示
> * 书写形式: !(a&lt;=10) 、!(&(a:1|2|3)&(b:zhangsan))
> * Node: !(a&lt;10) = (a&gt;=10)

---------------

> 逻辑运算符
> * 概念: 用于单元中与 或 的逻辑运算,用& | 表示
> * 书写形式: !(a&lt;=10) 、!(&(a:1|2|3)&(b:zhangsan))
> * Node: !(a&lt;10) = (a&gt;=10)

---------------

### 搜索案例：
* 精确查找：(supplierId:2241527253818753)
* 单字段或查询：
    * (cityId:1|18|241)
    * !(cityId:1|18|241)
* 简单范围查询:
    * (goodsStorage&gt;100000000)
    * (goodsStorage&lt;=10)
    * (goodsStorage&lt;=10|goodsStorage&gt;100000000)
    * !(goodsStorage&lt;=10|goodsStorage&gt;100000000)同(10&lt;goodsStorage&lt;=100000000)等价
* 日期范围查询：yyyy-MM-dd HH:mm:ss
    * (createTime&lt;2018-05-15 00:00:00)
    * (createTime*gt;=2018-05-15 00:00:00)
    * !(createTime&lt;=2018-05-15 00:00:00)
    * (createTime&lt;=2018-05-15 00:00:00|createTime&gt;2018-06-15 00:00:00)
    * !(createTime&lt;=2018-05-15 00:00:00|createTime&gt;2018-06-15 00:00:00)与(2018-05-15 00:00:00&lt;createTime&lt;=2018-06-15 00:00:00)等价
* 函数查询：
    * (supplierName:{prefix:测试}) 前缀
    * (supplierName:{wildcard:*测试*}) 通配符
    * (supplierName:{regexp:[^Baker]+测试.*}) 正则
* 组合查询：
    * (&(2018-05-15 00:00:00&lt;=createTime&lt;=2018-06-15 00:00:00)&(goodsStorage&lt;1000))
    * !(&(2018-05-15 00:00:00&lt;=createTime&lt;=2018-06-15 00:00:00)&(goodsStorage&lt;1000))
    * (&(2018-05-15 00:00:00&lt;=createTime&lt;=2018-06-15 00:00:00)&(goodsStorage&lt;1000)&(supplierId:2241527253818753))
    * !(supplierName:{regexp:[^Baker]+测试.*})
    * (&(supplierName:{regexp:[^Baker]+测试.*})&!(5&lt;goodsNum&lt;25))
    * &!(&(supplierName:{regexp:[^Baker]+测试.*})&!(5&lt;goodsNum&lt;25))

---------------

### EQL数据组织
```java
String eql = "|(attrIds:2232012366099328|189)|(attrIds:2292774003989889|2300097498406272)";
BoolPager boolPager = EqlToPagerConverterUtils.convertToPager(eql);
boolPager.setPageNo(pageNo);
boolPager.setPageSize(pageSize);
boolPager.set_index(dto.getIndexName().trim());
boolPager.set_type(dto.getIndexType().trim());
if(StringUtils.isNotBlank(dto.getSortName())) {
	if("desc".equals(dto.getSortOrder())){
		sortField.put(dto.getSortName().trim(), SearchFactor.DESC);
	}else {
		sortField.put(dto.getSortName().trim(), SearchFactor.ASC);
	}
	boolPager.setSortFields(sortField);
}
BoolPager resultPager = BaseSearch.boolQuery(transportClient, boolPager);
System.out.println(resultPager.getResult());
```

## 搜索平台
#### 分词功能
![分词](https://wx1.sinaimg.cn/mw690/006QW2Smgy1fu3drpx81kj311k13hajs.jpg "分词")

#### 检索功能
![检索](https://wx4.sinaimg.cn/mw690/006QW2Smgy1fu3drrv9pnj30w81fuh5m.jpg "检索")

#### 聚合........




---------------
## 插件功能
IK分词器进行二次开发，支持数据库扫描热词、停止词功能。
添加ElasticSearch TokenFilter 扩展,增加以数据库为数据源的联想词功能,源码下载
* [elasticsearch-analysis-ik](git@git.58qf.com:business/project/platform/elasticsearch-analysis-ik.git)
IK分词器配置使用方式不变,内部自动集成MySQL热词数据源

联想词`ik_synonym`功能扩展
```bash
curl -XPUT http://XXX.XXX.XXX.XXX:9200/g_i -d '
{
  "settings":{
    "refresh_interval":"1s",
    "number_of_replicas":1,
    "number_of_shards":1,
    "analysis":{
      "filter":{
        "by_tfr":{
          "type":"stop",
          "stopwords":[" "]
        },
        "by_sfr":{
          "type":"ik_synonym",
          "ignore_case":true,
          "expand":true
        }
      },
      "analyzer":{
        "by_smart":{
          "type":"custom",
          "char_filter": [
            "html_strip"
          ],
          "tokenizer":"ik_smart",
          "filter":[
            "by_sfr"
          ]
        },
        "by_max_word":{
          "type":"custom",
          "char_filter": [
            "html_strip"
          ],
          "tokenizer":"standard",
          "filter":[
            "by_sfr"
          ]
        }
      }
    }
  },
  "mappings":{
    "g_t": {
      "properties": {
        "goodsName": {
          "type":  "text",
          "analyzer": "by_smart"
        }
      }
    }
  }
}'
```


---------------
## 聚合功能
[聚合查询](https://git.58qf.com/business/qfelasticsearch/tree/dev_platform/es-common/src/main/java/com/qf58/search/index/aggr)

---------------
# 自定义Spring标签
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:search="http://www.58qf.com/schema/search"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
      http://www.58qf.com/schema/search http://www.58qf.com/schema/search/search-1.1.xsd">

    <search:client id="esConnect">
        <search:cluster-servers cluster-name="elasticsearch" ping-timeout="10s" ignore-cluster-name="true" cluster-sniff="true">
            <search:node-address value="XXX.XXX.XXX.XXX:9300"/>
            <search:node-address value="XXX.XXX.XXX.XXX:9300"/>
            <search:node-address value="XXX.XXX.XXX.XXX:9300"/>
            <search:node-address value="XXX.XXX.XXX.XXX:9300"/>
        </search:cluster-servers>
    </search:client>

</beans>
```
---------------
# 查询数据组织(统一入口) 
## 针对查询采用统一的查询入口进行数据组织
![BoolPager](https://wx1.sinaimg.cn/mw690/006QW2Smgy1fu3b6rzvrpj30es0he3z5.jpg "逻辑查询数据类型")
## 组织形式
```java
BoolPager boolPager = new BoolPager();
boolPager.setPageNo(pageNo);
boolPager.setPageSize(pageSize);
List<ValuePackage> vps = new ArrayList<>();
List<ValueEntity> vs = new ArrayList<>();

vs.add(new ValueEntity.Builder("categoryId",new Object[]{categoryId.toString()}).setNot().build());
vs.add(new ValueEntity.Builder("supplierName",new Object[]{"*"+supplierName+"*"}).rule(SearchType.WILDCARD_QUERY).build());
vs.add(new ValueEntity.Builder("orderStatus",new Object[]{1}).build());//接单状态 1正常接单
vs.add(new ValueEntity.Builder("isDel",new Object[]{0}).build());//供应商状态 1正常 2暂停接单
if(isSpecial != null && isSpecial == 1){//自营订单传 1 限制可接自营供应商才可接该单 进行限制
    vs.add(new ValueEntity.Builder("isSpecial",new Object[]{isSpecial}).build());//供应商状态 1正常 2暂停接单
}
if(isTest != null){//自营订单传 1 限制可接自营供应商才可接该单 进行限制
    vs.add(new ValueEntity.Builder("isTest",new Object[]{isTest}).build());
}

ValuePackage vp = new ValuePackage();
vp.setEntitys(vs);
vps.add(vp);
boolPager.setQuery(vps);
boolPager.set_index(IndexConf.supplierIndex);
boolPager.set_type(IndexConf.categoryType);
boolPager.setFields(new String[]{"supplierId","supplierName"});//获取供应商id和供应商名称

boolPager =  BaseSearch.boolQuery(client,boolPager);
```
ValuePackage类似于一个查询实体包 ,是包装了多个查询逻辑单元组合的查询集合,如果对ElasticSearch Restful调用
![ValuePackage](https://wx3.sinaimg.cn/mw690/006QW2Smgy1fu3b6ryytrj309v08xmx5.jpg "最外层逻辑运算实体结构")
![ValueEntity](https://wx1.sinaimg.cn/mw690/006QW2Smgy1fu3b6ryrq5j30950a7dfv.jpg "逻辑运算单元")

---------------



``
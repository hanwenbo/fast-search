package com.github.search;

import com.github.search.commons.ESConnect;
import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;
import com.github.search.index.BaseSearch;
import com.github.search.page.BoolPager;
import com.github.search.pub.SearchType;
import com.github.search.pub.ValueEntity;
import com.github.search.pub.ValuePackage;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月22日
 * @modifytime:
 */
public class BaseSearchTest {

    @Test
    public void indexTypeGet() throws Exception{
       /* ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.117:9300");
        address.add("172.16.11.117:9301");
        address.add("172.16.11.117:9302");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        BoolPager pager = new BoolPager();
        pager.setPageNo(1);
        pager.setPageSize(10);
        Map<String,ValueEntity> map = new HashMap<>();
        ValueEntity valueEntity = new ValueEntity(SearchType.NUMBER_RANGE_INNER_QUERY,new Object[]{100,1000});
        map.put("goodsStorage",valueEntity);
        pager.setMustQuery(map);
        pager.set_index("goods_index");
        pager.set_type("goods_type");
        BoolPager boolPager = BaseSearch.boolQuery(client, pager);
        System.out.println(boolPager);*/

    }

    @Test
    public void indexTypeGet2() throws Exception{
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.6.87:9300");
        address.add("172.16.6.82:9300");
        address.add("172.16.6.90:9300");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        BoolPager pager = new BoolPager();
        pager.setPageNo(1);
        pager.setPageSize(40);

        List<ValueEntity> vs = new ArrayList<>();
        vs.add(new ValueEntity.Builder("supplierId", new Object[]{2277339560646017L}).build());
        ValueEntity cateJoinId = new ValueEntity.Builder("cateJoinId", new Object[]{2251835875521408L, 2251835615196033L}).build();
        cateJoinId.setAndOrFlag(AndOrEnums.OR.getCode());
        vs.add(cateJoinId);

        ValueEntity cityIds = new ValueEntity.Builder("cityIds", new Object[]{1L, 2L}).build();
        cityIds.setAndOrFlag(AndOrEnums.OR.getCode());
        vs.add(cityIds);

        List<ValuePackage> packages = new ArrayList<>();
        ValuePackage pk = new ValuePackage();
        pk.setEntitys(vs);
        packages.add(pk);
        pager.setQuery(packages);
        pager.setFields(new String[]{"supplierId","cateJoinId","cityIds"});
        pager.set_index("goods_index");
        pager.set_type("goods_type");

        BoolPager boolPager = BaseSearch.boolQuery(client, pager);
        System.out.println(boolPager);

    }

    @Test
    public void test2() throws Exception {

        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.117:9300");
        address.add("172.16.11.117:9301");
        address.add("172.16.11.117:9302");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        BoolPager pager = new BoolPager();
        pager.setPageNo(1);
        pager.setPageSize(10);
        pager.set_index("goods_index");
        pager.set_type("goods_type");

        ValueEntity ve1 = new ValueEntity.Builder("goodsStorage",new Object[]{2000,3000}).rule(SearchType.NUMBER_RANGE_INNER_QUERY).build();
        ValueEntity ve2 = new ValueEntity.Builder("marketPrice",new Object[]{900,100000}).rule(SearchType.NUMBER_RANGE_OUTER_QUERY).build();
        ValueEntity ve3 = new ValueEntity.Builder("shopId",new Object[]{2233027778108801L}).rule(SearchType.TERM_QUERY).build();
        List<ValueEntity> vs = Arrays.asList(new ValueEntity[]{ve1,ve2,ve3});

        ValueEntity ve4 = new ValueEntity.Builder("cateJoinId",new Object[]{2232012400882560L}).build();
        List<ValueEntity> vs2 = Arrays.asList(new ValueEntity[]{ve4});

        ValuePackage vp = new ValuePackage();
        vp.setEntitys(vs);
        vp.setAndOrFlag(AndOrEnums.OR.getCode());

        ValuePackage vp2 = new ValuePackage();
        vp2.setEntitys(vs2);
        vp2.setNotFlag(NotFlagEnum.NEGATIVE.getCode());
        vp2.setAndOrFlag(AndOrEnums.AND.getCode());

        List<ValuePackage> packages = Arrays.asList(new ValuePackage[]{vp,vp2});

        pager.setQuery(packages);

        BoolPager boolPager = BaseSearch.boolQuery(client, pager);
        System.out.println(boolPager);
    }




    @Test
    public void test3() throws Exception {

        // TODO
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.117:9300");
        address.add("172.16.11.117:9301");
        address.add("172.16.11.117:9302");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        BoolPager pager = new BoolPager();
        pager.setPageNo(1);
        pager.setPageSize(10);
        pager.set_index("goods_index");
        pager.set_type("goods_type");

        ValueEntity ve1 = new ValueEntity.Builder("goodsStorage",new Object[]{2000,3000}).rule(SearchType.NUMBER_RANGE_INNER_QUERY).build();
        ValueEntity ve2 = new ValueEntity.Builder("marketPrice",new Object[]{900,100000}).rule(SearchType.NUMBER_RANGE_OUTER_QUERY).build();
        ValueEntity ve3 = new ValueEntity.Builder("shopId",new Object[]{2233027778108801L}).build();
        List<ValueEntity> vs = Arrays.asList(new ValueEntity[]{ve1,ve2,ve3});



        ValuePackage vp = new ValuePackage();
        vp.setEntitys(vs);
        vp.setAndOrFlag(AndOrEnums.OR.getCode());


        List<ValuePackage> packages = Arrays.asList(new ValuePackage[]{vp});

        pager.setQuery(packages);

        BoolPager boolPager = BaseSearch.boolQuery(client, pager);
        System.out.println(boolPager);
    }



    @Test
    public void test4() throws Exception{
        ESConnect connect = new ESConnect();
        connect.setClusterName("elasticsearch");
        connect.setPingTimeout("10s");
        connect.setIgnoreClusterName(true);
        connect.setClusterSniff(true);
        List<String> address = new ArrayList<>();
        address.add("172.16.11.117:9300");
        address.add("172.16.11.117:9301");
        address.add("172.16.11.117:9302");
        connect.setAddress(address);
        connect.getTransportClient();
        TransportClient client = connect.getTransportClient();
        BoolPager pager = new BoolPager();
        pager.setPageNo(1);
        pager.setPageSize(10);
        pager.set_index("goods_index");
        pager.set_type("goods_type");

        List<ValuePackage> vps = new ArrayList<>();
        List<ValueEntity> vs = new ArrayList<>();
        ValueEntity valueEntity = new ValueEntity.Builder("attrIds", new Object[]{2232012366099328L, 189L}).build();
        valueEntity.setAndOrFlag(AndOrEnums.OR.getCode());
        vs.add(valueEntity);
        ValueEntity valueEntity2 = new ValueEntity.Builder("attrIds", new Object[]{2292774003989889L, 2300097498406272L}).build();
        valueEntity2.setAndOrFlag(AndOrEnums.OR.getCode());
        vs.add(valueEntity2);
        ValuePackage vp = new ValuePackage();
        vp.setEntitys(vs);
        vps.add(vp);
        pager.setQuery(vps);
        pager = BaseSearch.boolQuery(client, pager);

        System.out.println(pager);

    }




}

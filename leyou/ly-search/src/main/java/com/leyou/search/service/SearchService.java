package com.leyou.search.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specClient;
    public Goods buildGoods(Spu spu){
        Long spuId = spu.getId();
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        if (CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
        //查询品牌
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        if (brand==null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        //搜索字段
        String all=spu.getTitle() + StringUtils.join(names," ")+brand.getName();
        //查询SKU
        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOND);
        }
        //对sku进行处理
        List<Map<String,Object>> skus = new ArrayList<>();
        Set<Long> priceList = new HashSet<>();
        for (Sku sku : skuList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",sku.getId());
            map.put("title",sku.getTitle());
            map.put("price",sku.getPrice());
            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
            skus.add(map);
            priceList.add(sku.getPrice());
        }
        //查询规格参数
        List<SpecParam> params = specClient.queryParamList(null, spu.getCid3(), true);
        if (CollectionUtils.isEmpty(params)){
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        //查询商品详情
        SpuDetail spuDetail = goodsClient.queryDetailById(spuId);
        //通用规格参数
        Map<Long , String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //特有规格参数
        //String json = spuDetail.getSpecialSpec();
        Map<Long, List<String>> specialSpec = JsonUtils
                .nativeRead( spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
        //规格参数
        Map<String, Object> specs = new HashMap<>();
        for (SpecParam param : params) {
            String key = param.getName();
            Object value="";
            //判断是否是通用价格
            if (param.getGeneric()) {
               value=genericSpec.get(param.getId());
                if (param.getNumeric()) {
                    value=chooseSegment(value.toString(),param);
                }
            }else{
                value=specialSpec.get(param.getId());
            }
            //存入map
            specs.put(key,value);
        }

        //构建good对象
        Goods goods = new Goods();
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setId(spuId);
        goods.setAll(all);//搜素字段 标题品牌
        goods.setPrice(priceList); // 所有sku的价格几何
        goods.setSkus(JsonUtils.toString(skus));
        goods.setSpecs(specs);
        goods.setSubTitle(spu.getSubTitle());
        return goods;
    }
    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }
    //重新开始再来一次加油
//    @Autowired
//    private CategoryClient categoryClient;
//    @Autowired
//    private BrandClient brandClient;
//    @Autowired
//    private GoodsClient goodsClient;
//    @Autowired
//    private SpecificationClient specClient;
//    public Goods buildGoods(Spu spu){
//        //        Long spuId = spu.getId();
//        Long spuId = spu.getId();
//
//        //查询分类
//        List<Category> categories = categoryClient.queryCategoryByIds(
//                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
//        if (CollectionUtils.isEmpty(categories)){
//            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
//        }
//        List<String> names = categories.stream().map(Category::getName).collect(Collectors.toList());
//
//        //查询品牌
//        Brand brand = brandClient.queryBrandById(spu.getBrandId());
//        if (brand==null) {
//            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
//        }
//
//        //搜索字段
//        String all=spu.getTitle()+ StringUtils.join(names,"")+ brand.getName();
//        //查询sku
//        List<Sku> skuList = goodsClient.querySkuBySpuId(spuId);
//        if (CollectionUtils.isEmpty(skuList)){
//            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOND);
//        }
//        //对sku进行处理
//        List<Map<String,Object>> skus= new ArrayList<>();
//        //价格
//        Set<Long> priceList = new HashSet<>();
//        for (Sku sku : skuList) {
//            Map<String,Object> map=new HashMap<>();
//            map.put("id",sku.getId());
//            map.put("title",sku.getTitle());
//            map.put("price",sku.getPrice());
//            map.put("image",StringUtils.substringBefore(sku.getImages(),","));
//
//            priceList.add(sku.getPrice());
//        }
//        //查询规格参数
//        List<SpecParam> params = specClient.queryParamList(null, spu.getCid3(), true);
//        if (CollectionUtils.isEmpty(params)){
//            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
//        }
//        //查询商品详情
//        SpuDetail spuDetail=goodsClient.queryDetailById(spuId);
//        //获取通用
//
//        Map<String, String> genericSpec = JsonUtils.toMap(spuDetail.getGenericSpec(), String.class, String.class);
//        //获取特有
//        Map<Long, List<String>> specialSpec = JsonUtils
//                .nativeRead( spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {});
//        //规格处理
//        Map<String, Object> specs = new HashMap<>();
//        for (SpecParam param : params) {
//            String key = param.getName();
//            Object value="";
//            if (param.getGeneric()) {
//                 value = genericSpec.get(param.getId());
//                if (param.getNumeric()) {
//                    value=chooseSegment(value.toString(),param);
//
//                }
//            }else {
//                 value = specialSpec.get(param.getId());
//            }
//            specs.put(key,value);
//        }
//
//
//        //构建Good对象
//        Goods goods = new Goods();
//        goods.setBrandId(spu.getBrandId());
//        goods.setCid1(spu.getCid1());
//        goods.setCid2(spu.getCid2());
//        goods.setCid3(spu.getCid3());
//        goods.setCreateTime(spu.getCreateTime());
//        goods.setId(spuId);
//        goods.setAll("");
//        goods.setPrice(priceList);
//        goods.setSkus(JsonUtils.toString(skus));
//        goods.setSpecs(specs);
//        goods.setSubTitle(spu.getSubTitle());
//        return goods;
//    }
//    private String chooseSegment(String value, SpecParam p) {
//        double val = NumberUtils.toDouble(value);
//        String result = "其它";
//        // 保存数值段
//        for (String segment : p.getSegments().split(",")) {
//            String[] segs = segment.split("-");
//            // 获取数值范围
//            double begin = NumberUtils.toDouble(segs[0]);
//            double end = Double.MAX_VALUE;
//            if (segs.length == 2) {
//                end = NumberUtils.toDouble(segs[1]);
//            }
//            // 判断是否在范围内
//            if (val >= begin && val < end) {
//                if (segs.length == 1) {
//                    result = segs[0] + p.getUnit() + "以上";
//                } else if (begin == 0) {
//                    result = segs[1] + p.getUnit() + "以下";
//                } else {
//                    result = segment + p.getUnit();
//                }
//                break;
//            }
//        }
//        return result;
//    }
}

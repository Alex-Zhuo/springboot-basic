package com.abab.common.fund;

import com.abab.common.util.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
public class FundTest {

    @Test
    public void searchFund() {

    }

    @Test
    public void getAllFund() throws Exception {
        String url = "http://fund.eastmoney.com/js/fundcode_search.js";
        String ret = HttpHelper.httpGet(url);
        //System.out.println(ret);
        String regex = "var r = (.*);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ret);
        if (matcher.find()) {
            String funds = matcher.group(1);
            if (StringUtils.isNoneBlank(funds)) {
                JSONArray fundArr = JSON.parseArray(funds);
                fundArr.stream().forEach(i -> {
                    JSONArray item = (JSONArray) i;
                    //System.out.println("简拼："+);
                    log.info("名称：{}，简拼：{}，全拼：{}，类型：{}", item.getString(2), item.getString(1), item.getString(4), item.getString(3));
                });
            }
            //System.out.println(ret);
        }
    }

    @Test
    public void getAllFundOrg() throws Exception {
        String url = "http://fund.eastmoney.com/js/jjjz_gs.js?dt=";
        //String ret = HttpHelper.httpGet(url + System.currentTimeMillis());
        String ret = "var gs={op:[[\"80163340\",\"安信基金\"],[\"80084302\",\"安信证券\"],[\"80538609\",\"渤海汇金\"],[\"80560392\",\"博道基金\"],[\"80365985\",\"北信瑞丰\"],[\"80000236\",\"宝盈基金\"],[\"80560400\",\"博远基金\"],[\"80000226\",\"博时基金\"],[\"80380794\",\"创金合信基金\"],[\"80000243\",\"长信基金\"],[\"80000239\",\"长城基金\"],[\"80000227\",\"长盛基金\"],[\"80965167\",\"淳厚基金\"],[\"80139382\",\"长安基金\"],[\"80404701\",\"财通资管\"],[\"80161341\",\"财通基金\"],[\"80404004\",\"长江证券(上海)\"],[\"80205268\",\"东海基金\"],[\"80042861\",\"东方基金\"],[\"80000225\",\"大成基金\"],[\"80048161\",\"东吴基金\"],[\"80114781\",\"东兴证券\"],[\"80145102\",\"东方红资产管理\"],[\"80701749\",\"达诚基金管理\"],[\"80560384\",\"东财基金\"],[\"80560388\",\"东方阿尔法基金\"],[\"80175511\",\"德邦基金\"],[\"80560383\",\"蜂巢基金\"],[\"80000221\",\"富国基金\"],[\"80488954\",\"富荣基金\"],[\"80128562\",\"富安达基金\"],[\"80174741\",\"方正富邦基金\"],[\"80000068\",\"方正证券\"],[\"80102419\",\"国金基金\"],[\"80000233\",\"国投瑞银基金\"],[\"80000224\",\"国泰基金\"],[\"80000248\",\"广发基金\"],[\"80357951\",\"广发资产管理\"],[\"80044515\",\"国海富兰克林基金\"],[\"80043374\",\"国联安基金\"],[\"80280039\",\"国开泰富基金\"],[\"80355783\",\"国寿安保基金\"],[\"80548351\",\"格林基金\"],[\"80064225\",\"工银瑞信基金\"],[\"80056613\",\"高华证券\"],[\"80156175\",\"国泰君安资产管理\"],[\"80048088\",\"光大保德信基金\"],[\"80000095\",\"国都证券\"],[\"80560389\",\"国融基金\"],[\"80199117\",\"华润元大基金\"],[\"80000246\",\"海富通基金\"],[\"80201857\",\"华宸未来基金\"],[\"80055334\",\"华泰柏瑞基金\"],[\"80037023\",\"华富基金\"],[\"80391977\",\"华泰证券(上海)\"],[\"80000222\",\"华夏基金\"],[\"80000064\",\"华安证券\"],[\"80067635\",\"汇丰晋信基金\"],[\"80053708\",\"汇添富基金\"],[\"80424273\",\"泓德基金\"],[\"80205263\",\"红塔红土\"],[\"80000228\",\"华安基金\"],[\"80000250\",\"华宝基金\"],[\"80053204\",\"华商基金\"],[\"80385906\",\"红土创新基金\"],[\"80092743\",\"华融证券\"],[\"80523667\",\"华泰保兴\"],[\"80498278\",\"汇安基金\"],[\"80508384\",\"恒生前海基金\"],[\"80560380\",\"恒越基金\"],[\"80560379\",\"弘毅远方基金\"],[\"80560396\",\"合煦智远基金\"],[\"80924817\",\"惠升基金\"],[\"80975669\",\"华融基金\"],[\"80000223\",\"嘉实基金\"],[\"80064562\",\"交银施罗德基金\"],[\"80065990\",\"建信基金\"],[\"80000251\",\"景顺长城基金\"],[\"80365987\",\"嘉合基金\"],[\"80205264\",\"江信基金\"],[\"80446423\",\"金信基金\"],[\"80384640\",\"九泰基金\"],[\"80000245\",\"金鹰基金\"],[\"80086876\",\"金元顺安基金\"],[\"80560381\",\"凯石基金\"],[\"80036797\",\"摩根士丹利华鑫基金\"],[\"80664536\",\"明亚基金\"],[\"80106677\",\"民生加银基金\"],[\"80068180\",\"诺德基金\"],[\"80049689\",\"诺安基金\"],[\"80555446\",\"南华基金\"],[\"80000220\",\"南方基金\"],[\"80092233\",\"农银汇理基金\"],[\"80000230\",\"鹏华基金\"],[\"80522693\",\"鹏扬基金\"],[\"80168726\",\"平安基金\"],[\"80091787\",\"浦银安盛基金\"],[\"80468996\",\"前海联合\"],[\"80280038\",\"前海开源基金\"],[\"80061431\",\"人保资产\"],[\"81046799\",\"瑞达基金管理\"],[\"80672691\",\"睿远基金\"],[\"80000231\",\"融通基金\"],[\"80045188\",\"申万菱信基金\"],[\"80050229\",\"上投摩根基金\"],[\"80366080\",\"上银基金\"],[\"80192219\",\"上海光大证券资产管理\"],[\"80404011\",\"申万宏源证券\"],[\"80184574\",\"上海海通证券资产管理\"],[\"80000080\",\"山西证券\"],[\"80294346\",\"太平基金\"],[\"80041198\",\"天弘基金\"],[\"80000238\",\"泰达宏利基金\"],[\"80061674\",\"泰康资产\"],[\"80560408\",\"同泰基金\"],[\"80050806\",\"太平洋\"],[\"80000124\",\"天风证券\"],[\"80000252\",\"天治基金\"],[\"80000247\",\"泰信基金\"],[\"80000240\",\"万家基金\"],[\"80351991\",\"鑫元基金\"],[\"80147736\",\"西部利得基金\"],[\"80036742\",\"兴证全球基金\"],[\"80074234\",\"信达澳银基金\"],[\"80000249\",\"新华基金\"],[\"81102343\",\"兴华基金管理\"],[\"80368700\",\"兴银基金\"],[\"80452130\",\"新沃基金\"],[\"80280395\",\"兴业基金\"],[\"80280397\",\"湘财基金\"],[\"80374411\",\"兴证资管\"],[\"80501166\",\"先锋基金\"],[\"80000235\",\"银华基金\"],[\"80175498\",\"英大基金\"],[\"80000229\",\"易方达基金\"],[\"80280036\",\"圆信永丰基金\"],[\"80046522\",\"益民基金\"],[\"80356155\",\"永赢基金\"],[\"80000237\",\"银河基金\"],[\"80046614\",\"中海基金\"],[\"80066058\",\"中信建投\"],[\"80065113\",\"中欧基金\"],[\"80156777\",\"浙商基金\"],[\"80066470\",\"中信保诚基金\"],[\"80341238\",\"中融基金\"],[\"80075936\",\"中邮基金\"],[\"80351345\",\"中加基金\"],[\"80048752\",\"中银基金\"],[\"80355113\",\"中信建投基金\"],[\"80000200\",\"中银证券\"],[\"80365986\",\"中金基金\"],[\"80381452\",\"中泰证券(上海)资管\"],[\"80455765\",\"中科沃土基金\"],[\"10000018\",\"中信证券\"],[\"80508391\",\"中航基金\"],[\"80403111\",\"浙商证券资管\"],[\"80560391\",\"中庚基金\"],[\"80431710\",\"招商证券资管\"],[\"80016241\",\"中金公司\"],[\"80560407\",\"朱雀基金\"],[\"80036782\",\"招商基金\"]]}";
        String regex = "var gs=\\{op:(.*)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ret);
        if (matcher.find()) {
            String orgs = matcher.group(1);
            if (StringUtils.isNoneBlank(orgs)) {
                JSONArray orgArr = JSON.parseArray(orgs);
                orgArr.stream();
            }
        }
        System.out.println(ret);
    }

    @Test
    public void getFunds() throws Exception {
        Integer page = 1;
        Integer pageCount = 50;
        //String url = "http://fund.10jqka.com.cn/data/market/jjsx/?count=50&key1=nowyear&sort=desc&zkl=0&page=1";
        //String url = "http://fund.10jqka.com.cn/data/market/jjsx/?type[]=gpx&orgid[]=T000021466&fg[]=phx&net[]=1to2&count=50&key1=nowyear&sort=desc&zkl=-51&page=1";
        String url = "http://fund.10jqka.com.cn/data/market/jjsx/?count=" + pageCount + "&page=" + page;
        String ret = HttpHelper.httpGet(url);
        if (StringUtils.isNoneBlank(ret)) {
            JSONObject retJson = JSON.parseObject(ret);
            JSONObject data = retJson.getJSONObject("data");
            data.forEach((key, value) -> {
                JSONObject detail = (JSONObject) value;
                Map<String, Object> detailMap = new LinkedHashMap<String, Object>() {{
                    put("基金代码", detail.get("code"));
                    put("基金名称", detail.get("name"));
                    put("基金类型", detail.get("saletype"));
                    put("基金规模（亿元）", detail.get("scale"));
                    put("基金公司", detail.get("orgname"));
                    put("基金经理", detail.get("manager"));
                    put("风险等级", detail.get("fxdj"));
                    put("投资风格", detail.get("tzfg"));

                    put("买入费率（%）", detail.get("buy"));
                    put("认购费率（%）", detail.get("rgfl"));
                    put("认购起始日期", detail.get("rgStart"));
                    put("认购截止日期", detail.get("rgEnd"));
                    put("累计净值", detail.get("totalnet"));

                    put("最新净值时间", detail.get("updatetime"));
                    put("最新净值涨幅（%）", detail.get("prerate"));
                    put("最新净值", detail.get("net"));

                    put("本周涨幅（%）", detail.get("nowweek"));
                    put("今年来涨幅（%）", detail.get("nowyear"));

                    put("近一周涨幅（%）", detail.get("week"));
                    put("近一月涨幅（%）", detail.get("month"));
                    put("近三月涨幅（%）", detail.get("tmonth"));
                    put("近半年涨幅", detail.get("hyear"));
                    put("近一年涨幅（%）", detail.get("year"));
                    put("近两年涨幅（%）", detail.get("twoyear"));
                    put("近三年涨幅（%）", detail.get("tyear"));
                }};
                //log.info("基金代码：{}，近一周涨幅：{}，近一年涨幅：{}，", detail.get("code"), detail.get("week"), detail.get("year"));
                System.out.println(detailMap);
            });
        }
        //System.out.println(ret);
    }

    @Test
    public void getFundDetail() {
        //http://fund.10jqka.com.cn/data/client/myfund/161725
        //http://fund.10jqka.com.cn/ifindRank/quarter_year_161725.json
    }
}

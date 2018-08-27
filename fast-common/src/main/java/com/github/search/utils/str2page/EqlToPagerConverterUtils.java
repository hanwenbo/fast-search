package com.github.search.utils.str2page;

import com.github.search.enums.AndOrEnums;
import com.github.search.enums.NotFlagEnum;
import com.github.search.page.BoolPager;
import com.github.search.pub.ValueEntity;
import com.github.search.pub.ValuePackage;
import com.github.search.pub.str2page.Position;
import com.github.search.pub.str2page.StrContent;
import com.github.search.pub.str2page.VPEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhubo
 * @description:
 * @time: 2018年06月22日
 * @modifytime:
 */
public class EqlToPagerConverterUtils {

	/**
	 * 元数据
	 */
	private static final char META_DATE_PREFIX = 'M';
	private static final String TWO_LEVEL_PREFIX = "T";
	private static final char FULL_BLANK = '$';
	private static final String FULL_BLANK_STR = "$";


	private static final String META_PATTERN = "\\(([^\\(\\)]*)\\)";
	private static final String TWO_LEVEL_PATTERN = "\\(([^\\(\\)]+)\\)";
	private static final String LOGIC_CLOSE_META_PATTERN = "(\\&|\\|)?(!)?\\(?([^\\(\\|\\&\\!\\)]+)\\)?";
	private static final String LOGIC_FINAL_META_PATTERN = "(\\&?|\\|?)(!{0,1})([^\\&\\|!]+)";


	// 初始化元数据
	static {

	}


	public static void main(String[] args) {
		//String str = "&!(|(a:1|2|3)|(a<=10|a>=100)|!(a:{wildcard:W?F*HW}))|(|(a:1)|(b:2))";
		//String str = "!(attrIds:2232012366099328|189)|(attrIds:2292774003989889|2300097498406272)";
		// String str = "&!(|(a:1|2|3)|(a<=10|a>=100)|!(a:{wildcard:W?F*HW}))|(|(a:1)|(b:2))&(c:1000)";

		String str = "|(attrIds:2232012366099328|189)|(attrIds:2292774003989889|2300097498406272)";
		BoolPager boolPager = convertToPager(str);
		System.out.println(boolPager);


        /*String s = "(|M4|M5)|M6";
        List<VPEntity> vpEntities = logicConvert(s);
        System.out.println(vpEntities);*/

	}


	/**
	 * "&!(&(supplierId:2241527253818753|2233001659489665)&(goodsStorage>1000))&(marketPrice>1000)&!(&(supplierId:2241527253818753|2233001659489665)&(goodsStorage>1000))"
	 *
	 * @param eql
	 * @return
	 */
	public static BoolPager convertToPager(String eql) {
		List<ValuePackage> query = new ArrayList<>();
		if (StringUtils.isNotBlank(eql)) {
			// 第一步 穷举元符号
			StrContent strContent = metaDataConvert(META_PATTERN, eql);
			Map<String, Position> keyMatcher = strContent.getKeyMatcher();
			String convertStr = strContent.getConvertStr();

			StrContent content = convertTowLevelContent(convertStr);
			List<VPEntity> vpEntities = logicConvert(content.getConvertStr());

			for (VPEntity ve : vpEntities) {
				ValuePackage vp = new ValuePackage();
				List<ValueEntity> list = convStrToListValueEntity(content.getKeyMatcher().get(ve.getEntitys()).getStr(), keyMatcher);
				if (list == null || list.size() == 0) {
					continue;
				}
				vp.setNotFlag(ve.getNotFlag());
				vp.setAndOrFlag(ve.getAndOrFlag());
				vp.setEntitys(list);
				query.add(vp);
			}
		}
		BoolPager pager = new BoolPager();
		pager.setQuery(query);
		return pager;
	}


	/**
	 * @param str &!T1&T3&!T2
	 * @return
	 */
	public static List<VPEntity> logicConvert(String str) {
		List<VPEntity> vps = new ArrayList<>();
		Pattern p = Pattern.compile(LOGIC_CLOSE_META_PATTERN);
		Matcher matcher = p.matcher(str);
		while (matcher.find()) {
			VPEntity vpEntity = convMatcherToVp(matcher);
			if (vpEntity != null && StringUtils.isNotBlank(vpEntity.getEntitys())) {
				vps.add(vpEntity);
			}
		}
		return vps;
	}


	private static VPEntity convMatcherToVp(Matcher matcher) {
		VPEntity vpe = new VPEntity();
		if (StringUtils.isNotBlank(matcher.group(1))) {
			if ("|".equals(matcher.group(1).trim())) {
				vpe.setAndOrFlag(AndOrEnums.OR.getCode());
			}
		}
		if (StringUtils.isNotBlank(matcher.group(2))) {
			if ("!".equals(matcher.group(2).trim())) {
				vpe.setNotFlag(NotFlagEnum.NEGATIVE.getCode());
			}
		}
		if (StringUtils.isNotBlank(matcher.group(3))) {
			vpe.setEntitys(matcher.group(3));
			return vpe;
		} else {
			return null;
		}
	}


	/**
	 * @param str &!(&M1&M2)&M3&!(&M4&M5)
	 * @return &!T1&T3&!T2
	 */
	private static StrContent convertTowLevelContent(String str) {
		StrContent content = new StrContent();
		content.setOriginalStr(str);
		Map<String, Position> keyMatcher = new HashMap<>();
		content.setKeyMatcher(keyMatcher);
		int index = 1;

		Pattern p = Pattern.compile(TWO_LEVEL_PATTERN);
		Matcher matcher = p.matcher(str);

		String s = str;
		while (matcher.find()) {

			int j = index++;
			String rs = TWO_LEVEL_PREFIX + j;
			String replaceStr = matcher.group(1);
			Position position = new Position(0, 0, replaceStr);

			Pattern ps = Pattern.compile("\\(" + replaceStr + "\\)");
			Matcher pm = ps.matcher(s);
			s = pm.replaceFirst(rs);
			keyMatcher.put(rs, position);
		}

		Pattern p2 = Pattern.compile("(M[0-9]+)");
		Matcher m2 = p2.matcher(s);
		while (m2.find()) {
			String group = m2.group(1);
			int j = index++;
			String rs = TWO_LEVEL_PREFIX + j;
			keyMatcher.put(rs, new Position(0, 0, group));
			Pattern ps = Pattern.compile(group);
			Matcher pm = ps.matcher(s);
			s = pm.replaceFirst(rs);
		}
		content.setConvertStr(s);
		return content;
	}


	/**
	 * '|M1|M2|!M3'
	 *
	 * @param ves
	 * @return
	 */
	private static List<ValueEntity> convStrToListValueEntity(String ves, Map<String, Position> keyMatcher) {
		List<ValueEntity> list = new ArrayList<>();
		Pattern p = Pattern.compile(LOGIC_FINAL_META_PATTERN);
		Matcher matcher = p.matcher(ves);

		int notFlag = NotFlagEnum.POSITIVE.getCode();   // 默认为正向查询
		int andOrFlag = AndOrEnums.AND.getCode();       // 默认为 & 查询


		while (matcher.find()) {
			if (StringUtils.isNotBlank(matcher.group(1))) {
				if ("|".equals(matcher.group(1))) {
					andOrFlag = AndOrEnums.OR.getCode();
				}
			}

			if (StringUtils.isNotBlank(matcher.group(2))) {
				if ("!".equals(matcher.group(2))) {
					notFlag = NotFlagEnum.NEGATIVE.getCode();
				}
			}
			String key = matcher.group(3);
			if (StringUtils.isNotBlank(key)) {
				Position position = keyMatcher.get(key);
				if (position != null) {
					String str = position.getStr();
					if (StringUtils.isNotBlank(str)) {
						ValueEntity ve = MetaConvertUtils.convertEntity(notFlag, andOrFlag, str);
						if (ve != null) {
							list.add(ve);
						}
					}
				}
			}
		}
		return list;
	}


	/**
	 * @param pattern
	 * @param line    &!(&(supplierId:2241527253818753|2233001659489665)&(goodsStorage>1000))&(marketPrice>1000)&!(&(supplierId:2241527253818753|2233001659489665)&(goodsStorage>1000))
	 * @return &!(&M1&M2)&M3&!(&M4&M5)
	 */
	public static StrContent metaDataConvert(String pattern, String line) {
		StrContent content = new StrContent();
		content.setOriginalStr(line);
		Map<String, Position> keyMatcher = new HashMap<>();

		content.setKeyMatcher(keyMatcher);
		Integer index = 1;
		char[] chars = line.toCharArray();
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(line);
		while (matcher.find()) {
			keyMatcher.put(META_DATE_PREFIX + index.toString(), new Position(matcher.start(), matcher.end(), matcher.group(1)));
			char[] indices = index.toString().toCharArray();
			for (int i = matcher.start(); i < matcher.end(); i++) {
				if (i == matcher.start()) {
					chars[i] = META_DATE_PREFIX;
					continue;
				}
				if (indices.length >= i - matcher.start()) {
					chars[i] = indices[i - matcher.start() - 1];
				} else {
					chars[i] = FULL_BLANK;
				}
			}
			index++;
		}
		String s = String.valueOf(chars);
		s = StringUtils.replace(s, FULL_BLANK_STR, "");
		s = StringUtils.replace(s, " ", "");
		content.setConvertStr(s);
		return content;
	}

}

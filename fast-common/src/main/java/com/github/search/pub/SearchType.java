package com.github.search.pub;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: zhubo
 * Date: 2017-09-28
 * Time: 17:19
 */
public class SearchType {

	/**
	 * 精确查询
	 */
	public static final int TERM_QUERY = 1;
	/**
	 * 匹配查询
	 */
	public static final int MATCH_QUERY = 2;
	/**
	 * 前缀查询
	 */
	public static final int PREFIX_QUERY = 3;
	/**
	 * 通配符查询
	 */
	public static final int WILDCARD_QUERY = 4;
	/**
	 * 正则表达式查询
	 */
	public static final int REGEXP_QUERY = 5;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 查询规则：[a,b]
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY = 11;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 查询规则：(a,b)
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_OPEN = 12;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 查询规则：(a,b]
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_CLOSE = 13;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss
	 * <p>
	 * 查询规则：[a,b)
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE_RIGHT_OPEN = 14;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss field >= DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE
	 * <p>
	 * 查询规则：[a,+∞)
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_LEFT_CLOSE = 15;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss field > DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN
	 * <p>
	 * 查询规则：(a,+∞)
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_LEFT_OPEN = 16;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss field <= DATE_STRING_RANGE_INNER_QUERY_RIGHT_CLOSE
	 * <p>
	 * 查询规则：(-∞,b]
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_RIGHT_CLOSE = 17;
	/**
	 * 时间范围查询 yyyy-MM-dd HH:mm:ss field < DATE_STRING_RANGE_INNER_QUERY_RIGHT_OPEN
	 * <p>
	 * 查询规则：(-∞,b)
	 */
	public static final int DATE_STRING_RANGE_INNER_QUERY_RIGHT_OPEN = 18;
	/**
	 * 时间范围查询 java.util.Date
	 * <p>
	 * 查询规则：[a,b]
	 */
	public static final int DATE_OBJ_RANGE_INNER_QUERY = 19;
	/**
	 * 时间范围查询 java.util.Date
	 * <p>
	 * 查询规则：(a,b)
	 */
	public static final int DATE_OBJ_RANGE_QUERY_LEFT_OPEN_RIGHT_OPEN = 20;
	/**
	 * 时间范围查询 java.util.Date field >= DATE_OBJ_RANGE_INNER_QUERY_LEFT_CLOSE
	 * <p>
	 * 查询规则：[a,+∞)
	 */
	public static final int DATE_OBJ_RANGE_INNER_QUERY_LEFT_CLOSE = 21;
	/**
	 * 时间范围查询 java.util.Date field > DATE_OBJ_RANGE_INNER_QUERY_LEFT_OPEN
	 * <p>
	 * 查询规则：(a,+∞)
	 */
	public static final int DATE_OBJ_RANGE_INNER_QUERY_LEFT_OPEN = 22;
	/**
	 * 时间范围查询 java.util.Date field <= DATE_OBJ_RANGE_INNER_QUERY_RIGHT_CLOSE
	 * <p>
	 * 查询规则：(-∞,b]
	 */
	public static final int DATE_OBJ_RANGE_INNER_QUERY_RIGHT_CLOSE = 23;
	/**
	 * 时间范围查询 java.util.Date field < DATE_OBJ_RANGE_INNER_QUERY_RIGHT_OPEN
	 * <p>
	 * 查询规则：(-∞,b)
	 */
	public static final int DATE_OBJ_RANGE_INNER_QUERY_RIGHT_OPEN = 24;
	/**
	 * a:"yyyy-MM-dd HH:mm:ss"
	 * b:"yyyy-MM-dd HH:mm:ss"
	 * a < b
	 * <p>
	 * 日期范围查询  field <= a || field >= b
	 * <p>
	 * 查询规则：(-∞,a] || [a,+∞)
	 */
	public static final int DATE_STRING_RANGE_OUTER_QUERY = 25;
	/**
	 * a:"yyyy-MM-dd HH:mm:ss"
	 * b:"yyyy-MM-dd HH:mm:ss"
	 * a < b
	 * <p>
	 * 日期范围查询  field < a || field > b
	 * <p>
	 * 查询规则：(-∞,a) || (a,+∞)
	 */
	public static final int DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN = 26;
	/**
	 * a:"yyyy-MM-dd HH:mm:ss"
	 * b:"yyyy-MM-dd HH:mm:ss"
	 * a < b
	 * 日期范围查询  field < a || field >= b
	 * <p>
	 * 查询规则：(-∞,a) || [b,+∞)
	 */
	public static final int DATE_STRING_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE = 27;
	/**
	 * a:"yyyy-MM-dd HH:mm:ss"
	 * b:"yyyy-MM-dd HH:mm:ss"
	 * a < b
	 * <p>
	 * 日期范围查询  field <= a || field > b
	 * <p>
	 * 查询规则：(-∞,a] || (b,+∞)
	 */
	public static final int DATE_STRING_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN = 28;
	/**
	 * 普通范围查询
	 * <p>
	 * 查询规则：[a,b]
	 */
	public static final int NUMBER_RANGE_INNER_QUERY = 31;
	/**
	 * 普通范围查询
	 * <p>
	 * 查询规则：(a,b)
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_OPEN = 32;
	/**
	 * 普通范围查询
	 * <p>
	 * 查询规则：[a,b)
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_LEFT_CLOSE_RIGHT_OPEN = 33;
	/**
	 * 普通范围查询
	 * <p>
	 * 查询规则：(a,b]
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_LEFT_OPEN_RIGHT_CLOSE = 34;
	/**
	 * 普通范围查询  field >= LEFT_RANGE_QUERY
	 * <p>
	 * 查询规则：[a,+∞)
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_LEFT_CLOSE = 35;
	/**
	 * 普通范围查询  field > NUMBER_RANGE_INNER_QUERY_LEFT_OPEN
	 * <p>
	 * 查询规则：(a,+∞)
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_LEFT_OPEN = 36;
	/**
	 * 普通范围查询  field <= NUMBER_RANGE_INNER_QUERY_RIGHT_CLOSE
	 * <p>
	 * 查询规则：(-∞,b]
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_RIGHT_CLOSE = 37;
	/**
	 * 普通范围查询  field < NUMBER_RANGE_INNER_QUERY_RIGHT_OPEN
	 * <p>
	 * 查询规则：(-∞,b)
	 */
	public static final int NUMBER_RANGE_INNER_QUERY_RIGHT_OPEN = 38;
	/**
	 * 普通范围查询  field <= a || field >= b
	 * <p>
	 * 查询规则：(-∞,a] || [a,+∞)
	 */
	public static final int NUMBER_RANGE_OUTER_QUERY = 39;
	/**
	 * 普通范围查询  field < a || field > b
	 * <p>
	 * 查询规则：(-∞,a) || (a,+∞)
	 */
	public static final int NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_OPEN = 40;
	/**
	 * 普通范围查询  field < a || field >= b
	 * <p>
	 * 查询规则：(-∞,a) || [a,+∞)
	 */
	public static final int NUMBER_RANGE_OUTER_QUERY_LEFT_OPEN_RIGHT_CLOSE = 41;
	/**
	 * 普通范围查询  field <= a || field > b
	 * <p>
	 * 查询规则：(-∞,a] || (a,+∞)
	 */
	public static final int NUMBER_RANGE_OUTER_QUERY_LEFT_CLOSE_RIGHT_OPEN = 42;


}

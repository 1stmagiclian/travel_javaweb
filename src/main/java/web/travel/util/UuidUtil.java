package web.travel.util;

import java.util.UUID;

/**
 * 产生UUID随机字符串工具类
 * 封装Java的UuidUtil工具类来生成全球唯一的字符串
 */
public final class UuidUtil {
	private UuidUtil(){}

	public static String getUuid(){
		return UUID.randomUUID()
				.toString().replace("-","");
	}
}

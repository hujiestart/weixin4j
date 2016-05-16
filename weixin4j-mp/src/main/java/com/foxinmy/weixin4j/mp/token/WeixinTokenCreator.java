package com.foxinmy.weixin4j.mp.token;

import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.http.weixin.WeixinResponse;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.mp.type.URLConsts;
import com.foxinmy.weixin4j.token.AbstractTokenCreator;

/**
 * 微信公众平台TOKEN创建者
 * 
 * @className WeixinTokenCreator
 * @author jy
 * @date 2015年1月10日
 * @since JDK 1.6
 * @see <a
 *      href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN">微信公众平台获取token说明</a>
 * @see com.foxinmy.weixin4j.model.Token
 */
public class WeixinTokenCreator extends AbstractTokenCreator {

	private final String appid;
	private final String secret;

	/**
	 * 
	 * @param appid
	 *            公众号ID
	 * @param secret
	 *            公众号secret
	 */
	public WeixinTokenCreator(String appid, String secret) {
		this.appid = appid;
		this.secret = secret;
	}

	@Override
	public String getCacheKey0() {
		return String.format("mp_token_%s", appid);
	}

	@Override
	public Token createToken() throws WeixinException {
		String tokenUrl = String.format(URLConsts.ASSESS_TOKEN_URL, appid,
				secret);
		WeixinResponse response = weixinExecutor.get(tokenUrl);
		Token token = response.getAsObject(new TypeReference<Token>() {
		});
		token.setCreateTime(System.currentTimeMillis());
		token.setOriginalResult(response.getAsString());
		return token;
	}
}

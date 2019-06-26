package com.boc.hopeheatapp.setting;

import android.content.Context;

/**
 * ISettings的工厂类，避免外部直接依赖他的实现类
 * @author fpliu@iflytek.com 2014-2-11
 *
 */
public final class SettingsFactory {
	
	private SettingsFactory() {}

	public static ISettings newInstance(Context context, String name) {
		return new SettingsImpl(context, name);
	}
}

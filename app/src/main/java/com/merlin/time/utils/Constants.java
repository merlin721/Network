package com.merlin.time.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class Constants {
	// APP_ID 替换为你的应用从官方网站申请到的合法appID
	public static final String APP_ID = "wx93dcff7f0e81e7b5";
    
    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}

	//定义并配置gson
	public static final Gson gson = new GsonBuilder()//建造者模式设置不同的配置
			.serializeNulls()//序列化为null对象
			.disableHtmlEscaping()//防止对网址乱码 忽略对特殊字符的转换
			.registerTypeAdapter(String.class, new StringConverter())//对为null的字段进行转换
			.create();
	/**
	 * 实现了 序列化 接口    对为null的字段进行转换
	 */
	public static class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
		//字符串为null 转换成"",否则为字符串类型
		@Override
		public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return json.getAsJsonPrimitive().getAsString();
		}

		@Override
		public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null || src.equals("null") ? new JsonPrimitive("") : new JsonPrimitive(src.toString());
		}
	}

}

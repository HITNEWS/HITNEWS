package com.HITIIDX.netservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import java.util.regex.*;

import android.annotation.SuppressLint;
import com.HITIIDX.domain.News;
import com.HITIIDX.newssearch.Setting;
import com.HITIIDX.domain.Newsshow;

public class GetNewsService extends NetService {

	/**
	 * 通过page返回新闻列表集合
	 * 
	 * @param newsName
	 *            搜索新闻名称
	 * @param page
	 *            搜索新闻页码
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static List<News> getNewsByPage(int page) {/* List可以添加任何对象 */

		JSONObject[] jsonObjects = getJsonObjectsByUrl("http://news.hititp.com/index.php/index/index/show?nextrow="
				+ page);
		List<News> newss = new ArrayList<News>();
		//int def=1;
		try {
			Newsshow show = new Newsshow();
			if (jsonObjects != null && jsonObjects.length > 0) {
				for (JSONObject jsonObject : jsonObjects) {
					show.setNewsshow(0);
					for (int i = 0; i < 8; i++) {
						if (Setting.checkboxes[i] == 1) {
						//def=0;
							Pattern pattern = Pattern
									.compile(Setting.values[i]);
							String s = jsonObject.getString("var1");
							Matcher m = pattern.matcher(s);
							if (m.find())
								show.setNewsshow(1);
						}
					}
					//if (def==1) {
				//		newsshow = 1;
					//}
					if (show.getNewsshow()==1) {
						News news = new News();
						news.setTitle(jsonObject.getString("title"));
						news.setSource(jsonObject.getString("var1"));
						news.setUrl(jsonObject.getString("url"));
						news.setPhotoUrl(jsonObject.getString("title"));
						news.setDate(jsonObject.getString("pubtime"));
						newss.add(news);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newss;
	}
}

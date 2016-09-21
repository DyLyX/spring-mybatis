package com.cn.dyl.quartz;

import static com.google.common.collect.Maps.newHashMap;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.cn.dyl.parser.wangyiyun.entity.MusicList;
import com.cn.dyl.parser.wangyiyun.musicListParser.MusicListParser;
import com.cn.dyl.pojo.Music;
import com.cn.dyl.util.DownLoadUtil;
import com.cn.dyl.util.ESManager;
import com.dyl.es.sample.IndexType;
/**
 * 只要把spring的配置文件加载了就可以看到定时任务运行了
 * 启动你的应用即可，即将工程部署至tomcat或其他容器
 * @author DengYinLei
 * @date   2016年8月27日 下午9:42:07
 */
@Component(value="musicCollectQuartzJob")
public class MusicCollectQuartzJob {
	private static final Logger logger=LoggerFactory.getLogger(MusicCollectQuartzJob.class);
	@Autowired
	private MusicListParser musicListParser;
	@Autowired
	private DownLoadUtil downLoadUtil;
	@Autowired
	@Qualifier("ESManager")
	private ESManager ESManager;
	public void work(){
		try {
			logger.info("处理爬取歌单列表任务开始>........");
			//业务逻辑代码
			doCollectMusic();
			logger.info("处理任务结束>........");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("处理任务出现异常",e);
		}
		
	}
	
	private void doCollectMusic(){
		String url="http://music.163.com/discover/playlist";
		HttpGet get=new HttpGet(url);
		get.setHeader("Cookie", "appver=1.5.0.75771");
		get.setHeader("Referer", "http://music.163.com/");
		List<MusicList> musicLists=new ArrayList<MusicList>();
		while(!"".equals(url)){
			String htmlString = downLoadUtil.getByCallBackGet(get);
			List<MusicList> musicLists1=musicListParser.getMusiclist(url,htmlString);
			musicLists.addAll(musicLists1);
			url=musicListParser.getNextPageUrl(url, htmlString);
			System.out.println(url);
		}
		for(MusicList ml:musicLists){
			//取出每个歌单的所有歌曲，后续把歌曲信息存储到es中
			List<Music> musics=musicListParser.getMusic(ml);
			for(Music music:musics){
				Map<String,Object> source=newHashMap();
				//根据歌曲id与歌单名生成唯一keyID
				String keyId=DigestUtils.md5Hex(music.getId()+music.getMusicListName());
				Map<String, Object> musicEs=ESManager.findDataByKeyId(IndexType.musicInfo, keyId);
				if(musicEs==null){
					continue;
				}
				source.put("keyId", keyId);
				source.put("musicId", music.getId());
				source.put("musicName", music.getName());
				source.put("musicListName", music.getMusicListName());
				source.put("musicListUrl", music.getMusicListName());
				source.put("musicListId", music.getMusicListId());
				source.put("musicfileSize", music.getMusicSize());
				source.put("artistsName", music.getArtistsName());
				source.put("artistId", music.getArtistId());
				source.put("albumName", music.getAlbumName());
				source.put("popularity", music.getPopularity());
				source.put("mp3Url", music.getMp3Url());
				source.put("mvUrl", music.getMvUrl());
				source.put("introUrl", music.getIntroUrl());
				source.put("duration", music.getDuration());
				source.put("lyrics", music.getLyrics());
				source.put("publishDate", music.getPublishDate());
				source.put("lyrics", music.getLyrics());
				source.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				ESManager.insertOrUpdate(IndexType.musicInfo, keyId, source);
			}
		}
	}
	
}

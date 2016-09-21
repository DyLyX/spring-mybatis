package com.cn.dyl.parser.wangyiyun.musicListParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.mapper.JsonMapper;

import com.cn.dyl.commom.Constant;
import com.cn.dyl.parser.wangyiyun.entity.LyricMusic;
import com.cn.dyl.parser.wangyiyun.entity.MusicDetial;
import com.cn.dyl.parser.wangyiyun.entity.MusicList;
import com.cn.dyl.parser.wangyiyun.entity.MusicListBody;
import com.cn.dyl.parser.wangyiyun.entity.MusicListDetail;
import com.cn.dyl.parser.wangyiyun.entity.Singer;
import com.cn.dyl.parser.wangyiyun.entity.searchEntity.SearchBody;
import com.cn.dyl.parser.wangyiyun.entity.searchEntity.Song;
import com.cn.dyl.pojo.Music;
import com.cn.dyl.service.SingerService;
import com.cn.dyl.util.DownLoadUtil;
import com.cn.dyl.util.ExtractUtil;

@Component
public class MusicListParser {
	private static final Logger logger = LoggerFactory.getLogger(MusicListParser.class);
	private static JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();
	private static final int PLAY_COUNTS = 100;
	@Autowired
	private DownLoadUtil downLoadUtil;
	@Autowired
	private SingerService singerService;
	/**
	 *  得到下一页
	 * @param url
	 * @param urlContent
	 * @return
	 */
	public String getNextPageUrl(String url, String urlContent) {
		String nextUrl = "";
		Element body = Jsoup.parse(urlContent, url);
		Element nextEle = body.select("a:contains(下一页)").first();
		if (nextEle != null && !"javascript:void(0)".equals(nextEle.absUrl("href"))) {
			nextUrl = nextEle.absUrl("href");
		}
		return nextUrl;
	}

	// 得到收听次数大于100万的歌单
	/**
	 * url:歌单入口url 
	 * content:htmlString
	 * @param
	 * @param
	 * @return
	 */
	public List<MusicList> getMusiclist(String url, String urlContent) {
		List<MusicList> musicLists = Lists.newArrayList();
		Document document = Jsoup.parse(urlContent, url);
		Element body = document.body();
		if (body == null) {
			return null;
		}
		Elements lis = body.select("ul[class=m-cvrlst f-cb]>li");
		if (lis != null && lis.size() > 0) {
			for (Element e : lis) {
				MusicList list = new MusicList();
				// 先获取收听次数
				Element playCountsEle = e.select(" span[class=nb]").first();
				if (playCountsEle != null) {
					String playCounts = playCountsEle.text().trim();
					String playtimes = ExtractUtil.getFirstContent(playCounts, "(\\d+)万", 1);
					if (!"".equals(playtimes) && Integer.parseInt(playtimes) >= PLAY_COUNTS) {
						list.setPlayTimes(playCounts);
					} else {
						continue;
					}
				}
				//作者
				Element authorEle = e.select(" a[class=nm nm-icn f-thide s-fc3]").first();
				if (authorEle != null) {
					list.setAuthor(authorEle.text().trim());
				}
				// 截图地址
				Element imageEle = e.select(" img[class=j-flag]").first();
				if (imageEle != null && StringUtils.isNotBlank(imageEle.attr("src"))) {
					list.setImageUrl(imageEle.attr("src"));
				}
				Element titleEle = e.select(" a[class=msk]").first();
				// 名称
				if (titleEle != null) {
					String title = titleEle.attr("title");
					list.setName(title);
					String introUrl = titleEle.absUrl("href");
					list.setIntroUrl(introUrl);
					String introId = ExtractUtil.getFirstContent(introUrl, "id=(\\d+)", 1);
					list.setId(Long.parseLong(introId));
				}
				musicLists.add(list);
			}
		}
		return musicLists;
	}
	/**
	 * 根据歌单获取歌曲详细信息
	 * @param musicList
	 * @return
	 */
	public List<Music> getMusic(MusicList musicList){
		List<Music> musics=new ArrayList<Music>();
		//获取歌单id
		long musicListId=musicList.getId();
		if(musicListId==0)
		{
			return null;
		}
		String url="http://music.163.com/api/playlist/detail?id="+musicListId+"&updateTime=-1";
		HttpGet get=new HttpGet(url);
		get.setHeader("Cookie", "appver=1.5.0.75771");
		get.setHeader("Referer", "http://music.163.com/");
		String jsonString=downLoadUtil.getByCallBackGet(get);
		//把json串反序列化成对象
		MusicListBody listBody=jsonMapper.fromJson(jsonString, MusicListBody.class);
		if(listBody==null){
			return null;
		}
		MusicListDetail listDetail=listBody.getResult();
		if(listDetail==null){
			return null;
		}
		//歌单名称
		String musicListName=listDetail.getName();
		//歌单id
		long musiclistId=listDetail.getId();
		
		//歌曲详情
		List<MusicDetial> musicDetials=listDetail.getTracks();
		if(musicDetials!=null&&musicDetials.size()>0){
			for(MusicDetial musicDetial:musicDetials){
				if (StringUtils.isBlank(musicDetial.getMp3Url())){
					return null;
				}
				Music music=new Music();
				music.setMusicListId(musiclistId);//歌单id
				music.setMusicListName(musicListName);//歌单名称
				String musicListUrl="http://music.163.com/playlist?id="+musiclistId;
				music.setMusicListUrl(musicListUrl);
				music.setName(musicDetial.getName());//歌曲名称
				music.setId(musicDetial.getId());//歌曲id
				music.setPopularity(musicDetial.getPopularity());//歌曲热度
				music.setDuration(musicDetial.getDuration());//播放时长
				MusicDetial.Artists artist=musicDetial.getArtists().get(0);
				if(artist!=null){
					String artistName=artist.getName();
					music.setArtistsName(artistName);//歌手名称
					music.setArtistId(artist.getId());//歌手id
					music.setMusicSize(artist.getMusicSize());//歌曲大小
				}
				MusicDetial.Album album=musicDetial.getAlbum();
				if(album!=null){
					music.setAlbumName(album.getName());//专辑名
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					music.setPublishDate(df.format(new Date(album.getPublishTime())));//发布时间
				}
				music.setMvid(musicDetial.getMvid());//mv地址id
				if(musicDetial.getMvid()!=0){
					String mvUrl="http://music.163.com/mv?id="+musicDetial.getMvid();
					music.setMvUrl(mvUrl);
				}
				music.setMp3Url(musicDetial.getMp3Url());//播放地址
				music.setScore(musicDetial.getScore());//评分
				//根据歌曲id获取歌词
				String lyricUrl="http://music.163.com/api/song/lyric?os=pc&id="+musicDetial.getId()+"&lv=-1&kv=-1&tv=-1";
				String introUrl="http://music.163.com/song?id="+musicDetial.getId();//歌曲详情url
				music.setIntroUrl(introUrl);
				String jsonlyric=downLoadUtil.getByCallBack(lyricUrl);
				LyricMusic lyricMusic=jsonMapper.fromJson(jsonlyric, LyricMusic.class);
				if(lyricMusic!=null){
					LyricMusic.Lrc lrc=lyricMusic.getLrc();
					if(lrc!=null){
						music.setLyrics(lrc.getLyric());//歌词
					}
					
				}
				
				musics.add(music);
			}
		}
		return musics;
		
	}
	/**
	 * 根据歌手id爬取歌手热门歌曲50首
	 * @return
	 */
	public List<Music> getMusicByArtists(){
		
		return null;
	}
	/**
	 * POST  http://music.163.com/api/search/pc
	 * @param postSearchBody
	 * @param type 搜索的类型  *歌曲 1  *专辑 10  *歌手 100 *歌单 1000  *用户 1002  *mv 1004  *歌词 1006  *主播电台 1009
	 * @param offset  偏移量（分页用）
	 * @param limit 获取的数量
	 * @return
	 */
	public List<Music> getMusicBySearch(String postSearchBody,int type,int offset,int limit){
		List<Music> musics=new ArrayList<Music>();
		String baseUri="http://music.163.com/api/search/pc";
		Map<String,Object> params=Maps.newHashMap();
		params.put("s", postSearchBody);
		params.put("offset", offset+"");
		params.put("limit", limit+"");
		params.put("type", type+"");
		// 创建httppsot
		HttpPost httpPost = new HttpPost(baseUri);
		// 创建参数队列
		List<NameValuePair> formparams = Lists.newArrayList();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, Object> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
			}
		}
		// 构建url加密实体，并以utf-8方式进行加密；
		UrlEncodedFormEntity uFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		httpPost.setEntity(uFormEntity);
		//设置头部
		httpPost.setHeader("Host", "music.163.com");
		httpPost.setHeader("Referer", "http://music.163.com/");
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//		httpPost.setHeader("Content-length",uFormEntity.getContentLength()+"");
		httpPost.setHeader("Cookie", "appver=1.5.0.75771");
		httpPost.setHeader("Connection", "close");
		String htmlStr=new DownLoadUtil().getByCallBackPost(httpPost);
		System.out.println(htmlStr);
		//把json串反序列化成对象
		SearchBody searchBody=jsonMapper.fromJson(htmlStr, SearchBody.class);
		List<Song> songs=searchBody.getResult().getSongs();
		for(Song s:songs){
			if (StringUtils.isBlank(s.getMp3Url())){
				return null;
			}
			Music music=new Music();
			music.setMusicListId(0);//歌单id
			music.setMusicListName(null);//歌单名称
			music.setMusicListUrl(null);
			music.setName(s.getName());//歌曲名称
			music.setId(s.getId());//歌曲id
			music.setPopularity(s.getPopularity());//歌曲热度
			music.setDuration(s.getDuration());//播放时长
			Song.Artists artist=s.getArtists().get(0);
			if(artist!=null){
				String artistName=artist.getName();
				music.setArtistsName(artistName);//歌手名称
				music.setArtistId(artist.getId());//歌手id
				music.setMusicSize(artist.getMusicSize());//歌曲大小
			}
			Song.Album album=s.getAlbum();
			if(album!=null){
				music.setAlbumName(album.getName());//专辑名
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				music.setPublishDate(df.format(new Date(album.getPublishTime())));//发布时间
			}
			music.setMvid(s.getMvid());//mv地址id
			if(s.getMvid()!=0){
				String mvUrl="http://music.163.com/mv?id="+s.getMvid();
				music.setMvUrl(mvUrl);
			}else{
				music.setMvUrl(null);
			}
			music.setMp3Url(s.getMp3Url());//播放地址
			music.setScore(s.getScore());//评分
			//根据歌曲id获取歌词
			String lyricUrl="http://music.163.com/api/song/lyric?os=pc&id="+s.getId()+"&lv=-1&kv=-1&tv=-1";
			String introUrl="http://music.163.com/song?id="+s.getId();//歌曲详情url
			music.setIntroUrl(introUrl);
			String jsonlyric=new DownLoadUtil().getByCallBack(lyricUrl);
			LyricMusic lyricMusic=jsonMapper.fromJson(jsonlyric, LyricMusic.class);
			if(lyricMusic!=null){
				LyricMusic.Lrc lrc=lyricMusic.getLrc();
				if(lrc!=null){
					music.setLyrics(lrc.getLyric());//歌词
				}
				
			}
			
			musics.add(music);
		
		}
		System.out.println(htmlStr);
		return musics;
	}
	
	/**
	 * 获取热门歌手，并把歌手信息存到数据库中
	 * @param url
	 * @param urlContent
	 * @return
	 */
	public List<Singer> getHotSinger(String url,String urlContent){
		int type = 0;
		if(Constant.HOT_SINGERMAN_URL.equals(url)){
			type = 1;
		}else if(Constant.HOT_SINGERWOMAN_URL.equals(url)){
			type = 2;
		}else if(Constant.HOT_SINGERTEAM_URL.equals(url)){
			type = 3;
		}
		List<Singer> singers =Lists.newArrayList();
		Document document = Jsoup.parse(urlContent, url);
		Element body = document.body();
		Elements lis=body.select("div.m-sgerlist>ul#m-artist-box>li");
		if(lis!=null&&lis.size()>0){
			for(Element li:lis){
				Singer s = new Singer();
				s.setType(type);
				Element img = li.select(" img").first();
				if(img!=null){
					s.setImageUrl(img.attr("src"));
				}
				Element p=li.select("p>a").first();
				if(p!=null){
					String purl=p.absUrl("href");
					if(StringUtils.isNotBlank(purl)){
						String singId =ExtractUtil.getFirstContent(purl, "/artist?id=(\\d+)", 1);
						if(!singId.isEmpty()){
							s.setSingId(Long.valueOf(singId));
						}
						
					}
				   String name = p.text().trim();
				   s.setName(name);
				}else {
					continue;
				}
				singerService.saveSinger(s);//把歌手信息存到数据库中
				singers.add(s);
			}
		}else {
			logger.error("没有歌手信息！可能入口有误");
			return null;
		}
		return singers;
	}
}

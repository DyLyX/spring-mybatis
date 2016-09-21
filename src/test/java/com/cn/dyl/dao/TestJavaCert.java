0package com.cn.dyl.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.Charsets;
import org.junit.Test;
import com.cn.dyl.pojo.Person;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;

public class TestJavaCert {

	@Test
	public void test() {
		//创建不可变的的列表与图
		ImmutableList<String> of =ImmutableList.of("a", "b", "c", "d");
		ImmutableMap<String,? extends Object> map = ImmutableMap.of("name", "dengyinlei", "age", 12);
		
		String s = "";  
		for (String p : of) {  
		    s += "," + p;  
		}  
		s = s.substring(1); //remove first comma  
		
		System.out.println(s);
		
		//较好的字符串连接方式
		StringBuilder sb = new StringBuilder(of.size() * 16); // well estimated buffer  
		System.out.println(sb.capacity());
		for (String p : of) {  
		    if (sb.length() > 0) sb.append(", ");  
		    sb.append(p);  
		} 
		System.out.println(sb);
		System.out.println(sb.length());
		
		
		//String  字符串中某个字符出现的次数
		String str = "sfsfluljslfnslafuensdfre";
		String s1="s";
		int count= str.length() - str.replace(s1,"").length();
		System.out.println(count);
		System.out.println(getClass().getResource(""));
		File file = new File(getClass().getResource("/test.txt").getFile());
		List<String> lines = null;
		try {
		
		  lines = Files.readLines(file, Charsets.UTF_8);
		
		} catch (IOException e) {
		
		  e.printStackTrace();
		
		}
		
		File file1 = new File(getClass().getResource("/test.txt").getFile());
		BufferedReader reader = null;
		StringBuilder text = new StringBuilder();
		try {
		    reader = new BufferedReader(new FileReader(file1));
		    String line = null;
		    while (true) {
		        line = reader.readLine();
		        if (line == null) {
		            break;
		        }
		        text.append(line.trim()).append("\n");
		    }
		    
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		finally {
			if(reader!=null)
				try {
					reader.close();
				    reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		//比较字符是否为空, 最好判断它的长度. 
		
		if ("John".equals(text)){}
		if (text.length() == 0){}
		String text1="";
		if (text1.isEmpty()){}
		//数字转换成字符串
		String.valueOf(of.size());
		int a=1;
		int b=2;
		int compare = Ints.compare(a, b);
		//把一个List转换为int数组：

		List<Integer> list = ImmutableList.of(1, 2, 3, 4);
		int[] array2 = Ints.toArray(list);
		
		//
		List<Integer> lists=Arrays.asList(1,2,3);
		
		//Google Collections提供了多种Multimaps的实现，如果你想防止出现键值对重复，可以用HashMultimap；
		//如果你需要键值对按照自然顺序排列，你可以使用TreeMultimap；甚至你想按插入顺序来遍历集合，LinkedHashMultimap可以满足你的需求。
		Multimap<Person, List<BlogPost>> multimap = ArrayListMultimap.create();
		Multimap<Person, List<BlogPost>> multimap1 = HashMultimap.create();
		 
		
	}
	public void addBlogPost(final Person author, final List<BlogPost> blogPost) {
		Multimap<Person, List<BlogPost>> multimap = ArrayListMultimap.create();
	    multimap.put(author,blogPost);
	}
	@Test
	public void TestMultimap(){
		List<Map<String, String>> listOfMaps=Lists.newArrayList();
	/*	我们假设我们已经拥有了包含了一组map的list。list里的每一个Map代表拥有指定属性的一个文档。这个Map看起来可能会是下面这个样子：
		mapOf("type", "blog", "id", "292", "author", "john");
		即每个Map中我们拥有3个属性，他们分别是“type”、 “id”和“author”。
		如上所示，所有我们的List看起来应该是下面这个样子：
		List<Map<String, String>> listOfMaps
		现在，我们想把这个list根据所装载对象的类型不同分成多个list，比如一个叫“blog”，一个叫“news”等等...
		如果没有Google Collections这将是一场恶梦！我们必须得先循环这个list，然后再分别检查每一个map中的key，然后再把根据类型的不同放入不同的list中。但如果我们不知道map里都有哪些类型，这个过程会更痛苦！
		想不想和我一起来看看有没有轻松的办法解决？
		用一点点Function的魔法加上Multimaps，我样可以以一种上相当优雅的方式来解决这个问题：
		*/
		
		Multimap<String, Map<String, String>> partitionedMap = Multimaps.index(listOfMaps, new Function<Map<String, String>, String>() {
			    public String apply(final Map<String, String> from) {
			        return from.get("type");
			    }
			});
		//现在我们拥有了每一个key代表不同类型的Multimaps了！
		//　　如果现在我们想要指定类型的所有map，唯一需要做的就是找Multimaps要！
	}
	
	@Test
	public void testGuvav(){
		//如果你想从字符串中得到所有的数字，那么你可以这样：
		String string = CharMatcher.DIGIT.retainFrom("some t111ext 89983 and more");
		System.out.println(string);
		
		//如果你想把字符串中的数据都去掉，可以如下：
		String string1 = CharMatcher.DIGIT.removeFrom("some t111ext 89983 and more");
		System.out.println(string1);
		
		
		String testString = "foo , what,,,more,";
		int[] numbers = { 1, 2, 3, 4, 5 };
		String numbersAsString = Joiner.on(";").join(Ints.asList(numbers));
		System.out.println(numbersAsString);
		
		Iterable<String> split = Splitter.on(",").omitEmptyStrings().trimResults().split(testString);
		System.out.println(split);
		for(String s:split){
			System.out.println(s);
		}
		Iterator<String> it=split.iterator();
		if(it.hasNext()){
			System.out.print(it.next());
		}
		
		
		int[] array = { 1, 2, 3, 4, 5 };
		int a = 4;
		boolean hasA = false;
		for (int i : array) {
		    if (i == a) {
		        hasA = true;
		    }
		}
		
		boolean contains = Ints.contains(array, a);
		List<Person> persons=Lists.newArrayList();
		
		//Google collections提供了Function接口，实际上，一个function就是从一个对象到另外一个对象的转换变形。
		
		//假如我们现在想现根据last name排序，再根据first name排序，然后对排序结果反序，那我们我们需要做的是：
		List<Person> sortedCopy = Ordering.from(byLastName).compound(byFirstName).reverse().sortedCopy(persons);
		
		List<String> names = ImmutableList.of("Aleksander", "Jaran", "Integrasco", "Guava", "Java");
		Collections.sort(persons, byFirstName);
		
	}
		//根据person的name属性排序按字母zyx....
		Comparator<Person> byLastName = new Comparator<Person>() {
		    public int compare(final Person p1, final Person p2) {
		        return p2.getName().compareTo(p1.getName());
		    }
		};
		 
		 //根据person的name属性排序按字母abcd....
		Comparator<Person> byFirstName = new Comparator<Person>() {
		    public int compare(final Person p1, final Person p2) {
		        return p1.getName().compareTo(p2.getName());
		    }
		};
}

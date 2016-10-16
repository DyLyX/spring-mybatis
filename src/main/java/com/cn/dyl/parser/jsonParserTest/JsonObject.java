package com.cn.dyl.parser.jsonParserTest;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * @JsonIgnoreProperties
         此注解是类注解，作用是json序列化时将Java bean中的一些属性忽略掉，序列化和反序列化都受影响。

@JsonIgnore
         此注解用于属性或者方法上（最好是属性上），作用和上面的@JsonIgnoreProperties一样。

@JsonFormat
        此注解用于属性或者方法上（最好是属性上），可以方便的把Date类型直接转化为我们想要的模式，比如@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

@JsonSerialize
        此注解用于属性或者getter方法上，用于在序列化时嵌入我们自定义的代码，比如序列化一个double时在其后面限制两位小数点。
        
@JsonDeserialize
         此注解用于属性或者setter方法上，用于在反序列化时可以嵌入我们自定义的代码，类似于上面的@JsonSerialize
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 
 * @author Denley
 * @date   2016年10月4日 下午5:32:51
 */
//表示序列化时忽略的属性  
@JsonIgnoreProperties(value = { "word" })  
public class JsonObject {

	private String name;
	private int age;
	private boolean sex;
	//GMT(Greenwich Mean Time)是格林尼治标准时间，+08:00 指标准时间加8小时，也就是北京时间。
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
	private Date birthday;
	private String word;
	private double salary;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	 // 反序列化一个固定格式的Date  
    @JsonDeserialize(using = CustomDateDeserialize.class)   //同 @JsonFormat@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	// 序列化指定格式的double格式  
    @JsonSerialize(using = CustomDoubleSerialize.class)  
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public  JsonObject(String name, int age, boolean sex, Date birthday,  
	            String word, double salary) {  
	        super();  
	        this.name = name;  
	        this.age = age;  
	        this.sex = sex;  
	        this.birthday = birthday;  
	        this.word = word;  
	        this.salary = salary;  
	    }  
	  
	    public JsonObject() {  
	    }  
	  
	    @Override  
	    public String toString() {  
	        return "Person [name=" + name + ", age=" + age + ", sex=" + sex  
	                + ", birthday=" + birthday + ", word=" + word + ", salary="  
	                + salary + "]";  
	    }  
}

// 2, 函数调用模式 
var userCheck = function(){
	
	var userCheckList_url = ctx + "/user/list";
	var userAdd_url = ctx + "/user/addUser";
	var userUpdate_url = ctx + "/user/update";
	var down_url = ctx + "/user/download";
	var search = function(page){
		var json = {};
		$("#q_currentPage").val(page);
		
		$("*[id*=q_]").each(function(){
			var key = this.id.split("_")[1];
			json[key] = this.value;
		});
		
		$.ajax({
			url: userCheckList_url,
			type: "post",
			data: json,
			dataType: "json",
			success : function(result){
				if(result && !result.state){
					if(result.message != ''){
						$('#msginfo').text(result.messge);
						$('#message').removeClass('hide');
					}
				}else{
					$("#paginationPanel").html('');
					var userList = result.data.data;
					var html = '';
				
					if(userList.length > 0){
						//添加分页
						buidlerPage(result.data.currentPage,result.data.pageCount);
						var user = null;
						for(var i=0; i<userList.length; i++){
							user = userList[i];
							//stringify()用于从一个对象解析出字符串，同时支持中文
							var userString=window.btoa(window.encodeURIComponent(JSON.stringify(user)));
							var sex = user.sex;
							html += '<tr>'
									+'<td style="text-align: center;">'+(i+1)+'</td>'
									+'<td style="text-align: center;" title="'+user.name+'"><a href=\"javascript:userCheck.updateUser(\''+userString+'\')\">'+user.name+'</td>'
									+'<td style="text-align: center;" title="'+user.nickname+'">'+user.nickname+'</td>'
									+'<td style="text-align: center;" title="'+user.email+'">'+user.email+'</td>'
//									if(user.fileName==null){
//										html +='无附件</td>';
//									}else{
//										html +='<a href="'+down_url+'?path='+user.attachment+'&name='+user.fileName+'">'+user.fileName+'</a></td>';
//									}
									html += '<td style="text-align: center;" title="'+new Date(user.createTime).format("yyyy-MM-dd HH:mm:ss")+'">'+new Date(user.createTime).format("yyyy-MM-dd HH:mm:ss")+'</td>';
									if(sex === 1){
										html += '<td style="text-align: center;">男</td>';
									}else{
										html += '<td style="text-align: center;">女</td>';
									}
							html += '</tr>'
						};
					}else{
						html += '<tr><td colspan="6"><div style="height:30px;">暂无用户数据O(∩_∩)O~</div></td></tr>';
					}
					
					$("#userList").html(html);
				};
			}
		});
	};
	/**
	 * 更新用户
	 */
	//只能接受字符串类型的
	var updateUser=function(userString){
		//一个字符串中解析出json对象
		//对于转码来说，Base64转码的对象只能是字符串，因此来说，对于其他数据还有这一定的局限性，在此特别需要注意的是对Unicode转码。
		//那么如何让他支持汉字呢，这就要使用window.encodeURIComponent和window.decodeURIComponent
		var userObject=JSON.parse(window.decodeURIComponent(window.atob(userString)));
		$('#userUpdateDialog').modal({
			backdrop:"static",
			keyboard: true,
			show: true
		});
		//可拖拽
		$("#userUpdateDialog").draggable({   
		    cursor: "move",   
		    refreshPositions: false  
		}); 
		$('#myModalLabel').html('{<span style="color:red;"> '+userObject.name+'</span> }更新个人信息');
		//模态框显示用户信息
		$('#updateUserForm').find('#u_sex').val(userObject.sex);
		$('#updateUserForm').find('#u_id').val(userObject.id);
		$('#updateUserForm').find('#u_createTime').val(new Date());
		$('#updateUserForm').find('#u_name').val(userObject.name);
		$('#updateUserForm').find('#u_nickname').val(userObject.nickname);
		$('#updateUserForm').find('#u_email').val(userObject.email);
		
	};
	var changeUser=function(){
		var form = $("#updateUserForm");
//		var json = {};
//		$("*[id*=u_]").each(function(){
//			var key = this.id.split("_")[1];
//			json[key] = this.value;
//		});
		var data = {
			    sex: form.find('#u_sex').val(),
			    id: form.find('#u_id').val(),
			    createTime: form.find('#u_createTime').val(),
			    name: form.find('#u_name').val(),
			    nickname: form.find('#u_nickname').val(),
			    email: form.find('#u_email').val()
			};
		$.ajax({
			url: userUpdate_url,
			type: "post",
			data: data,
			dataType: "json",
			success : function(result,status){
				alert(result);
				if(result=='success'){
        			$('#userUpdateDialog').modal('hide');
        			window.location.reload();
        			alert('更新用户操作成功！');
        		}else{
        			alert('更新用户失败！');
        		}
			},
			error: function (data, status, e) {  
	            alert(e);
	        }  
		});
		
	};
	
	
	/**
	 * 添加用户操作
	 */
	var openAddUser = function(obj) {
		$('#addUserDialog').modal({
			backdrop:"static",
		});
	};
	
	var addUserTask = function() {
		var form = $("#addUserForm");
//		var json = {};
		var data = {
			    sex: form.find('#uu_sex:checked').val(),
			    name: form.find('#uu_name').val(),
			    nickname: form.find('#uu_nickname').val(),
			    email: form.find('#uu_email').val()
			};
//		$("*[id*=uu_]").each(function(){
//			var key = this.id.split("_")[1];
//			//只去被选中的单选框
//			if(key=='sex' && $(this).attr('checked')){
//				json[key] = this.value;
//			}else if(key!='sex'){
//				json[key] = this.value;
//			}
//		});
		$.ajax({
			url: userAdd_url,
			type: "post",
			data: data,
			dataType: "json",
			success : function(result,status){
				if(result=='success'){
        			$('#addUserDialog').modal('hide');
        			window.location.reload();
        			alert('添加用户操作成功！');
        		}else{
        			alert('操作失败');
        		}
				
			},
		  error: function (data, status, e) {  
	            alert(e);
	        }  
		});
		
	
	};
	//自定义  对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	/**
	 * 文件下载
	 */
	var downLoad = function(id,index){
		window.location.href=downLoad_url + id + '/' + index;
	};
	/**
	 * 文件上传
	 */
	var labourCheck =function(){
		var form = $("#addUserForm");
		if(form.validate().form()){
			var url = this.ctx+this._url.labourCheck;
			var data = {
			    checkStatus: form.find('#checkStatus').val(),
			    appKeyId: form.find('#appKeyId').val(),
			    marketKeyId: form.find('#marketKeyId').val(),
				title: form.find('#title').val(),
				fileMD5: form.find('#fileMD5').val(),
				content: form.find('#content').val()
			};
			$.ajaxFileUpload({  
		        url: url,  
		        secureuri: false,  
		        fileElementId: 'file',
//		        dataType: 'json',
		        type: 'post',
		        data: data,
		        success: function (data, status) {
		        	var json = $(data).find('#mainBody').text();
		        	if(json){
		        		data = JSON.parse(json);
		        		if(data=='success'){
		        			$('#addUserDialog').modal('hide');
		        			window.location.reload();
		        			alert('操作成功！');
		        		}else{
		        			alert('操作失败，附件大小超过10M！');
		        		}
		        	}else{
		        		alert('操作失败，附件大小超过10M！');
		        	}
		        },  
		        error: function (data, status, e) {  
		            alert(e);
		        }  
		    });  
		}
	};
	
	/**
	 * 分页展示设置
	 */
	var buidlerPage = function(currentPage,pageCount){
		var options = {
				size:"small",
				bootstrapMajorVersion:3,
				currentPage: currentPage,
				totalPages: pageCount,
				numberOfPages:5,
				onPageClicked: function(e,originalEvent,type,page){
					userCheck.changePage(page);
	            }
			};
		$("#paginationPanel").bootstrapPaginator(options);;
	};
	//翻页
	var changePage = function(page){
		$("#q_currentPage").val(page);
		search(page);
	};
	
	var clearSearch = function(){
//		var attrName = '';
//		$("*[id*=q_]").each(function(){
//			attrName = this.id.split("_")[1];
//			if(attrName == "name"){
//				this.value = "";
//			}
//		});
//		search(1);
		window.location.reload();
	};
	
	/**
	 * 弹出信息框关闭
	 */
	var hideMsg = function() {
		$('#message').addClass('hide');
		$('#msginfo').text('');
	};
	
	return{
		search : function(page){
			search(page);
		},
		buidlerPage : function(currentPage,pageCount){
			buidlerPage(currentPage,pageCount);
		},
		changePage : function(page){
			changePage(page);
		},
		clearSearch : function(){
			clearSearch();
		},
		hideMsg : function(){
			hideMsg();
		},
		openAddUser : function(obj){
			openAddUser(obj);
		},
		addUserTask : function(){
			addUserTask();
		},
		updateUser : function(userString){
			updateUser(userString);
		},
		changeUser : function(){
			changeUser();
		}
	};
}();
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="en">
<head>
<link href="${ctx}/static/jquery/ui/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/datepicker/css/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
<style type="text/css">
  .custom-combobox {
    position: relative;
    display: inline-block;
  }
  .custom-combobox-toggle {
    position: absolute;
    top: 0;
    bottom: 0;
    margin-left: -1px;
    padding: 0;
    /* 支持： IE7 */
    *height: 1.7em;
    *top: 0.1em;
  }
  .custom-combobox-input {
    margin: 0;
    padding: 0.3em;
  }
</style>
<style type="text/css">
	#queryForm td{
		vertical-align: middle; /*vertical-align 属性设置元素的垂直对齐方式。*/
	}
	/*超出字体隐藏*/
	#list td{ /* td 的 title 属性 设值后，鼠标移上去一会会自动显示，鼠标移走会消失*/
		text-align: center;/*text-align 属性规定元素中的文本的水平对齐方式。*/
		max-width: 100px;
		overflow: hidden;/*这个overflow:hidden;属性可以保证div的高度或宽度不变。div里添加的东西再多，高度或宽度也不变。超出的部分隐藏*/
		text-overflow: ellipsis;/*ellipsis： 当对象内文本溢出时显示省略标记（...）*/
		white-space: nowrap;/* 规定段落中的文本不进行换行：*/
	}
	#list th{
		text-align: center;
		font-weight: bold;
		white-space: nowrap;
	}
	.hide{
	display: none;
	
	}
	 .aaa{
	position: relative;
	padding-top: 40px;
}
</style>
<title>用户详情</title> 
</head>
<body>
<div class="aaa">
	<div class="panel-body edit_password table_box">
		<div>
			<form id="queryForm">
				<input type="hidden" id="q_limit" value="10" class="input-small"/>
				<input type="hidden" id="q_currentPage" value="1" class="input-small"/>
				<div style="width: 80%; float: left;">
					<table class="table table-condensed table-bordered" >
					   <tr class="success">
							<td align="right" width="8%">姓名:</td>
							<td width="25%"><input type="text" id="q_name" class="form-control input-sm" placeholder="根据用户名模糊查询"></td>
                            <td align="right">开始时间:</td>
                            <td><input type="text" id="q_beginTime" class="form-control input-sm time" placeholder="创建时间"/></td>
                            <td align="right">结束时间:</td>
                            <td><input type="text" id="q_endTime" class="form-control input-sm time" placeholder="创建时间"/></td>
						</tr>
						<tr class="success">
							<td align="right">性别:</td>
							<td><select id="q_sex" name="sex"
								class="form-control input-sm">
									<option value="">===全部===</option>
									<option value="1">男</option>
									<option value="2">女</option>
							</select></td>
							<td align="right" width="8%">应用市场:</td>
                            <td width="25%" >
                            	<div class="ui-widget">
                                <select id="q_marketKeyId" name="marketKeyIds" class="form-control input-sm">
                                  <!--   <c:if test="${fn:length(loginUser.markets) > 1}">
                                        <option value="">===全部===</option>
                                    </c:if>
                                    <c:forEach items="${loginUser.markets}" var="m">
                                        <option value="${m.keyId}">${m.name}</option>
                                    </c:forEach>
                                     -->
								    <option value="">请选择...</option>
								    <option value="ActionScript">ActionScript</option>
								    <option value="AppleScript">AppleScript</option>
								    <option value="Asp">Asp</option>
								    <option value="BASIC">BASIC</option>
								    <option value="C">C</option>
								    <option value="C++">C++</option>
								    <option value="Clojure">Clojure</option>
								    <option value="COBOL">COBOL</option>
								    <option value="ColdFusion">ColdFusion</option>
								    <option value="Erlang">Erlang</option>
								    <option value="Fortran">Fortran</option>
								    <option value="Groovy">Groovy</option>
								    <option value="Haskell">Haskell</option>
								    <option value="Java">Java</option>
								    <option value="JavaScript">JavaScript</option>
								    <option value="Lisp">Lisp</option>
								    <option value="Perl">Perl</option>
								    <option value="PHP">PHP</option>
								    <option value="Python">Python</option>
								    <option value="Ruby">Ruby</option>
								    <option value="Scala">Scala</option>
								    <option value="Scheme">Scheme</option>
								    <option value="中国">zhongguo(中国)</option>
                                </select>
                               </div>
                            </td>
						</tr>
					</table>
				</div>
				<div style="width: 20%; float: left;padding:30px 50px;">
                    <input type="button" class="btn btn-primary" value="查询" onclick="userCheck.search(1)"/>
                    <input type="button" class="btn" value="清空" onclick="userCheck.clearSearch()"/>
                    <input type="button" class="btn btn-primary" value="添加用户" onclick="userCheck.openAddUser(this)"/>
                </div>
			</form>
		</div>
			
		<div class="span12">
				<div id="message" class="alert alert-success hide"
                    style="margin-bottom: 0px; position: absolute; left: 43%; top: 45px; width: 300px;">
                    <button data-dismiss="alert" class="close" onclick="userCheck.hideMsg()">×</button>
                    <span id="msginfo"></span>
                </div>
			
				<table class="table table-condensed table-hover table-bordered table-striped" id="list">
					<thead>
						<tr style="background:#f5f5f5;">
							<th style="text-align: center;width:5%;">序号</th>
							<th style="text-align: center;" class="success"><span class="glyphicon glyphicon-cog"></span>&nbsp;姓名</th>
							<th style="text-align: center;" class="info"><span class="glyphicon glyphicon-user"></span>&nbsp;昵称</th>
							<th style="text-align: center;" class="warning"><span class="glyphicon glyphicon-envelope"></span> &nbsp;邮箱</th>
							<th style="text-align: center;" class="success"><span class="glyphicon glyphicon-time"></span>&nbsp;创建时间</th>
							<th style="text-align: center;" class="danger"><span class="glyphicon glyphicon-user"></span>&nbsp;性别</th>
						</tr>
					</thead>
					<tbody id="userList">
					</tbody>
				</table>
				<!-- 分页显示 -->
				<div class="pull-right"><ul id="paginationPanel"></ul></div>
		</div>
		<div class="modal fade" id="userUpdateDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				   <div class="modal-dialog">
				      <div class="modal-content">
				         <div class="modal-header">
				            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				            <h4 class="modal-title" id="myModalLabel" style="text-align: center;"></h4>
				         </div>
                          	<div class="modal-body" style="padding:0 15px;">
				             <form id="updateUserForm" name="updateUserForm">
				                 <input type="hidden" name="sex" id="u_sex">
				                 <input type="hidden" name="id" id="u_id">
				                 <input type="hidden" name="createTime" id="u_createTime">
				                 <table class="table" style="margin-bottom:0;">
				                    <tbody>
				                        <tr>
                                            <td style="text-align:left;vertical-align:middle;width:20%;border-top-width:0;"><span style="color:red;"> * </span>用户姓名：</td>
                                            <td style="text-align:left;width: 80%;"><input type="text" id="u_name" name="name" class="form-control required" maxlength="300"></td>
                                        </tr>
                                         <tr>
                                            <td style="text-align:left;vertical-align:middle;width:20%;border-top-width:0;"><span style="color:red;"> * </span>用户昵称：</td>
                                            <td style="text-align:left;width: 80%;"><input type="text" id="u_nickname" name="nickname" class="form-control required" maxlength="300"></td>
                                        </tr>
				             			 <tr>
                                            <td style="text-align:left;vertical-align:middle;width:20%;border-top-width:0;"><span style="color:red;"> * </span>用户邮箱：</td>
                                            <td style="text-align:left;width: 80%;"><input type="text" id="u_email" name="email" class="form-control required" maxlength="300"></td>
                                        </tr>
				                    </tbody>
				                </table>
				            </form>
				         </div>
                      
                         <div class="modal-footer">
                            <span id="addErrorMsg" style="float: left; display: none;" class="error"></span>
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭 </button>
                            <button type="button" class="btn btn-primary" onclick="userCheck.changeUser()">提交 </button>
                         </div>
                      </div><!-- /.modal-content -->
                   </div><!-- /.modal-dialog -->
       </div><!-- /.modal -->
       <!-- 添加user模态框 -->	
		<div class="modal fade" id="addUserDialog" tabindex="-1" role="dialog" aria-labelledby="addIssueDialogLabel" aria-hidden="true">
		       <div class="modal-dialog">
		          <div class="modal-content">
		             <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		                <h4 class="modal-title" id="addIssueDialogLabel">新增用户</h4>
		             </div>
		             <div class="modal-body" style="padding:0 15px;">
		                 <form id="addUserForm" name="addUserForm">
		                     <table class="table" style="margin-bottom:0;">
		                        <tbody>
	                          <tr>
			                      <td style="text-align:right;vertical-align:middle;width:15%;border-top-width:0;">性别：</td>
			                      <td style="border-top-width:0;">
			                       <label class="radio-inline"><input type="radio" id="uu_sex" name="sex" value="1" checked="checked"/>男</label>
			                       <label class="radio-inline"><input type="radio" id="uu_sex" name="sex" value="2"/>女</label>
			                       </td>
				               </tr>
		                        <tr>
                                  <td style="text-align:right;vertical-align:middle;"><span style="color: red;"> * </span>姓名：</td>
                                   <td><input type="text" class="form-control required" id="uu_name" name="name" maxlength="300"  required="required"/></td>
                                </tr>
                                 <tr>
                                   <td style="text-align:right;vertical-align:middle;"><span style="color:red;"> * </span>昵称：</td>
                                   <td><input type="text" id="uu_nickname" name="nickname" class="form-control required" maxlength="300"></td>
                                 </tr>
                                  <tr>
                                      <td style="text-align:right;vertical-align:middle;"><span style="color:red;"> * </span>邮箱：</td>
                                      <td><input type="text" id="uu_email" name="email" class="form-control email" maxlength="300"></td>
                                  </tr>
		                        </tbody>
		                    </table>
		                </form>
		             </div>
		             <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭 </button>
		                <button type="button" class="btn btn-primary" onclick="userCheck.addUserTask()">提交 </button>
		             </div>
		          </div>
		       </div>
		</div>
		
		
        <div class="ex-card-box" align="center">
            <div class="ex-screenshot-thumb-carousel">
                <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_0.jpg.t.jpg" data-toggle="modal" data-target="#ex-screenshot-modal" onclick="$('#ex-screenshot-carousel').carousel(0);"/>
                <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_1.jpg.t.jpg" data-toggle="modal" data-target="#ex-screenshot-modal" onclick="$('#ex-screenshot-carousel').carousel(1);"/>
                <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_2.jpg.t.jpg" data-toggle="modal" data-target="#ex-screenshot-modal" onclick="$('#ex-screenshot-carousel').carousel(2);"/>
                <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_3.jpg.t.jpg" data-toggle="modal" data-target="#ex-screenshot-modal" onclick="$('#ex-screenshot-carousel').carousel(3);"/>
           </div>
        </div>
    	
		
		
       <!-- Bootstrap 轮播（Carousel）插件   start -->
        <div id="ex-screenshot-modal" class="modal fade ex-screenshot-modal" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">截图预览</h4>
                </div>
                <div class="modal-body">
                    <div id="ex-screenshot-carousel" class="carousel slide ex-screenshot-carousel">
                          <!-- 轮播（Carousel）指标 -->
                        <ol class="carousel-indicators">
                            <li data-target="#ex-screenshot-carousel" data-slide-to="0" class="active"></li>
                            <li data-target="#ex-screenshot-carousel" data-slide-to="1" class=""></li>
                            <li data-target="#ex-screenshot-carousel" data-slide-to="2" class=""></li>
                            <li data-target="#ex-screenshot-carousel" data-slide-to="3" class=""></li>
                       </ol>
                       <!-- 轮播（Carousel）项目 -->
                        <div class="carousel-inner">
                             <div class="item active">
                             <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_0.jpg" />
                             <div class="carousel-caption">标题 1</div>
                             </div>
                             <div class="item ">
                             <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_1.jpg" />
                             <div class="carousel-caption">标题 2</div>
                             </div>
                             <div class="item ">
                             <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_2.jpg" />
                             <div class="carousel-caption">标题 3</div>
                             </div>
                             <div class="item ">
                             <img src="http://image.coolapk.com/apk_image/2013/0421/com.nubee.valkyriecrusade_screenshot_3.jpg" />
                             <div class="carousel-caption">标题 4</div>
                             </div>
                        </div>
                       <!-- 轮播（Carousel）导航 -->
                        <a class="left carousel-control" href="#ex-screenshot-carousel" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a>
                        <a class="right carousel-control" href="#ex-screenshot-carousel" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                        </a>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>
       
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/common/json2.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/datepicker/datepicker.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/plugin/bootstrap-paginator.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
	$(document).ready(function() {
		userCheck.search(1);
	});
	
	// 时间控件
		var date=new Date();
		var yy=date.getFullYear();
		var MM=date.getMonth();
		var firstDate=new Date(yy,MM,1);
		
	$('#q_beginTime').datepicker({
		dateFormat: 'yy-mm-dd',
		maxDate:new Date(),
		 onClose: function( selectedDate ) {
		        $('#q_endTime').datepicker( 'option', 'minDate', selectedDate );
		      }
	}); 
	//默认查询起始时间
	 $('#q_beginTime').datepicker('setDate', firstDate );
	$('#q_endTime').datepicker({
		dateFormat: 'yy-mm-dd',
		maxDate:new Date(),
		 onClose: function( selectedDate ) {
		        $('#q_beginTime').datepicker( 'option', 'maxDate', selectedDate );
		      }
	}); 
	//默认查询结束时间xxxx-xx-xx 00:00:00
	 $('#q_endTime').datepicker('setDate', new Date());
	//Jquery实现自动提示下拉框
	 var $autocomplete = $("<ul class='autocomplete'></ul>").hide().insertAfter("#q_name");
		var url=ctx+"/user/getAll";
     $("#q_name").keyup(function() {
         $.get(url, { "q_name": $("#q_name").val() }, function(data) {

             if (data.length) {
                 $autocomplete.empty();

                 var arr = data.substring(0, data.length - 1).split(',');
                 $.each(arr, function(index, term) {
                     $("<li></li>").text(term).appendTo($autocomplete).mouseover(function() {
                         $(this).css("background", "green");
                     }).mouseout(function() {
                         $(this).css("background", "white");
                     })
                     .click(function() {
                         $("#q_name").val(term);
                         $autocomplete.hide();
                     });
                 });
                 $autocomplete.show();
             }

         });
     }).blur(function() {
     setTimeout(function() {
     $autocomplete.hide();
         },500);
        
     });
</script>
  <script>
  (function( $ ) {
    $.widget( "custom.combobox", {
      _create: function() {
        this.wrapper = $( "<span>" )
          .addClass( "custom-combobox" )
          .insertAfter( this.element );
 
        this.element.hide();
        this._createAutocomplete();
        this._createShowAllButton();
      },
 
      _createAutocomplete: function() {
        var selected = this.element.children( ":selected" ),
          value = selected.val() ? selected.text() : "";
 
        this.input = $( "<input>" )
          .appendTo( this.wrapper )
          .val( value )
          .attr( "title", "" )
          .addClass( "custom-combobox-input ui-widget ui-widget-content ui-state-default ui-corner-left" )
          .autocomplete({
            delay: 0,
            minLength: 0,
            source: $.proxy( this, "_source" )
          })
          .tooltip({
            tooltipClass: "ui-state-highlight"
          });
 
        this._on( this.input, {
          autocompleteselect: function( event, ui ) {
            ui.item.option.selected = true;
            this._trigger( "select", event, {
              item: ui.item.option
            });
          },
 
          autocompletechange: "_removeIfInvalid"
        });
      },
 
      _createShowAllButton: function() {
        var input = this.input,
          wasOpen = false;
 
        $( "<a>" )
          .attr( "tabIndex", -1 )
          .attr( "title", "Show All Items" )
          .tooltip()
          .appendTo( this.wrapper )
          .button({
            icons: {
              primary: "ui-icon-triangle-1-s"
            },
            text: false
          })
          .removeClass( "ui-corner-all" )
          .addClass( "custom-combobox-toggle ui-corner-right" )
          .mousedown(function() {
            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
          })
          .click(function() {
            input.focus();
 
            // 如果已经可见则关闭
            if ( wasOpen ) {
              return;
            }
 
            // 传递空字符串作为搜索的值，显示所有的结果
            input.autocomplete( "search", "" );
          });
      },
 
      _source: function( request, response ) {
        var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
        response( this.element.children( "option" ).map(function() {
          var text = $( this ).text();
          if ( this.value && ( !request.term || matcher.test(text) ) )
            return {
              label: text,
              value: text,
              option: this
            };
        }) );
      },
 
      _removeIfInvalid: function( event, ui ) {
 
        // 选择一项，不执行其他动作
        if ( ui.item ) {
          return;
        }
 
        // 搜索一个匹配（不区分大小写）
        var value = this.input.val(),
          valueLowerCase = value.toLowerCase(),
          valid = false;
        this.element.children( "option" ).each(function() {
          if ( $( this ).text().toLowerCase() === valueLowerCase ) {
            this.selected = valid = true;
            return false;
          }
        });
 
        // 找到一个匹配，不执行其他动作
        if ( valid ) {
          return;
        }
 
        // 移除无效的值
        this.input
          .val( "" )
          .attr( "title", value + " didn't match any item" )
          .tooltip( "open" );
        this.element.val( "" );
        this._delay(function() {
          this.input.tooltip( "close" ).attr( "title", "" );
        }, 2500 );
        this.input.data( "ui-autocomplete" ).term = "";
      },
 
      _destroy: function() {
        this.wrapper.remove();
        this.element.show();
      }
    });
  })( jQuery );
 
  $(function() {
    $( "#q_marketKeyId" ).combobox();
  });
  </script>
<script type="text/javascript" src="${ctx}/static/user/user.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugins/ajaxfileupload.js"></script>
</html>

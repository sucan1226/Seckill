<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="commons/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>秒杀列表页</title>
      <%@include file="commons/head.jsp" %>
      <style type="text/css">
      	body{
      		background-color:#eee;
      	}
		.table th, .table td {  
			text-align: center;
			vertical-align: middle!important;
		}   
      </style>
   </head>
   <body>
      <!-- 页面显示部分 -->
      <div class="container" style="margin-top:40px;">
      	<div class="panel panel-primary">
      		<div class="panel-heading text-center">
      			<h2>秒杀列表</h2>
      		</div>
      		<div class="panel-body">
      			<table class="table table-hover">
      				<thead>
      					<tr>
      						<th>名称</th>
      						<th>库存</th>
      						<th>开始时间</th>
      						<th>结束时间</th>
      						<th>创建时间</th>
      						<th>详情页</th>
      					</tr>
      				</thead>
      				<tbody>
      					<c:forEach var="sk" items="${list}">
      						<tr>
      							<td >${sk.name}</td>
      							<td >${sk.number }</td>
      							<td>
      								<fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
      							</td >
      							<td >
      								<fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
      							</td>
      							<td>
      								<fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
      							</td>
      							<td>
      								<a class="btn btn-info" href="<%=request.getContextPath()%>/seckill/${sk.seckillId}/detail">查看详情</a>
      							</td>
      						</tr>
      					</c:forEach>
      				</tbody>
      			</table>
      		</div>
      	</div>
      </div>
   </body>
   <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
   <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
   <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
   <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
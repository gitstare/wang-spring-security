package org.student.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServer
 */
public class DownloadServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//获取需要下载的文件名
		String FileName=request.getParameter("filename");
		//下载文件 设置消息头
		response.addHeader("content-Type", "application/octet-stream");
		response.addHeader("content-Disposition", "attachement; filename="+FileName);
		//将文件转换为输入流
		InputStream in=getServletContext().getResourceAsStream("/res/mm.jpg");
       //通过输出流 将刚才已将转为输入流的文件 输出啊给用户
		 ServletOutputStream out = response.getOutputStream();
		 byte[] bs=new byte[10];
		 int len=-1;
		 while((len=in.read(bs))!=-1) {
			 out.write(bs, 0, len);
		 }
		 out.close();
		 in.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

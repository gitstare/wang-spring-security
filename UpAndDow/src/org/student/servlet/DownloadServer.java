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
		//��ȡ��Ҫ���ص��ļ���
		String FileName=request.getParameter("filename");
		//�����ļ� ������Ϣͷ
		response.addHeader("content-Type", "application/octet-stream");
		response.addHeader("content-Disposition", "attachement; filename="+FileName);
		//���ļ�ת��Ϊ������
		InputStream in=getServletContext().getResourceAsStream("/res/mm.jpg");
       //ͨ������� ���ղ��ѽ�תΪ���������ļ� ��������û�
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

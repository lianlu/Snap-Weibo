package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Blah;
import model.UserService;

/**
 * Servlet implementation class Message
 */
@WebServlet(urlPatterns = { "/message.do" }, initParams = { @WebInitParam(name = "MEMBER_VIEW", value = "member.jsp") })
public class Message extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String MEMBER_VIEW;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() throws ServletException {
		MEMBER_VIEW = getServletConfig().getInitParameter("MEMBER_VIEW");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("login");
		UserService userService = (UserService) getServletContext()
				.getAttribute("userService");

		Blah blah = new Blah();
		blah.setUsername(username);
		String blabla = request.getParameter("blabla");
		if (blabla != null) blabla = blabla.trim();
		
		if (blabla != null && blabla.length() != 0) {
			if (blabla.length() < 140) {
				blah.setDate(new Date());
				blah.setTxt(blabla);
				userService.addBlah(blah);
			} else
				request.setAttribute("blabla", blabla);
		}

		List<Blah> blahs = userService.getBlahs(blah);
		
		request.setAttribute("blahs", blahs);
		request.getRequestDispatcher(MEMBER_VIEW).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

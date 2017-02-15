package ardrone;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import GSON
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.*;

//import rabbitMQ
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;



/**
 * Servlet implementation class LiveImage
 */

@WebServlet("/LiveImage")
public class LiveImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String new_msg;
	Gson gson = new Gson();

	private BufferedImage image_deserialize(String raw_msg){
		Type typeOfHashMap = new TypeToken<Map<String, String>>() {}.getType();
		Map<String, String> msgobj = gson.fromJson(raw_msg, typeOfHashMap);
		System.out.println("NAME: "+msgobj.get("NAME"));
		System.out.println("TIMESTAMP"+msgobj.get("TIMESTAMP"));
		return null;
	}
	

    /**
     * Default constructor. 
     */
    public LiveImage() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("image/png");

		String pathToWeb = getServletContext().getRealPath(File.separator);
		System.out.println("pathToWeb is : "+pathToWeb);
		File f = new File(pathToWeb + "images/test.png");
		System.out.println("file dir is : "+f.getAbsolutePath());
		//BufferedImage bi = ImageIO.read(f);
		BufferedImage bi = image_deserialize(new_msg);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "png", out);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

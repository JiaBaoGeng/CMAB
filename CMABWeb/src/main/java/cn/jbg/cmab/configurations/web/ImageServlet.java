package cn.jbg.cmab.configurations.web;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jbg on 2018/4/7.
 */
public class ImageServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(ImageServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        synchronized (ImageServlet.class) {
            doRequest(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        synchronized (ImageServlet.class) {
            if(req.getRequestURI().equals("/uploadImage")){
                doImageUpload(req,resp);
            }else{
                doRequest(req, resp);
            }

        }
    }

    /**
     * 处理图片请求
     */
    protected void doRequest(HttpServletRequest req, HttpServletResponse resp) {

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        OutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            //inputStream = new FileInputStream("C:\\Users\\pc\\Desktop\\cart.png");
            inputStream = new FileInputStream("F:\\" + uri);
            resp.setContentType("image/*");
            outputStream = resp.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            PrintWriter toClient = null;
            try {
                toClient = new PrintWriter(outputStream);
                resp.setContentType("text/html;charset=gb2312");
                toClient.write("无法打开图片!");
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                if (toClient != null) {
                    toClient.close();
                }
            }
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 处理上传图片的请求,
     * 以表单形式上传， 将表单的name属性的值作为文件路径/名称
     * 故上传来的文件表单应当将name值 作为文件名称
     * */
    protected void doImageUpload(HttpServletRequest req, HttpServletResponse resp){
        //创建缓冲区目录
        File tempFile = new File("F:\\images\\");
        //1. 创建工厂类DiskFileItemFactory对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4096);  //缓冲区大小
        factory.setRepository(tempFile); //缓冲区目录
        //2. 使用工厂类创建解析器对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(4194304);
        upload.setSizeMax(4194304*2);//最大文件尺寸,8MB
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //3. 解析request对象，得到fileItems
            // FileItem对象对应一个表单项（表单字段）。可以是文件字段或普通字段
            List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(req));
            Iterator<FileItem> i = fileItems.iterator();
            while(i.hasNext()){
                //图片文件
                FileItem fileItem = i.next();
                //即获取formData中的参数，返回true为一般的表单字段， false则为文件字段
                String fieldName = fileItem.getFieldName(); // 字段名称
                if(fieldName != null) {
                    if (!fileItem.isFormField()) {  //表明为文件字段
                        String fileName = fileItem.getName();

                        // 文件名不为空，说明用户上传了文件
                        if (fileName != null && !fileName.equals("")) {
                            FileOutputStream outputStream = new FileOutputStream(new File("F:\\images\\" + fieldName));
                            //获取输入流
                            InputStream inputStream = fileItem.getInputStream();
                            int length = 0;
                            byte[] buf = new byte[1024];
                            while((length = inputStream.read(buf)) != -1){
                                outputStream.write(buf, 0, length);
                            }
                            outputStream.flush();
                            outputStream.close();
                            fileItem.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



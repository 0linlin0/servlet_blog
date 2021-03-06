package Servlet;

import Dao.ArticleDao;
import Entity.Article;
import Entity.Return_result;
import Util.initutil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @auther Skay
 * @date 2020/10/20 15:41
 * @description
 */
public class IndexServlet extends HttpServlet {
    private Return_result return_result = new Return_result();

    private String token = "";
    private HashMap<String,Object> req_map = new HashMap<>();;
    private String Action = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        PrintWriter out = response.getWriter();
//        out.println("hello world");
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        token = initutil.gettoken(request.getCookies(),token); //获取token
        Enumeration<String> a = request.getParameterNames(); //获取的请求数 保存到map中
        req_map = initutil.parsreq(request.getParameterNames(),request,response,req_map);
        Action = request.getParameter("action");

        IndexServlet indexServlet = new IndexServlet();
        out.println(indexServlet.run(request,response).get_return_result());

    }


    public Return_result run(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        Article[] newest_articles = articleDao.get_newest();
        for (Article each_article:newest_articles){
            return_result.add_result_para(String.valueOf(each_article.getId()),each_article.getTitle());
        }

        Article[] recommend_articles = articleDao.get_recommend();
        for (Article each_article:recommend_articles){
            return_result.add_result_para(String.valueOf(each_article.getId()),each_article.getTitle());
        }
        return return_result;
    }
}

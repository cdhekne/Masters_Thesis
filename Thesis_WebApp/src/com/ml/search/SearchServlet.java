package com.ml.search;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import com.google.gson.Gson;
import com.ml.query.QueryInfo;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "/home/cdhekne/Documents/Thesis/udacity_1.ttl";
	private static final String db = "http://localhost:3030/udacity/query";
	private final OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
	private QueryInfo qi = new QueryInfo();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword;
		
		keyword = request.getParameter("keyword");
		
		/*if(keyword.isEmpty()) {
			// Send back to home page
			response.sendRedirect("index.jsp");
			return;
		}
		else {*/
			m.read(url, "RDF/XML");
			
			String queryString = "prefix edu: <http://cdhekne.github.io/mooc.owl#>\n" +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
				"PREFIX schema: <http://schema.org/>\n" +
				"SELECT ?Short_Name ?Duration WHERE {\n" +
		        "?course edu:Duration ?Duration ; edu:Short_Name ?Short_Name .\n" +
		        "FILTER (regex(?Short_Name, \"" + keyword + "\", \"i\")).\n" +
				"}";
			
			Query query = QueryFactory.create(queryString) ;
			QueryExecution qexec = QueryExecutionFactory.sparqlService(db, query);
			
			/*Course courses = new Course();
			courses.setCourses(qi.getCourses(qexec));
			request.setAttribute("courses", courses);
			*/
			
			// Better: forward request & response with redirect
			// Browser doesn't know that this is a new request, request
			// and response parameters are carried over
			String json="";
			try{
				
				ResultSet responseResultSet = qexec.execSelect();
				while( responseResultSet.hasNext())
				{
					HashMap<String, String> j = new HashMap<String, String>();
					Gson gson = new Gson();
					QuerySolution soln = responseResultSet.nextSolution();
					RDFNode name = soln.get("?Short_Name");
					RDFNode recBackground = soln.get("?Duration");
					j.put("name", name.toString());
					j.put("Duration", recBackground.toString());
					json += "\n"+gson.toJson(j);
				}
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
			
			json = "[" + json+ "]";
            
            // fw.close();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
            //Tell the browser what requests we allow.
            response.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(json)); 
            System.out.println("Done");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("results.jsp");
			request.setAttribute("json", json);
			dispatcher.forward(request, response);
		}
	//}
	
	public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }	
	
}

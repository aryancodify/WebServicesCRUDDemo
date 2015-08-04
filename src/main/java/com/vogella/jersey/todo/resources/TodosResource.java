package com.vogella.jersey.todo.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.vogella.jersey.todo.dao.TodoDao;
import com.vogella.jersey.todo.model.Todo;

@Path("/todos")
public class TodosResource {
	
	//Insert contextual objects into the class,
	//e.g. ServletContext,Request,Response,UriInfo
	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	
	//Returns the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Todo> getTodosBrowser(){
		List<Todo> todos=new ArrayList<Todo>();
		todos.addAll(TodoDao.instance.getModel().values());
		return todos;
	}
	
	//Returns a list of todos for the application
	@GET
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public List<Todo> getTodos(){
		List<Todo> todos=new ArrayList<Todo>();
		Todo todo=new Todo("1", "fvf", "eferfvre");
		Todo todo2=new Todo("2", "dcsdc", "cvvev");
		/*todos.addAll(TodoDao.instance.getModel().values());*/
		todos.add(todo);
		todos.add(todo2);
		return todos;
	}
	//Returns the number of Todos tto get total number of records
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount(){
		int count = TodoDao.instance.getModel().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newTodo(@FormParam("id")String id,
			            @FormParam("summary")String summary,
			            @FormParam("description")String description,
			            @Context HttpServletResponse servletResponse) throws IOException{
			Todo todo = new Todo(id, description, summary);
			TodoDao.instance.getModel().put(id, todo);
			servletResponse.sendRedirect("../create_todo.html");
	}
	
	//Passing a parameter in the url for the rest api 
	@Path("{todo}")
	public TodoResource getTodo(@PathParam("todo") String id){
			return new TodoResource(uriInfo, request, id);
	}
}

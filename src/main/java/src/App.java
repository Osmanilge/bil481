/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package src;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.*;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class App {
  public String getGreeting() {
    return "Hello world.";
}

public static boolean search(ArrayList<Integer> array, int e) {
  System.out.println("inside search");
  if (array == null) return false;

  for (int elt : array) {
    if (elt == e) return true;
  }
  return false;
}

public static void main(String[] args) {
    
    port(getHerokuAssignedPort());
    Logger logger = LogManager.getLogger(App.class);

        int port =4567; //Integer.parseInt(System.getenv("PORT"));//
        port(port);
        logger.error("Current port number:" + port);


    get("/", (req, res) -> "Hello, World");

    post("/compute", (req, res) -> {
      //System.out.println(req.queryParams("input1"));
      //System.out.println(req.queryParams("input2"));

      String input1 = req.queryParams("input1");
      java.util.Scanner sc1 = new java.util.Scanner(input1);
      sc1.useDelimiter("[;\r\n]+");
      java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
      while (sc1.hasNext())
      {
        int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
        inputList.add(value);
      }
      sc1.close();
      System.out.println(inputList);
      java.util.ArrayList<Integer> inputList2 = new java.util.ArrayList<>();

      String input2 = req.queryParams("input2").replaceAll("\\s","");
      java.util.Scanner sc2 = new java.util.Scanner(input2);
      while (sc2.hasNext())
      {
        int value = Integer.parseInt(sc2.next().replaceAll("\\s",""));
        inputList2.add(value);
      }
      sc2.close();
      String diff = req.queryParams("Diff").replaceAll("\\s","");
      int length=inputList.size();
      double mean=0;
      for(int i=0;i<length;i++)mean+=(double)inputList.get(i)/length;

      int length2=inputList2.size();
      double mean2=0;
      for(int i=0;i<length2;i++)mean2+=(double)inputList2.get(i)/length2;

      
      //int input2AsInt = Integer.parseInt(input2);

      boolean result = false;//App.search(inputList, input2AsInt);
      if(mean-mean2==Double.parseDouble(diff)){
        

        result=true;}
        System.out.println(mean+" "+mean2+" "+diff+"\n\n\n");
      Map<String, Boolean> map = new HashMap<String, Boolean>();
      map.put("result", result);
      return new ModelAndView(map, "compute.mustache");
    }, new MustacheTemplateEngine());


    get("/compute",
        (rq, rs) -> {
          Map<String, String> map = new HashMap<String, String>();
          map.put("result", "not computed yet!");
          return new ModelAndView(map, "compute.mustache");
        },
        new MustacheTemplateEngine());
}

static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
        return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
}


}

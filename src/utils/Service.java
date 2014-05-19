package utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.directwebremoting.ServerContext;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.proxy.dwr.Util;

public class Service {

 private int count = 0;

 public void update(Data data) {
   List<Data> messages = new ArrayList<Data>();
//   messages.add(new Data("testing" + count++));
   messages.add(data);

   // Collection sessions = wctx.getAllScriptSessions();
   WebContext wctx = WebContextFactory.get();
   Collection sessions = wctx.getScriptSessionsByPage("/BDYRMS/testdwr.jsp");
   Util utilAll = new Util(sessions);
   utilAll.addOptions("updates", messages, "value");
 }
}
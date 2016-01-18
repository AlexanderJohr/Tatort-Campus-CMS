part of tatort_campus_app;

/**
 * Liest die Elemente url und target aus dem uebergenen
 * XML Dokument [xml] aus und aktualisiert das Element
 * mit der id target mit der Antwort von der Anfrage an url.
 * 
 * @author Alexander Johr u26865 m18927
 */
void updateContainer(Document xml){  
  if(xml != null && xml.queryAll("updates").isNotEmpty){  
    ElementList updates = xml.queryAll("update");
    
    updates.forEach((Element update){
      // XML Tags "target" und "url" auslesen
      String target = update.query("target").text;      
      String url = update.query("url").text;     
      
      // Anfrage an den Server nach der emfangenen url stellen.
      HttpRequest.request(url, method:  "get").then((httpResult){        
        Element targetElement = query("#${target}");        
        if(targetElement != null){          
          targetElement.innerHtml = httpResult.responseText;    

          // Alle dazukommenden RestLinks sollen gesucht und erstellt werden         
          queryAll('.rest_link').forEach((Element link){ new RestLink(link); });
        }     
      });           
    });      
  } else {    
    window.alert("Fehler beim Aufrufen des Dienstes. Bitte einloggen.");
  }
}





part of tatort_campus_app;

/**
 * Sendet bei Klick auf den Button f&uuml;r das Aktualisieren
 * der Seiteninhalte eine asynchrone Anfrage an den Server,
 * die alle aktualisierten Seiteninhalte beinhaltet, 
 * um diese auf der Datenbank auszutauschen.
 * 
 * @author Alexander Johr u26865 m18927
 */
class SeitenInhaltUpdateDialog{

  Map<String, String> original = new Map<String, String>();
  DivElement dialogDiv = new DivElement();

  SeitenInhaltUpdateDialog(){

    queryAll('[contenteditable="true"]').forEach((element){
      if(element.id != "")
        original['${element.id}'] = element.innerHtml;
    });

    dialogDiv.classes.add("seiten_inhalt_update_dialog");

    var sendenLabel = new SpanElement();
    sendenLabel..classes.add("update_senden_button")
               ..text = "Änderungen am Seiteninhalt übernehmen"
               ..onClick.listen(_sendenClick);

    dialogDiv.append(sendenLabel);
    query('body').append(dialogDiv);
  }

  /**
   * Anfrage an den Server senden.
   */
  void _sendenClick(MouseEvent evt){
    //Anfrage erstellen
    MultipartRequest request = new MultipartRequest("/TatortCampusCMS/webresources/content/update");    
        
    // Alle aktualisierbaren Containers suchen
    queryAll('[contenteditable="true"]').forEach((element){
        if(element.innerHtml != original[element.id]){
          // Inhalt hinzuf&uuml;gen
          request.append(element.id, element.innerHtml.replaceAll("'", "&rsquo;"));
        }
    });
    
    request.send().then((xml){ 
      window.location.reload();
    }); 
  }
}

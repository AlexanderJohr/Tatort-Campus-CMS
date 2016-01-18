
part of tatort_campus_app;

/**
 * Erstellt aus der href des uebergebenen Links
 * eine asynchrone Post Anfrage an einen Rest-Service
 */
class RestLink {
  AnchorElement _element;
  /**
   * Dem Konstruktor wird ein HTML-Link uebergeben
   */
  RestLink(this._element){
    // Beim Klick auf den Link wird onClick gestartet
    _element.onClick.listen(onClick);
  }
  
  onClick(MouseEvent evt){
    // Die Standartaktion (Anfrage im Browser ausfueren) wird verhindert
    evt.preventDefault();
    
    // Asynchroner Post Aufruf an den Rest-Service aus dem href-Attribut 
    HttpRequest.request(_element.href, method:  "post").then((restResult)
    {
      // Die Antwort-XML zum Aktualisieren eines Containers verwenden
      updateContainer(restResult.responseXml);      
    });    
  }  
}
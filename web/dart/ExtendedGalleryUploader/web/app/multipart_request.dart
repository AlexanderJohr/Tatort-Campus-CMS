
part of tatort_campus_app;

/**
 * Asynchrone Anfrage an den Server des enctype "multipart/form-data"
 * 
 * @author Alexander Johr u26865 m18927
 */
class MultipartRequest{
  
  FormData _data; 
  String _action;
  
  MultipartRequest(this._action){
    FormElement fe = new FormElement();
    fe.method="post";
    fe.action= _action;
    fe.enctype = "multipart/form-data";
    
    _data = new FormData(fe); 
  }
  
  /**
   * Einsetzen eines Formfeldes zum Aufruf.
   */
  void append(String name, String value){
    _data.append(name, value);
  }
  
  /**
   * Einsetzen eines Blobs (Bin&auml;rdaten z.B. Dateien) zum Aufruf.
   */
  void appendBlob(String name, Blob value, [String filename]){
    _data.appendBlob(name, value, filename);
  }
  
  /**
   * Asynchrones senden der Anfrage.
   */
  Future<Document> send(){    
    Completer ret = new Completer();    
    HttpRequest xhr = new HttpRequest();   
    
    xhr.onReadyStateChange.listen((Event e) {
      if (xhr.readyState == HttpRequest.DONE &&
          (xhr.status == 200 || xhr.status == 0)) {       
        ret.complete(xhr.responseXml);
      }
    });
    
    xhr.open("POST", _action);
    xhr.send(_data);    
    
    return ret.future;
  }
}